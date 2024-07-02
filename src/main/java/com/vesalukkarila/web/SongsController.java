package com.vesalukkarila.web;

import com.vesalukkarila.model.Song;
import com.vesalukkarila.service.SongService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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


    // TODO: add validation for path variables
    @PostMapping("/songs/{name}/{artist}/{publishYear}")
    public Song createSong(@PathVariable ("name") String name,
                           @PathVariable ("artist") String artist,
                           @PathVariable ("publishYear") Integer publishYear) {
        return songService.createSong(name, artist, publishYear);
    }
}
