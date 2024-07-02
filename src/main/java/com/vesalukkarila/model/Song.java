package com.vesalukkarila.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Song {

    // TODO: 1st step; db will have autoincremented int-id for starters, this class has random string for now.
    //  Find out how db could use that or something similar too, problem was with retrieving uuid from db with
    //  simplejdbcinsert.executeandreturnkey method -> returned Number
    private String id, name, artist;
    private Integer publishYear;

    public Song() {
    }

    public Song(String name, String artist, Integer publishYear) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.artist = artist;
        this.publishYear = publishYear;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

}
