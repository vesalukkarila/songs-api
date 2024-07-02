package com.vesalukkarila.dto;

public class SongDto {
    private String name, artist;
    private Integer publishYear;

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public Integer getPublishYear() {
        return publishYear;
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
}
