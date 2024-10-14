package com.vesalukkarila.service;

import com.vesalukkarila.dto.SongDto;
import com.vesalukkarila.model.Song;
import com.vesalukkarila.repository.SongRepository;
import com.vesalukkarila.web.exception.EmptyPatchRequestException;
import com.vesalukkarila.web.exception.SongAlreadyExistsException;
import com.vesalukkarila.web.exception.SongNotFoundException;

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
    public Song createSong(SongDto songDto){
        Song song = new Song(songDto);
        song.setId(UUID.randomUUID());
        if (songRepository.songExists(song)){
            throw new SongAlreadyExistsException(song);
        }
        songRepository.save(song);
        return song;
    }


    @Transactional
    public Song updateSong(String id, SongDto songDto){
        Song song = this.findById(id);
        song.updateFields(songDto);
        if (songRepository.songExists(song)){
            throw new SongAlreadyExistsException(song);
        }
        songRepository.update(song);
        return song;
    }

    @Transactional
    public Song patchSong(String id, SongDto songDto){
        validatePatchFields(songDto);
        Song song = this.findById(id);
        song.updateFields(songDto);
        if (songRepository.songExists(song)){
            throw new SongAlreadyExistsException(song);
        }
        songRepository.update(song);
        return song;
    }

    private void validatePatchFields(SongDto songDto) {
        if (songDto.getName() == null && songDto.getArtist() == null
                && songDto.getPublishYear() == null) {
            throw new EmptyPatchRequestException();
        }
    }


    @Transactional
    public void deleteSong(String id){
        if (!songRepository.delete(id)){
            throw new SongNotFoundException(id);
        }
    }
}
