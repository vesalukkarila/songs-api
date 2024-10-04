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
        return songService.findAll();
    }


    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable("id") String id) {
        validateUUID(id);
        Song song = songService.findById(id);
        return new ResponseEntity<>(song, HttpStatus.OK);
    }


    @PostMapping("/songs")
    public ResponseEntity<Song> createSong(@RequestBody
                                               @Validated(CreateOrPutGroup.class)
                                               SongDto songDto) {
        Song song = songService.createSong(songDto.getName(),
                songDto.getArtist(), songDto.getPublishYear());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/songs/" + song.getId()));
        return new ResponseEntity<>(song, headers, HttpStatus.CREATED);
    }


    @PutMapping("/songs/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable("id") String id,
                                           @RequestBody
                                           @Validated(CreateOrPutGroup.class)
                                           SongDto songDto){
        validateUUID(id);
        Song song = this.songService.updateSong(id, songDto.getName(),
                songDto.getArtist(), songDto.getPublishYear());
        return new ResponseEntity<>(song, HttpStatus.OK);
    }


    @PatchMapping("/songs/{id}")
    public ResponseEntity<Song> patchSong(@PathVariable("id") String id,
                                          @RequestBody
                                          @Validated(PatchGroup.class)
                                          SongDto songDto){
        validateUUID(id);
        Song song = this.songService.findById(id);
        song.updateFields(songDto);
        Song updatedSong = this.songService.patchSong(
                id, song.getName(), song.getArtist(), song.getPublishYear());
        return new ResponseEntity<>(updatedSong, HttpStatus.OK);
    }


    @DeleteMapping("/songs/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable("id") String id){
        validateUUID(id);
        this.songService.deleteSong(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    private void validateUUID(String id){
        String uuidRegex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-" +
                "[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        if(!Pattern.matches(uuidRegex, id)){
            throw new InvalidUUIDException(id);
        }
    }
}
