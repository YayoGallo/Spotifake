package org.example.apispotifake.model;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "albums")
@Data
public class Album implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private LocalDate fechaLanzamiento;
    private String portadaUrl;

    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artista artista;
}
