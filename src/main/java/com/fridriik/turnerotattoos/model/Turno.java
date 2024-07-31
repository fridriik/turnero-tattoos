package com.fridriik.turnerotattoos.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "turnos")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tatuador_id")
    private Tatuador tatuador;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private LocalDateTime fechaHora;
    private boolean reservado;

    @ManyToOne
    @JoinColumn(name = "sesion_id")
    private Sesion sesion;
}