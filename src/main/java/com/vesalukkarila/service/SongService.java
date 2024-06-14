package com.vesalukkarila.service;

import com.vesalukkarila.model.Song;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SongService {
    // TODO: add field JdbcTemplate use constructor injection
    private final SimpleJdbcInsert simpleJdbcInsert;

    public SongService(SimpleJdbcInsert simpleJdbcInsert) {
        this.simpleJdbcInsert = simpleJdbcInsert;
    }

    /*TODO: 1. refactor to use createSong
    *       2. later: create class that runs e.g. in "dev" mode and populates database with few Songs
    *       3. consider: should in-memory db be cleared after n-inputs or even on application shutdown*/
//    @PostConstruct
//    public void init() {
//        songs.add(new Song("Thunderstruck", "AC/DC", 1990));
//    }

    // TODO: replace with help of jdbctemplate's rowmapper, simpleJdbcInsert not usable here
    public List<Song> getSongs() {
        return List.of();
    }


    //TODO: createSong, use simpleJbdcInsert, simpler and more readable than jdbcTemplate for creation
    // TODO: later; check that same song&artist&year instance is not already in database
    public Song createSong(String name, String artist, Integer publishYear) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("artist", artist);
        parameters.put("publishYear", publishYear);
        //TODO, fix: atm db uses auto incremented int as primary key, Song uses UUID.random
        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        String id = key.toString();

        Song song = new Song();
        song.setId(id);
        song.setName(name);
        song.setArtist(artist);
        song.setPublishYear(publishYear);
        return song;
    }
}
