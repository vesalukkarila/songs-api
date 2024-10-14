package com.vesalukkarila.context;

import com.vesalukkarila.dto.SongDto;
import com.vesalukkarila.service.SongService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * SongInitializer is responsible for initializing the application with default song data
 * when the Spring application context is refreshed.
 * This component listens for the ContextRefreshedEvent and checks if the song
 * repository is empty. If it is empty, it adds a default song entry.
 */
@Component
public class SongInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private final SongService songService;

    /**
     * Constructs a new SongInitializer with the specified SongService.
     *
     * @param songService the SongService to be used for creating default songs
     */
    public SongInitializer(SongService songService) {
        this.songService = songService;
    }

    /**
     * Invoked when the application context is refreshed.
     * This method checks if there are any songs in the database. If no songs exist,
     * it creates a default song with the title "Thunderstruck" by AC/DC, released in 1990.
     *
     * @param event the event containing the context refresh information
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (songService.findAll().isEmpty()) {
            SongDto songDto = new SongDto("Thunderstruck", "AC/DC", 1990);
            songService.createSong(songDto);
        }
    }
}
