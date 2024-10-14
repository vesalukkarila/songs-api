package com.vesalukkarila.web.controller;

import com.vesalukkarila.dto.SongDto;
import com.vesalukkarila.model.Song;
import com.vesalukkarila.service.SongService;
import com.vesalukkarila.web.exception.InvalidUUIDException;
import com.vesalukkarila.web.validation.CreateOrPutGroup;
import com.vesalukkarila.web.validation.PatchGroup;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * REST controller for managing songs.
 * This controller provides endpoints for performing CRUD operations on songs,
 * including creating, reading, updating, and deleting songs. It uses the
 * SongService to handle business logic and data operations.
 */
@RestController
public class SongsController {

    private final SongService songService;

    /**
     * Constructs a new SongsController with the specified SongService.
     *
     * @param songService, the service used for song operations
     */
    public SongsController(SongService songService) {
        this.songService = songService;
    }

    /**
     * Returns a greeting message from the Songs API.
     *
     * @return a ResponseEntity containing a greeting message
     */
    @GetMapping("/")
    public ResponseEntity<Map<String, String>> greeting() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from Songs API");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves all songs.
     *
     * @return a ResponseEntity containing a list of all songs
     */
    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getSongs() {
        return new ResponseEntity<>(songService.findAll(), HttpStatus.OK);
    }

    /**
     * Retrieves a song by its unique identifier.
     *
     * @param id, the unique identifier of the song
     * @return a ResponseEntity containing the requested song
     * @throws InvalidUUIDException if the provided ID is not a valid UUID
     */
    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable("id") String id) {
        validateUUID(id);
        Song song = songService.findById(id);
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    /**
     * Creates a new song using the provided SongDto.
     *
     * @param songDto, the data transfer object containing song details
     * @return a ResponseEntity containing the created song and the location header
     */
    @PostMapping("/songs")
    public ResponseEntity<Song> createSong(
            @RequestBody @Validated(CreateOrPutGroup.class) SongDto songDto) {
        Song song = songService.createSong(songDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/songs/" + song.getId()));
        return new ResponseEntity<>(song, headers, HttpStatus.CREATED);
    }

    /**
     * Updates an existing song identified by its ID with the provided SongDto.
     *
     * @param id, the unique identifier of the song to be updated
     * @param songDto, the data transfer object containing updated song details
     * @return a ResponseEntity containing the updated song
     * @throws InvalidUUIDException if the provided ID is not a valid UUID
     */
    @PutMapping("/songs/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable("id") String id,
                                           @RequestBody @Validated(CreateOrPutGroup.class) SongDto songDto) {
        validateUUID(id);
        Song song = songService.updateSong(id, songDto);
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    /**
     * Applies partial updates to an existing song identified by its ID using the provided SongDto.
     *
     * @param id, the unique identifier of the song to be patched
     * @param songDto, the data transfer object containing fields to update
     * @return a ResponseEntity containing the patched song
     * @throws InvalidUUIDException if the provided ID is not a valid UUID
     */
    @PatchMapping("/songs/{id}")
    public ResponseEntity<Song> patchSong(@PathVariable("id") String id,
                                          @RequestBody @Validated(PatchGroup.class) SongDto songDto) {
        validateUUID(id);
        Song updatedSong = songService.patchSong(id, songDto);
        return new ResponseEntity<>(updatedSong, HttpStatus.OK);
    }

    /**
     * Deletes a song identified by its unique identifier.
     *
     * @param id, the unique identifier of the song to be deleted
     * @return a ResponseEntity indicating the deletion status
     * @throws InvalidUUIDException if the provided ID is not a valid UUID
     */
    @DeleteMapping("/songs/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable("id") String id) {
        validateUUID(id);
        songService.deleteSong(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Validates the format of the provided UUID.
     *
     * @param id, the UUID to validate
     * @throws InvalidUUIDException if the provided ID is not a valid UUID
     */
    private void validateUUID(String id) {
        String uuidRegex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-" +
                "[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        if (!Pattern.matches(uuidRegex, id)) {
            throw new InvalidUUIDException(id);
        }
    }
}
