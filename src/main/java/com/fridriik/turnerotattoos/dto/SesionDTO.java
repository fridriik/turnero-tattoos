package com.fridriik.turnerotattoos.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class SesionDTO {
    private Long id;
    private Long tatuajeId;
    private Long clienteId;
    private Long tatuadorId;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private List<Long> turnosIds;
    private String trabajoRealizado;
    private BigDecimal costo;
}