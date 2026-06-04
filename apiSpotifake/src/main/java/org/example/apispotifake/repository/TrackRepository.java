package org.example.apispotifake.repository;

import org.example.apispotifake.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findByTituloContainingIgnoreCaseOrArtistaNombreContainingIgnoreCase(String titulo, String artistaNombre);
    List<Track> findByArtistaId(Long artistaId);
    List<Track> findByAlbumId(Long albumId);
}
