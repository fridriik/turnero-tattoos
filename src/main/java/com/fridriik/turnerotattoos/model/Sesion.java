package com.fridriik.turnerotattoos.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "sesiones")
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tatuaje_id")
    private Tatuaje tatuaje;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "tatuador_id")
    private Tatuador tatuador;

    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    @OneToMany(mappedBy = "sesion")
    private List<Turno> turnos;

    private String trabajoRealizado;
    private BigDecimal costo;
}