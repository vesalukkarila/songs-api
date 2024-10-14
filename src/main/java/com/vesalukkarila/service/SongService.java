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

/**
 * Service class for managing Song entities.
 * This class provides methods to perform CRUD operations on songs,
 * including creating, reading, updating, patching, and deleting songs.
 * It interacts with the SongRepository for database operations
 * and includes validation logic for input data.
 */
@Service
public class SongService {
    private final SongRepository songRepository;

    /**
     * Constructs a new SongService with the specified SongRepository.
     *
     * @param songRepository, the repository used for song operations
     */
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    /**
     * Retrieves all songs from the repository.
     *
     * @return a list of all Song entities
     */
    @Transactional
    public List<Song> findAll() {
        return songRepository.findAll();
    }

    /**
     * Finds a song by its unique identifier.
     *
     * @param id, the unique identifier of the song
     * @return the Song entity associated with the given ID
     * @throws SongNotFoundException if the song with the specified ID does not exist
     */
    @Transactional
    public Song findById(String id) {
        return songRepository.findById(id);
    }

    /**
     * Creates a new song from the provided SongDto.
     *
     * @param songDto, the data transfer object containing song details
     * @return the created Song entity
     * @throws SongAlreadyExistsException if a song with the same name, artist, and publish year already exists
     */
    @Transactional
    public Song createSong(SongDto songDto) {
        Song song = new Song(songDto);
        song.setId(UUID.randomUUID());
        if (songRepository.songExists(song)) {
            throw new SongAlreadyExistsException(song);
        }
        songRepository.save(song);
        return song;
    }

    /**
     * Updates an existing song identified by its ID using the provided SongDto.
     *
     * @param id, the unique identifier of the song to be updated
     * @param songDto, the data transfer object containing updated song details
     * @return the updated Song entity
     * @throws SongNotFoundException if the song with the specified ID does not exist
     * @throws SongAlreadyExistsException if a song with the same name, artist, and publish year already exists
     */
    @Transactional
    public Song updateSong(String id, SongDto songDto) {
        Song song = this.findById(id);
        song.updateFields(songDto);
        if (songRepository.songExists(song)) {
            throw new SongAlreadyExistsException(song);
        }
        songRepository.update(song);
        return song;
    }

    /**
     * Applies partial updates to an existing song identified by its ID using the provided SongDto.
     *
     * @param id, the unique identifier of the song to be patched
     * @param songDto, the data transfer object containing fields to update
     * @return the patched song entity
     * @throws SongNotFoundException if the song with the specified ID does not exist
     * @throws EmptyPatchRequestException if no fields are provided for the patch operation
     * @throws SongAlreadyExistsException if a song with the same name, artist, and publish year already exists
     */
    @Transactional
    public Song patchSong(String id, SongDto songDto) {
        validatePatchFields(songDto);
        Song song = this.findById(id);
        song.updateFields(songDto);
        if (songRepository.songExists(song)) {
            throw new SongAlreadyExistsException(song);
        }
        songRepository.update(song);
        return song;
    }

    /**
     * Validates the fields in the provided SongDto for patch operations.
     *
     * @param songDto, the data transfer object containing song details to validate
     * @throws EmptyPatchRequestException if no fields are provided in the patch request
     */
    private void validatePatchFields(SongDto songDto) {
        if (songDto.getName() == null && songDto.getArtist() == null
                && songDto.getPublishYear() == null) {
            throw new EmptyPatchRequestException();
        }
    }

    /**
     * Deletes a song identified by its unique identifier.
     *
     * @param id, the unique identifier of the song to be deleted
     * @throws SongNotFoundException if the song with the specified ID does not exist
     */
    @Transactional
    public void deleteSong(String id) {
        if (!songRepository.delete(id)) {
            throw new SongNotFoundException(id);
        }
    }
}
