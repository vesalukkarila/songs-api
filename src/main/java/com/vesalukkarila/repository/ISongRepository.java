package com.vesalukkarila.repository;

import com.vesalukkarila.model.Song;

import java.util.List;

/**
 * Interface representing the repository for managing song entities.
 * This interface defines the operations for retrieving, saving, updating, and deleting
 * songs in the underlying data storage. Implementing classes provides the actual
 * data access logic.
 */
public interface ISongRepository {

    /**
     * Retrieves all songs from the repository.
     *
     * @return a list of all song entities
     */
    List<Song> findAll();

    /**
     * Finds a song by its unique identifier.
     *
     * @param id, the unique identifier of the song
     * @return the song entity associated with the given id, or null if not found
     */
    Song findById(String id);

    /**
     * Saves a new song to the repository.
     *
     * @param song, the song entity to be saved
     */
    void save(Song song);

    /**
     * Updates an existing song in the repository.
     *
     * @param song, the song entity containing updated information
     */
    void update(Song song);

    /**
     * Deletes a song from the repository by its unique identifier.
     *
     * @param id, the unique identifier of the song to be deleted
     * @return true if the song was successfully deleted, false otherwise
     */
    boolean delete(String id);
}
