package com.vesalukkarila.repository;

import com.vesalukkarila.model.Song;
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
        return jdbcTemplate.query(sql, songRowMapper);
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
