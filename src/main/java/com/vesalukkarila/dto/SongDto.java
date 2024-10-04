package com.vesalukkarila.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vesalukkarila.model.Song;
import com.vesalukkarila.web.validation.CreateOrPutGroup;
import com.vesalukkarila.web.validation.PatchGroup;
import com.vesalukkarila.web.validation.YearRange;
import jakarta.validation.constraints.*;

public class SongDto {
    @NotBlank(groups = CreateOrPutGroup.class)
    @Size(min = 1, groups = PatchGroup.class, message = "Name must not be blank when provided")
    private String name;

    @NotBlank(groups = CreateOrPutGroup.class)
    @Size(min = 1, groups = PatchGroup.class, message = "Artist must not be blank when provided")
    private String artist;

    @JsonProperty("publish_year")
    @NotNull(groups = CreateOrPutGroup.class)
    @YearRange(groups = {CreateOrPutGroup.class, PatchGroup.class})
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
        this.name = name.trim();
    }


    public void setArtist(String artist) {
        this.artist = artist.trim();
    }


    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
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
