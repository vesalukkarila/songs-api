package com.vesalukkarila.service;

import com.vesalukkarila.model.Song;
import com.vesalukkarila.repository.SongRepository;
import com.vesalukkarila.web.exception.SongAlreadyExistsException;
import com.vesalukkarila.web.exception.SongNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SongService {
    private final SongRepository songRepository;

    public SongService(SongRepository songRepository){
        this.songRepository = songRepository;
    }

    @Transactional
    public List<Song> findAll() {
        return songRepository.findAll();
    }


    @Transactional
    public Song findById(String id) {
        return songRepository.findById(id);
    }


    @Transactional
    public Song createSong(String name, String artist, Integer publishYear){
        if (songRepository.songExists(name, artist, publishYear)){
            throw new SongAlreadyExistsException(name,artist,publishYear);
        }
        Song song = new Song(name, artist, publishYear);
        song.setId(UUID.randomUUID());
        songRepository.save(song);
        return song;
    }


    @Transactional
    public Song updateSong(String id, String name, String artist, Integer publishYear){
        if (songRepository.songExists(name, artist, publishYear)){
            throw new SongAlreadyExistsException(name, artist, publishYear);
        }
        Song song = new Song(name, artist, publishYear);
        song.setId(UUID.fromString(id));
        songRepository.update(song);
        return song;
    }


    @Transactional
    public Song patchSong(String id, String name, String artist, Integer publishYear){
        if (songRepository.songExists(name, artist, publishYear)){
            throw new SongAlreadyExistsException(name, artist, publishYear);
        }
        Song song = new Song(name, artist, publishYear);
        song.setId(UUID.fromString(id));
        songRepository.update(song);
        return song;
    }


    @Transactional
    public void deleteSong(String id){
        if (!songRepository.delete(id)){
            throw new SongNotFoundException(id);
        }
    }
}
