package com.vesalukkarila.web;

public class SongNotFoundException extends RuntimeException{
    public SongNotFoundException(String id){
        super("Song with id " + id + " was not found");
    }
}
