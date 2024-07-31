package com.fridriik.turnerotattoos.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TurnoDTO {
    private Long id;
    private Long tatuadorId;
    private Long clienteId;
    private LocalDateTime fechaHora;
    private boolean reservado;
    private Long sesionId;
}