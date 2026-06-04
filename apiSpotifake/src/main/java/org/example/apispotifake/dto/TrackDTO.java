package org.example.apispotifake.dto;

import lombok.Data;

@Data
public class TrackDTO {
    private Long id;
    private String titulo;
    private Long artistaId;
    private String artistaNombre;
    private String albumTitulo;
    private String portadaUrl;
    private String streamUrl;
}
