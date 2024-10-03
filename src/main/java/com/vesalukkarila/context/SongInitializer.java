package com.vesalukkarila.context;

import com.vesalukkarila.service.SongService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SongInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private final SongService songService;

    public SongInitializer(SongService songService) {
        this.songService = songService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (songService.getSongs().isEmpty()) {
            songService.createSong("Thunderstruck", "AC/DC", 1990);
        }
    }
}
