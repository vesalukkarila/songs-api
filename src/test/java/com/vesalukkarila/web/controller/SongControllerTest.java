package com.vesalukkarila.web.controller;

import com.vesalukkarila.model.Song;
import com.vesalukkarila.service.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
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
    public void shouldReturnGreetingMessage(){
        Map<String, String> response= songsController.greeting();
        assertNotNull(response);
        assertEquals(response.get("message"), "Hello from Songs API");
    }

    @Nested
    public class GetSongsTests{

        @Test
        public void shouldReturnEmptyListWhenNoSongsAvailable(){
            List<Song> songs = new ArrayList<>();
            when(songService.findAll()).thenReturn(songs);
            List<Song> response = songsController.getSongs();
            assertEquals(0, response.size());
        }

        @Test
        public void shouldReturnOneSongWhenOneSongAvailable(){
            List<Song> songs = List.of(
                    new Song("The Thrill Is Gone", "B.B. King", 1969));
            when(songService.findAll()).thenReturn(songs);
            List<Song> response = songsController.getSongs();
            assertEquals(1, response.size());
            assertEquals("The Thrill Is Gone", response.get(0).getName());
            assertEquals("B.B. King", response.get(0).getArtist());
            assertEquals(1969, response.get(0).getPublishYear());
        }

        @Test
        public void shouldReturnTwoSongsWhenTwoSongsAvailable(){
            List<Song> songs = Arrays.asList(
                    new Song("The Thrill Is Gone", "B.B. King", 1969),
                    new Song("Sweet Home Chicago", "Robert Johnson", 1936));
            when(songService.findAll()).thenReturn(songs);
            List<Song> response = songsController.getSongs();
            assertEquals(2, response.size());
            assertEquals("The Thrill Is Gone", response.get(0).getName());
            assertEquals("B.B. King", response.get(0).getArtist());
            assertEquals(1969, response.get(0).getPublishYear());
            assertEquals("Sweet Home Chicago", response.get(1).getName());
            assertEquals("Robert Johnson", response.get(1).getArtist());
            assertEquals(1936, response.get(1).getPublishYear());
        }
    }


}
