package com.vesalukkarila.web.exception;

import com.vesalukkarila.model.Song;

public class SongAlreadyExistsException extends RuntimeException{
    public SongAlreadyExistsException(Song song){
        super("Song with name: " + song.getName() + ", artist: "
                + song.getArtist() + " and publishYear: "
                + song.getPublishYear() + " already exists.");
    }
}
