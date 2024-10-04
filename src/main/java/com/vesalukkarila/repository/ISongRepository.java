package com.vesalukkarila.repository;

import com.vesalukkarila.model.Song;

import java.util.List;

public interface ISongRepository {
    List<Song> findAll();
    Song findById(String id);
    void save(Song song);
}
