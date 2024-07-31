package com.fridriik.turnerotattoos.dto;

import lombok.Data;
import java.util.List;

@Data
public class TatuajeDTO {
    private Long id;
    private Long clienteId;
    private Long tatuadorPrincipalId;
    private String descripcion;
    private String ubicacionCuerpo;
    private String tamañoEstimado;
    private boolean completado;
    private List<Long> sesionesIds;
}