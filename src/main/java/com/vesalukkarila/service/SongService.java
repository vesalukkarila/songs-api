package com.vesalukkarila.service;

import com.vesalukkarila.model.Song;
import com.vesalukkarila.web.exception.SongAlreadyExistsException;
import com.vesalukkarila.web.exception.SongNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class SongService {

    private final JdbcTemplate jdbcTemplate;

    public SongService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*TODO: 1. later: create class that runs e.g. in "dev" mode and populates database with few Songs
    *       2. consider: should in-memory db be cleared after n-inputs/on application shutdown/through endpoint*/
    @PostConstruct
    public void init() {
        if (getSongs().isEmpty()){
            createSong("Thunderstruck", "AC/DC", 1990);
        }
    }


    public List<Song> getSongs() {
        String sql = "SELECT id, name, artist, publishYear FROM songs";
        return jdbcTemplate.query(sql, new SongRowMapper());
    }


    public Song getSongById(String id) {
        String sql = "SELECT id, name, artist, publishYear FROM songs where id=?";
        try {
            return jdbcTemplate.queryForObject(sql, new SongRowMapper(), id);
        }catch (Exception e){
            throw new SongNotFoundException(id);
        }
    }


    public Song createSong(String name, String artist, Integer publishYear) {
        if (songExists(name, artist, publishYear)){
            throw new SongAlreadyExistsException(name, artist, publishYear);
        }else {
            UUID id = UUID.randomUUID();
            insertSongIntoDatabase(id, name, artist, publishYear);
            Song song = new Song(name, artist, publishYear);
            song.setId(id);
            return song;
        }
    }


    private boolean songExists(String name, String artist, Integer publishYear){
        String sql = "SELECT COUNT(*) FROM songs WHERE name = ? AND artist = ? AND publishYear = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name, artist, publishYear);
        return count != null && count > 0;
    }


    private void insertSongIntoDatabase(UUID id, String name, String artist, Integer publishYear) {
        String sql = "INSERT INTO songs (id, name, artist, publishYear) VALUES (?, ?, ?, ?)";
        this.jdbcTemplate.update(sql, id.toString(), name, artist, publishYear);
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
