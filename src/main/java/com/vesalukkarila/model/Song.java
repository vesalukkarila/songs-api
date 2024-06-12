package com.vesalukkarila.model;


public class Song {

    private String name, artist;
    private Integer year;

    public Song(String name, String artist, Integer year) {
        this.name = name;
        this.artist = artist;
        this.year = year;
    }


    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public Integer getYear() {
        return year;
    }
}
