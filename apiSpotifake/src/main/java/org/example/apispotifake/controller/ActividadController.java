package org.example.apispotifake.controller;

import org.example.apispotifake.dto.TrackDTO;
import org.example.apispotifake.model.Track;
import org.example.apispotifake.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import org.example.apispotifake.dto.ArtistaDTO;
import org.example.apispotifake.model.Artista;
import org.example.apispotifake.repository.ArtistaRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;

import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpRange;

@RestController
@RequestMapping("/actividad")
public class ActividadController {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private org.example.apispotifake.repository.AlbumRepository albumRepository;

    @GetMapping("/stream/{fileName:.+}")
    public ResponseEntity<ResourceRegion> streamAudio(@PathVariable String fileName, @RequestHeader HttpHeaders headers) throws java.io.IOException {
        Resource audio = new ClassPathResource("audio/" + fileName);
        if (!audio.exists()) {
            return ResponseEntity.notFound().build();
        }

        long contentLength = audio.contentLength();
        HttpRange range = headers.getRange().isEmpty() ? null : headers.getRange().get(0);
        
        ResourceRegion region;
        if (range != null) {
            long start = range.getRangeStart(contentLength);
            long end = range.getRangeEnd(contentLength);
            long rangeLength = Math.min(1024 * 1024L, end - start + 1); // 1MB chunks
            region = new ResourceRegion(audio, start, rangeLength);
        } else {
            long rangeLength = Math.min(1024 * 1024L, contentLength);
            region = new ResourceRegion(audio, 0, rangeLength);
        }

        String contentType = fileName.endsWith(".wav") ? "audio/wav" : "audio/mpeg";

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaType.parseMediaType(contentType))
                .body(region);
    }

    @GetMapping("/recomendaciones")
    public ResponseEntity<List<TrackDTO>> getRecomendaciones() {
        List<Track> tracks = trackRepository.findAll();
        return ResponseEntity.ok(mapToDTO(tracks));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<TrackDTO>> buscar(@org.springframework.web.bind.annotation.RequestParam String query) {
        List<Track> tracks = trackRepository.findByTituloContainingIgnoreCaseOrArtistaNombreContainingIgnoreCase(query, query);
        return ResponseEntity.ok(mapToDTO(tracks));
    }

    @GetMapping("/artista/{id}")
    public ResponseEntity<ArtistaDTO> getArtista(@PathVariable Long id) {
        Artista artista = artistaRepository.findById(id).orElse(null);
        if (artista == null) return ResponseEntity.notFound().build();

        ArtistaDTO dto = new ArtistaDTO();
        dto.setId(artista.getId());
        dto.setNombre(artista.getNombre());
        dto.setBiografia(artista.getBiografia());
        dto.setFotoUrl(artista.getFotoUrl());
        
        List<org.example.apispotifake.model.Album> albums = albumRepository.findByArtistaId(id);
        dto.setAlbums(albums.stream().map(this::mapAlbumToDTO).collect(Collectors.toList()));
        
        dto.setTracks(mapToDTO(trackRepository.findByArtistaId(id)));

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/album/{id}")
    public ResponseEntity<org.example.apispotifake.dto.AlbumDTO> getAlbum(@PathVariable Long id) {
        org.example.apispotifake.model.Album album = albumRepository.findById(id).orElse(null);
        if (album == null) return ResponseEntity.notFound().build();

        org.example.apispotifake.dto.AlbumDTO dto = mapAlbumToDTO(album);
        dto.setTracks(mapToDTO(trackRepository.findByAlbumId(id)));
        return ResponseEntity.ok(dto);
    }

    private org.example.apispotifake.dto.AlbumDTO mapAlbumToDTO(org.example.apispotifake.model.Album album) {
        org.example.apispotifake.dto.AlbumDTO dto = new org.example.apispotifake.dto.AlbumDTO();
        dto.setId(album.getId());
        dto.setTitulo(album.getTitulo());
        dto.setFechaLanzamiento(album.getFechaLanzamiento());
        dto.setPortadaUrl(album.getPortadaUrl());
        dto.setArtistaNombre(album.getArtista() != null ? album.getArtista().getNombre() : "Artista Desconocido");
        return dto;
    }

    private List<TrackDTO> mapToDTO(List<Track> tracks) {
        String baseUrl = org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return tracks.stream().map(t -> {
            TrackDTO dto = new TrackDTO();
            dto.setId(t.getId());
            dto.setTitulo(t.getTitulo());
            dto.setArtistaId(t.getArtista() != null ? t.getArtista().getId() : null);
            dto.setArtistaNombre(t.getArtista() != null ? t.getArtista().getNombre() : "Artista Desconocido");

            if (t.getPortadaUrl() != null && !t.getPortadaUrl().isEmpty()) {
                dto.setPortadaUrl(t.getPortadaUrl());
            } else if (t.getAlbum() != null) {
                dto.setPortadaUrl(t.getAlbum().getPortadaUrl());
            } else {
                dto.setPortadaUrl("");
            }
            if (t.getAlbum() != null) {
                dto.setAlbumTitulo(t.getAlbum().getTitulo());
            } else {
                dto.setAlbumTitulo("Sencillo");
            }
            String streamUrl = t.getStreamUrl();
            if (streamUrl != null && streamUrl.startsWith("/")) {
                dto.setStreamUrl(baseUrl + streamUrl);
            } else {
                dto.setStreamUrl(streamUrl);
            }
            return dto;
        }).collect(Collectors.toList());
    }
}
