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

    private final JdbcTemplate jdbcTemplate;
    private final SongRepository songRepository;
    public SongService(JdbcTemplate jdbcTemplate, SongRepository songRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.songRepository = songRepository;
    }

    @Transactional
    public List<Song> findAll() {
        return this.songRepository.findAll();
    }

    @Transactional
    public Song findById(String id) {
        return this.songRepository.findById(id);
    }

    @Transactional
    public Song createSong(String name, String artist, Integer publishYear) {
        if (this.songRepository.songExists(name, artist, publishYear)){
            throw new SongAlreadyExistsException(name,artist,publishYear);
        }
        Song song = new Song(name, artist, publishYear);
        song.setId(UUID.randomUUID());
        this.songRepository.save(song);
        return song;
    }


    @Transactional
    public Song updateSong(String id, String name, String artist, Integer publishYear) {
        if (this.songRepository.songExists(name, artist, publishYear)){
            throw new SongAlreadyExistsException(name, artist, publishYear);
        }
        Song song = new Song(name, artist, publishYear);
        song.setId(UUID.fromString(id));
        this.songRepository.update(song);
        return song;
    }

    @Transactional
    public Song patchSong(Song existingSong){
        if (songExists(existingSong.getName(), existingSong.getArtist(), existingSong.getPublishYear())){
            throw new SongAlreadyExistsException(existingSong.getName(), existingSong.getArtist(), existingSong.getPublishYear());
        }
        String sql = "UPDATE songs SET name = ?, artist = ?, publishYear = ? WHERE id = ?";
        Object[] args = new Object[] {
                existingSong.getName(),
                existingSong.getArtist(),
                existingSong.getPublishYear(),
                existingSong.getId().toString()
        };
        this.jdbcTemplate.update(sql, args);
        return existingSong;
    }

    @Transactional
    public void deleteSong(String id){
        String sql = "DELETE FROM songs WHERE id=?";
        Object[] args = new Object[] {id};
        boolean success = this.jdbcTemplate.update(sql, args) == 1;
        if (!success){
            throw new SongNotFoundException(id);
        }
    }

    private boolean songExists(String name, String artist, Integer publishYear){
        String sql = "SELECT COUNT(*) FROM songs WHERE name = ? AND artist = ? AND publishYear = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name, artist, publishYear);
        return count != null && count > 0;
    }


//    private void insertSongIntoDatabase(UUID id, String name, String artist, Integer publishYear) {
//        String sql = "INSERT INTO songs (id, name, artist, publishYear) VALUES (?, ?, ?, ?)";
//        this.jdbcTemplate.update(sql, id.toString(), name, artist, publishYear);
//    }



}
