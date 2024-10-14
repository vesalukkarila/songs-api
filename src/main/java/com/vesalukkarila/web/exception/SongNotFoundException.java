package com.vesalukkarila.web.exception;

/**
 * Exception thrown when a requested song cannot be found.
 * This indicates that there is no song associated with the given identifier.
 */
public class SongNotFoundException extends RuntimeException {
    /**
     * Constructs a new SongNotFoundException with a specified song identifier.
     *
     * @param id the identifier of the song that was not found
     */
    public SongNotFoundException(String id) {
        super("Song with id " + id + " was not found");
    }
}
