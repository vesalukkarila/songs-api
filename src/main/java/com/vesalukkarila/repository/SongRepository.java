package com.vesalukkarila.repository;

import com.vesalukkarila.model.Song;
import com.vesalukkarila.web.exception.SongNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class SongRepository implements ISongRepository{

    private final JdbcTemplate jdbcTemplate;
    private static final SongRowMapper songRowMapper = new SongRowMapper();

    public SongRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Song> findAll() {
        String sql = "SELECT id, name, artist, publishYear FROM songs";
        return this.jdbcTemplate.query(sql, songRowMapper);
    }

    @Override
    public Song findById(String id) {
        String sql = "SELECT id, name, artist, publishYear FROM songs where id=?";
        try {
            return this.jdbcTemplate.queryForObject(sql, songRowMapper, id);
        }catch (Exception e){
            throw new SongNotFoundException(id);
        }
    }

    @Override
    public void save(Song song) {
        String sql = "INSERT INTO songs (id, name, artist, publishYear) VALUES (?,?,?,?)";
        Object[] args = new Object[]{song.getId().toString(), song.getName(), song.getArtist(), song.getPublishYear()};
        this.jdbcTemplate.update(sql, args);
    }

    @Override
    public void update(Song song) {
        String sql = "UPDATE songs SET name = ?, artist = ?, publishYear = ? WHERE id=?";
        Object[] args = new Object[] {song.getName(), song.getArtist(), song.getPublishYear(), song.getId().toString()};
        this.jdbcTemplate.update(sql, args);
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM songs WHERE id=?";
        Object[] args = new Object[] {id};
        return this.jdbcTemplate.update(sql, args) == 1;
    }


    public boolean songExists(String name, String artist, Integer publishYear){
        String sql = "SELECT COUNT(*) FROM songs WHERE name = ? AND artist = ? AND publishYear = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name, artist, publishYear);
        return count != null && count > 0;
    }

    private static class SongRowMapper implements RowMapper<Song> {
        @Override
        public Song mapRow(ResultSet rs, int rowNum) throws SQLException {
            Song song = new Song();
            song.setId(UUID.fromString(rs.getString("id")));
            song.setName(rs.getString("name"));
            song.setArtist(rs.getString("artist"));
            song.setPublishYear(rs.getInt("publishYear"));
            return song;
        }
    }
}
