package com.vesalukkarila.service;

import com.vesalukkarila.model.Song;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SongService {
    // TODO: replace List with database access through simpleJdbcInsert and JdbcTemplate
    //  constructor injection
    private List<Song> songs;

    public SongService() {
        this.songs = new CopyOnWriteArrayList<>();
    }

    /*TODO: 1. refactor to use createSong
    *       2. later: create class that runs e.g. in "dev" mode and populates database
    *       3. consider: should in-memory db be cleared after n-inputs or even on application shutdown*/
    @PostConstruct
    public void init() {
        songs.add(new Song("Thunderstruck", "AC/DC", 1990));
    }

    // TODO: replace with help of jdbctemplate's rowmapper, simpleJdbcInsert not usable here
    public List<Song> getSongs() {
        return songs;
    }

    //TODO: createSong, use simpleJbdcInsert, simpler and more readable than jdbcTemplate for creation
    // TODO: later; check that same song&artist&year instance is not already in database
}
