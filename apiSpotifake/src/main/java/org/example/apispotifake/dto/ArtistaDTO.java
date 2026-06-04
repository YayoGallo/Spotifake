package org.example.apispotifake.dto;

import lombok.Data;
import java.util.List;

@Data
public class ArtistaDTO {
    private Long id;
    private String nombre;
    private String biografia;
    private String fotoUrl;
    private List<AlbumDTO> albums;
    private List<TrackDTO> tracks;
}
