package com.vesalukkarila.web.controller;

import com.vesalukkarila.model.Song;
import com.vesalukkarila.service.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SongControllerTest {

    @Mock
    private SongService songService;
    @InjectMocks
    private SongsController songsController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGreeting(){
        Map<String, String> response= songsController.greeting();
        assertEquals(response.get("message"), "Hello from Songs API");
    }

    @Test
    public void testGetSongs(){
        List<Song> songs = List.of(new Song("Name", "Artist", 1985));
        when(songService.findAll()).thenReturn(songs);
        List<Song> response = songsController.getSongs();
        assertEquals(1, response.size());
        assertEquals("Name", response.get(0).getName());
    }
}
