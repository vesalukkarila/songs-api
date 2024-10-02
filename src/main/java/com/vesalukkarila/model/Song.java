package com.vesalukkarila.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Song {

    private UUID id;
    private String name, artist;
    @JsonProperty("publish_year")
    private Integer publishYear;

    public Song() {
    }

    public Song(String name, String artist, Integer publishYear) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.artist = artist;
        this.publishYear = publishYear;
    }

    public void setId(UUID id) {
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

    public UUID getId() {
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
