package org.example.apispotifake.config;

import org.example.apispotifake.model.Track;
import org.example.apispotifake.model.Artista;
import org.example.apispotifake.model.Album;
import org.example.apispotifake.repository.AlbumRepository;
import org.example.apispotifake.repository.TrackRepository;
import org.example.apispotifake.repository.ArtistaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class TestDataInitializer {

    @Bean
    CommandLineRunner initTestData(TrackRepository trackRepo, ArtistaRepository artistaRepo, AlbumRepository albumRepo) {
        return args -> {
            if (trackRepo.count() > 10) return; // Ya hay datos

            Artista genericArtist = new Artista();
            genericArtist.setNombre("Spotifake AI");
            genericArtist.setBiografia("Generador automático de sonidos para pruebas.");
            genericArtist = artistaRepo.save(genericArtist);

            Album genericAlbum = new Album();
            genericAlbum.setTitulo("Pruebas Unitarias");
            genericAlbum.setArtista(genericArtist);
            genericAlbum.setFechaLanzamiento(LocalDate.now());
            genericAlbum.setPortadaUrl("https://via.placeholder.com/300/1DB954/FFFFFF?text=Spotifake");
            genericAlbum = albumRepo.save(genericAlbum);

            for (int i = 1; i <= 50; i++) {
                Track track = new Track();
                track.setTitulo("Pista de Prueba #" + i);
                track.setArtista(genericArtist);
                track.setAlbum(genericAlbum);
                track.setDuracion(180 + i);
                // Imagen aleatoria de Lorem Picsum
                track.setPortadaUrl("https://picsum.photos/300?random=" + i);
                // Usamos SoundHelix como fuente de streaming gratuita
                int helixId = (i % 10) + 1; 
                track.setStreamUrl("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-" + helixId + ".mp3");
                trackRepo.save(track);
            }
            
            System.out.println(">>> 50 pistas de prueba (Álbum 1) cargadas correctamente.");

            // Segundo Álbum: Atmósferas y Naturaleza (No SoundHelix)
            Artista natureArtist = new Artista();
            natureArtist.setNombre("Ambient Creator");
            natureArtist.setBiografia("Especialista en sonidos ambientales y de la naturaleza.");
            natureArtist = artistaRepo.save(natureArtist);

            Album natureAlbum = new Album();
            natureAlbum.setTitulo("Atmósferas & Naturaleza");
            natureAlbum.setArtista(natureArtist);
            natureAlbum.setFechaLanzamiento(LocalDate.now().minusMonths(1));
            natureAlbum.setPortadaUrl("https://picsum.photos/300?random=200");
            natureAlbum = albumRepo.save(natureAlbum);

            for (int i = 1; i <= 50; i++) {
                Track track = new Track();
                track.setTitulo("Sonido Ambiental #" + i);
                track.setArtista(natureArtist);
                track.setAlbum(natureAlbum);
                track.setDuracion(60 + i);
                track.setPortadaUrl("https://picsum.photos/300?random=" + (i + 100));
                // Usamos un set diferente de SoundHelix (IDs 11 a 16) para evitar overlap con el primer álbum
                int helixId = (i % 6) + 11; 
                track.setStreamUrl("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-" + helixId + ".mp3");
                trackRepo.save(track);
            }
            
            System.out.println(">>> 50 pistas adicionales (Álbum 2: Atmósferas) cargadas correctamente.");

            // Tercer Álbum: Rock Essentials
            Artista rockArtist = new Artista();
            rockArtist.setNombre("The Gemi-Rocks");
            rockArtist.setBiografia("Banda legendaria de rock alternativo con sonidos potentes.");
            rockArtist = artistaRepo.save(rockArtist);

            Album rockAlbum = new Album();
            rockAlbum.setTitulo("Rock Essentials");
            rockAlbum.setArtista(rockArtist);
            rockAlbum.setFechaLanzamiento(LocalDate.now().minusYears(1));
            rockAlbum.setPortadaUrl("https://picsum.photos/300?random=300");
            rockAlbum = albumRepo.save(rockAlbum);

            for (int i = 1; i <= 50; i++) {
                Track track = new Track();
                track.setTitulo("Rock Anthem #" + i);
                track.setArtista(rockArtist);
                track.setAlbum(rockAlbum);
                track.setDuracion(210 + i);
                track.setPortadaUrl("https://picsum.photos/300?random=" + (i + 200));
                // Usamos SoundHelix IDs 7 a 10 para el Rock (para variar de los otros álbumes y ser seguros)
                int helixId = (i % 4) + 7; 
                track.setStreamUrl("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-" + helixId + ".mp3");
                trackRepo.save(track);
            }
            
            System.out.println(">>> 50 pistas adicionales (Álbum 3: Rock) cargadas correctamente.");
        };
    }
}
