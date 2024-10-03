package com.vesalukkarila.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vesalukkarila.web.validation.YearRange;
import jakarta.validation.constraints.NotBlank;

public class SongDto {
    @NotBlank
    private String name;
    @NotBlank
    private String artist;
    @JsonProperty("publish_year")
    @YearRange
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
