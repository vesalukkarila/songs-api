package com.vesalukkarila.web.controller;

import com.vesalukkarila.dto.SongDto;
import com.vesalukkarila.model.Song;
import com.vesalukkarila.service.SongService;
import com.vesalukkarila.web.exception.InvalidUUIDException;
import com.vesalukkarila.web.exception.SongAlreadyExistsException;
import com.vesalukkarila.web.exception.SongNotFoundException;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
        ResponseEntity<Map<String, String>> response = songsController.greeting();
        assertNotNull(response.getBody());
        assertEquals(response.getBody().get("message"), "Hello from Songs API");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Nested
    public class GetSongsTests{

        @Test
        public void shouldReturnEmptyListWhenNoSongsAvailable(){
            List<Song> songs = new ArrayList<>();
            when(songService.findAll()).thenReturn(songs);
            ResponseEntity<List<Song>> response = songsController.getSongs();
            assertEquals(0, Objects.requireNonNull(response.getBody()).size());
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        public void shouldReturnOneSongWhenOneSongAvailable(){
            List<Song> songs = List.of(
                    new Song("The Thrill Is Gone", "B.B. King", 1969));
            when(songService.findAll()).thenReturn(songs);
            ResponseEntity<List<Song>> response = songsController.getSongs();
            assertEquals(1, Objects.requireNonNull(response.getBody()).size());
            assertEquals("The Thrill Is Gone", response.getBody().get(0).getName());
            assertEquals("B.B. King", response.getBody().get(0).getArtist());
            assertEquals(1969, response.getBody().get(0).getPublishYear());
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        public void shouldReturnTwoSongsWhenTwoSongsAvailable(){
            List<Song> songs = Arrays.asList(
                    new Song("The Thrill Is Gone", "B.B. King", 1969),
                    new Song("Sweet Home Chicago", "Robert Johnson", 1936));
            when(songService.findAll()).thenReturn(songs);
            ResponseEntity<List<Song>> response = songsController.getSongs();
            assertEquals(2, Objects.requireNonNull(response.getBody()).size());
            assertEquals("The Thrill Is Gone", response.getBody().get(0).getName());
            assertEquals("B.B. King", response.getBody().get(0).getArtist());
            assertEquals(1969, response.getBody().get(0).getPublishYear());
            assertEquals("Sweet Home Chicago", response.getBody().get(1).getName());
            assertEquals("Robert Johnson", response.getBody().get(1).getArtist());
            assertEquals(1936, response.getBody().get(1).getPublishYear());
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }

    @Nested
    public class GetSongByIdTests {

        @Test
        public void shouldThrowInvalidUUIDExceptionWhenRequestedWithInvalidSongId(){
            String invalidId = "12345678-1234-1234-1234-incorrect";
            assertThrows(InvalidUUIDException.class,
                    () ->songsController.getSongById(invalidId));
        }

        @Test
        public void shouldThrowSongNotFoundExceptionWhenRequestedWithNonExistingSongId(){
            String validNonExistentId = "12345678-1234-1234-1234-12345678af12";
            when(songService.findById(validNonExistentId)).thenThrow(SongNotFoundException.class);
            assertThrows(SongNotFoundException.class,
                    () -> songsController.getSongById(validNonExistentId));
        }

        @Test
        public void shouldReturnRequestedSongWhenExistentSongRequested() {
            String validExistentId = "12345678-1234-1234-1234-123456781112";
            Song song = new Song("The Thrill Is Gone", "B.B. King", 1969);
            song.setId(UUID.fromString(validExistentId));
            when(songService.findById(validExistentId)).thenReturn(song);
            ResponseEntity<Song> response = songsController.getSongById(validExistentId);
            assertEquals(song, response.getBody());
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }

    @Nested
    public class CreateSongTests{

        @Test
        public void shouldThrowSongAlreadyExistsException(){
            SongDto songDto = new SongDto("The Thrill Is Gone", "B.B. King", 1969);
            when(songService.createSong(songDto)).thenThrow(SongAlreadyExistsException.class);
            assertThrows(SongAlreadyExistsException.class, () -> songsController.createSong(songDto));
        }

        @Test
        public void shouldCreateSongAndReturnCreatedStatus(){
            SongDto songDto = new SongDto("The Thrill Is Gone", "B.B. King", 1969);
            Song song = new Song("The Thrill Is Gone", "B.B. King", 1969);
            when(songService.createSong(songDto)).thenReturn(song);
            ResponseEntity<Song> response = songsController.createSong(songDto);
            assertEquals(song, response.getBody());
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            response.getHeaders().getLocation();
        }
    }

    @Nested
    public class UpdateSongTests{

        @Test
        public void shouldThrowInvalidUUIDExceptionWhenRequestedWithInvalidSongId(){
            String invalidId = "12345678-1234-1234-1234-incorrect";
            SongDto songDto = new SongDto("The Thrill is Gone", "B.B. King", 1969);
            assertThrows(InvalidUUIDException.class,
                    () ->songsController.updateSong(invalidId, songDto));
        }

        @Test
        public void shouldUpdateSongAndReturnUpdatedResourceAndStatusOk(){
            String id = "12345678-1234-1234-1234-123456781112";
            SongDto songDto = new SongDto("The Thrill is Gone", "B.B. King", 1969);
            Song song = new Song("The Thrill Is Gone", "B.B. King", 1969);
            when(songService.updateSong(id, songDto)).thenReturn(song);
            ResponseEntity<Song> response = songsController.updateSong(id, songDto);
            assertEquals(song, response.getBody());
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }

    @Nested
    public class PatchSongTests{

        @Test
        public void shouldThrowInvalidUUIDExceptionWhenRequestedWithInvalidSongId(){
            String invalidId = "12345678-1234-1234-1234-incorrect";
            SongDto songDto = new SongDto("The Thrill is Gone", "B.B. King", 1969);
            assertThrows(InvalidUUIDException.class,
                    () ->songsController.patchSong(invalidId, songDto));
        }

        @Test
        public void shouldPatchSongAndReturnPatchedResourceAndStatusOk(){
            String validExistentId = "12345678-1234-1234-1234-123456781112";
            SongDto songDto = new SongDto();
            songDto.setName("The Thrill is Not Gone");
            Song song = new Song("The Thrill Is Not Gone", "B.B. King", 1969);
            when(songService.patchSong(validExistentId, songDto)).thenReturn(song);
            ResponseEntity<Song> response = songsController.patchSong(validExistentId, songDto);
            assertEquals(song, response.getBody());
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }

    @Nested
    public class DeleteSongTests{

        @Test
        public void shouldThrowInvalidUUIDExceptionWhenRequestedWithInvalidSongId(){
            String invalidId = "12345678-1234-1234-1234-incorrect";
            assertThrows(InvalidUUIDException.class,
                    () ->songsController.deleteSong(invalidId));
        }

        @Test
        public void shouldReturnStatusCodeNoContent(){
            String validExistentId = "12345678-1234-1234-1234-123456781112";
            ResponseEntity<Void> response = songsController.deleteSong(validExistentId);
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        }
    }



/*TODO:
   Questions:
   - naming testmethods
   - should status code tests be in separate methods
   - How to divide tests of one class when size expands like in this one
   - testing status codes in separate unit (throws..) or integration tests or both?
   - validation tests easier in integration tests i believe
   - also exceptions would be easier to test in integration tests i think?*/






















}
