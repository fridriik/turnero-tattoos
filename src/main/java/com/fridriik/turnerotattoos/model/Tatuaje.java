package com.fridriik.turnerotattoos.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "tatuajes")
public class Tatuaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "tatuador_principal_id")
    private Tatuador tatuadorPrincipal;

    private String descripcion;
    private String ubicacionCuerpo;
    private String tamañoEstimado;
    private boolean completado;

    @OneToMany(mappedBy = "tatuaje")
    private List<Sesion> sesiones;
}