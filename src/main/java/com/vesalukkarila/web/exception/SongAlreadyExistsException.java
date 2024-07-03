package com.vesalukkarila.web.exception;

public class SongAlreadyExistsException extends RuntimeException{
    public SongAlreadyExistsException(String name, String artist, Integer publishYear){
        super("Song with name: " + name + ", artist: "
                + artist + " and publishYear: " + publishYear + " already exists.");
    }
}
