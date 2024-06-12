package com.vesalukkarila.web;

import com.vesalukkarila.model.Song;
import com.vesalukkarila.service.SongService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SongsController {

    private SongService songService;

    public SongsController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/")
    public String greeting() {
        return "Hello world from SongsController";
    }

    @GetMapping("/songs")
    public List<Song> getSongs() {
        return songService.getSongs();
    }
}
