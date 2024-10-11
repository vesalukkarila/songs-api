package com.vesalukkarila.web.exception;

import com.vesalukkarila.dto.SongDto;

public class SongAlreadyExistsException extends RuntimeException{
    public SongAlreadyExistsException(SongDto songDto){
        super("Song with name: " + songDto.getName() + ", artist: "
                + songDto.getArtist() + " and publishYear: "
                + songDto.getPublishYear() + " already exists.");
    }
}
