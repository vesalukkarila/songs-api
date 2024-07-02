package com.vesalukkarila.web;

import com.vesalukkarila.dto.SongDto;
import com.vesalukkarila.model.Song;
import com.vesalukkarila.service.SongService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Song getSongById(@PathVariable("id") String id){
        return songService.getSongById(id);
    }

    @PostMapping("/songs")
    public ResponseEntity<Song> createSong(@RequestBody SongDto songDto){
        Song song = songService.createSong(
                songDto.getName(), songDto.getArtist(), songDto.getPublishYear());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/songs/" + song.getId()));

        return new ResponseEntity<>(song, headers, HttpStatus.CREATED);
    }
}
