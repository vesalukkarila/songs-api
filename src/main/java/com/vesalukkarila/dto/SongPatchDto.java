package com.vesalukkarila.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vesalukkarila.model.Song;
import org.springframework.stereotype.Component;

public class SongPatchDto {
    private String name, artist;
    @JsonProperty("publish_year")
    private Integer publishYear;

    public void setName(String name) {
        this.name = (name != null) ? name.trim() : null;
    }

    public void setArtist(String artist) {
        this.artist = (artist != null) ? artist.trim() : null;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
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

    public Song updateFields(Song existingSong) {
        if (this.getName() != null){
            existingSong.setName(this.getName());
        }
        if (this.getArtist() != null){
            existingSong.setArtist(this.getArtist());
        }
        if (this.getPublishYear() != null){
            existingSong.setPublishYear(this.getPublishYear());
        }
        return existingSong;
    }
}
