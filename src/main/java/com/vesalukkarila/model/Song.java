package com.vesalukkarila.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vesalukkarila.dto.SongDto;
import java.util.UUID;

/**
 * Represents a song in the database.
 * This class holds information about a song: ID, name, artist, and publish year.
 * It provides methods to access and modify these properties.
 */
public class Song {

    private UUID id;
    private String name, artist;

    @JsonProperty("publish_year")
    private Integer publishYear;

    /**
     * Default constructor for creating a new instance of the Song class.
     */
    public Song() {
    }

    /**
     * Constructs a new Song instance from a SongDto.
     *
     * @param songDto the data transfer object containing song information
     */
    public Song(SongDto songDto) {
        this.name = songDto.getName();
        this.artist = songDto.getArtist();
        this.publishYear = songDto.getPublishYear();
    }

    /**
     * Constructs a new Song instance with the specified name, artist, and publish year.
     *
     * @param name        the name of the song
     * @param artist      the artist of the song
     * @param publishYear the publish year of the song
     */
    public Song(String name, String artist, Integer publishYear) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.artist = artist;
        this.publishYear = publishYear;
    }

    /**
     * Sets the ID of the song.
     *
     * @param id the unique identifier for the song
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Sets the name of the song.
     *
     * @param name the name of the song
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the artist of the song.
     *
     * @param artist the artist of the song
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Sets the publish year of the song.
     *
     * @param publishYear the publish year of the song
     */
    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    /**
     * Gets the unique identifier of the song.
     *
     * @return the ID of the song
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets the name of the song.
     *
     * @return the name of the song
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the artist of the song.
     *
     * @return the artist of the song
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Gets the publish year of the song.
     *
     * @return the publish year of the song
     */
    public Integer getPublishYear() {
        return publishYear;
    }

    /**
     * Updates the song's fields based on the provided SongDto.
     * This method will only update fields that are non-null and non-blank in the provided DTO.
     *
     * @param songDto the data transfer object containing updated song information
     */
    public void updateFields(SongDto songDto) {
        if (songDto.getName() != null && !songDto.getName().isBlank()) {
            this.name = songDto.getName();
        }
        if (songDto.getArtist() != null && !songDto.getArtist().isBlank()) {
            this.artist = songDto.getArtist();
        }
        if (songDto.getPublishYear() != null) {
            this.publishYear = songDto.getPublishYear();
        }
    }
}
