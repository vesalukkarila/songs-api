package com.vesalukkarila.web.exception;

public class SongNotFoundException extends RuntimeException{
    public SongNotFoundException(String id){
        super("Song with id " + id + " was not found");
    }
}
