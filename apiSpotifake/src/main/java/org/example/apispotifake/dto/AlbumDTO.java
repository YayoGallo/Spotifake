package org.example.apispotifake.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class AlbumDTO {
    private Long id;
    private String titulo;
    private LocalDate fechaLanzamiento;
    private String portadaUrl;
    private String artistaNombre;
    private List<TrackDTO> tracks;
}
