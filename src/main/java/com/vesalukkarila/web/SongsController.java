package com.vesalukkarila.web;

import com.vesalukkarila.dto.SongDto;
import com.vesalukkarila.model.Song;
import com.vesalukkarila.service.SongService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/songs")
    public Song createSong(@RequestBody SongDto songDto){
        return songService.createSong(songDto.getName(), songDto.getArtist(), songDto.getPublishYear());
    }
}
