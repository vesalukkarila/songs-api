package com.vesalukkarila.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vesalukkarila.web.validation.CreateOrPutGroup;
import com.vesalukkarila.web.validation.PatchGroup;
import com.vesalukkarila.web.validation.YearRange;
import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for a Song.
 * This class represents a song's data; name, artist and publish year.
 * Used to transfer data between layers, mainly from the controller to the service layer.
 * Validation annotations are provided to ensure that the data meets specific criteria.
 */
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

    /**
     * Default constructor. Initializes a new instance of the class.
     */
    public SongDto() {
    }

    /**
     * Initializes a new instance of the class with specified values.
     * @param name          The name of the song.
     * @param artist        The artist of the song.
     * @param publishYear   The publish year of the song.
     */
    public SongDto(String name, String artist, Integer publishYear) {
        this.name = name;
        this.artist = artist;
        this.publishYear = publishYear;
    }

    /**
     * Gets the name of the song.
     * @return The name of the song.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the artist of the song.
     * @return The artist of the song.
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Gets the publish year of the song.
     *
     * @return The publish year of the song.
     */
    public Integer getPublishYear() {
        return publishYear;
    }

    /**
     * Sets the name of the song.
     *
     * @param name The name of the song.
     */
    public void setName(String name) {
        this.name = name.trim();
    }

    /**
     * Sets the artist of the song.
     *
     * @param artist The artist of the song.
     */
    public void setArtist(String artist) {
        this.artist = artist.trim();
    }

    /**
     * Sets the publish year of the song.
     *
     * @param publishYear The publish year of the song.
     */
    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }
}
