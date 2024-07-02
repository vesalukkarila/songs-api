package com.vesalukkarila.service;

import com.vesalukkarila.model.Song;
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

@Component
public class SongService {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public SongService(JdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsert) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert;
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
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new SongRowMapper());
    }

    // TODO: later; check that same song&artist&year instance is not already in database
    //TODO, fix: atm db uses auto incremented int as primary key, Song uses UUID.random
    public Song createSong(String name, String artist, Integer publishYear) {
        String id = insertSongIntoDatabase(name,artist,publishYear);

        Song song = new Song();
        song.setId(id);
        song.setName(name);
        song.setArtist(artist);
        song.setPublishYear(publishYear);
        return song;
    }

    private String insertSongIntoDatabase(String name, String artist, Integer publishYear) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("artist", artist);
        parameters.put("publishYear", publishYear);
        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return key.toString();
    }

    private static class SongRowMapper implements RowMapper<Song> {
        @Override
        public Song mapRow(ResultSet rs, int rowNum) throws SQLException {
            Song song = new Song();
            song.setId(rs.getString("id"));
            song.setName(rs.getString("name"));
            song.setArtist(rs.getString("artist"));
            song.setPublishYear(rs.getInt("publishYear"));
            return song;
        }
    }
}
