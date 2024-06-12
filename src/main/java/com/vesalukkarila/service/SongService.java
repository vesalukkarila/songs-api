package com.vesalukkarila.service;

import com.vesalukkarila.model.Song;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SongService {

    private List<Song> songs;

    public SongService() {
        this.songs = new CopyOnWriteArrayList<>();
    }

    @PostConstruct
    public void init() {
        songs.add(new Song("Thunderstruck", "AC/DC", 1990));
    }

    public List<Song> getSongs() {
        return songs;
    }
}
