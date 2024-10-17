package com.vesalukkarila.web.exception;

import com.vesalukkarila.model.Song;

/**
 * Exception thrown when an attempt is made to create a song
 * that already exists in the system.
 */
public class SongAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new SongAlreadyExistsException with details of the existing song.
     *
     * @param song the song that already exists, which caused the exception
     */
    public SongAlreadyExistsException(Song song) {
        super("Song with provided name(" + song.getName() + "), artist("
                + song.getArtist() + ") and publish_year("
                + song.getPublishYear() + ") already exists.");
    }
}
