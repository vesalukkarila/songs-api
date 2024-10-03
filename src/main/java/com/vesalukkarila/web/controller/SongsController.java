package com.vesalukkarila.web.controller;

import com.vesalukkarila.dto.SongDto;
import com.vesalukkarila.model.Song;
import com.vesalukkarila.service.SongService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController
public class SongsController {

    private final SongService songService;

    public SongsController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/")
    public Map<String, String> greeting() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from Songs API");
        return response;
    }


    @GetMapping("/songs")
    public List<Song> getSongs() {
        return songService.getSongs();
    }


    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable("id") String uuidStr) {
        try{
            if (!isValidUUID(uuidStr)){
                throw new IllegalArgumentException("Invalid UUID format: " + uuidStr);
            }
            Song song = songService.getSongById(uuidStr);
            return ResponseEntity.ok(song);         //found?? as responsestatus

        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping("/songs")
    public ResponseEntity<Song> createSong(@RequestBody @Valid SongDto songDto) {
        Song song = songService.createSong(
                songDto.getName(), songDto.getArtist(), songDto.getPublishYear());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/songs/" + song.getId()));

        return new ResponseEntity<>(song, headers, HttpStatus.CREATED);
    }

    private boolean isValidUUID(String uuidStr){
        String uuidRegex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        return Pattern.matches(uuidRegex, uuidStr);
    }
}
