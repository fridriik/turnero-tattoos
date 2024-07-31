package com.fridriik.turnerotattoos.controller;

import com.fridriik.turnerotattoos.dto.TurnoDTO;
import com.fridriik.turnerotattoos.service.TurnoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> getAllTurnos() {
        return ResponseEntity.ok(turnoService.getAllTurnos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> getTurnoById(@PathVariable Long id) {
        TurnoDTO turnoDTO = turnoService.getTurnoById(id);
        if (turnoDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(turnoDTO);
    }

    @PostMapping
    public ResponseEntity<TurnoDTO> saveTurno(@RequestBody TurnoDTO turnoDTO) {
        TurnoDTO savedTurno = turnoService.saveTurno(turnoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTurno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurnoDTO> updateTurno(@PathVariable Long id, @Valid @RequestBody TurnoDTO turnoDTO) {
        TurnoDTO updatedTurno = turnoService.updateTurno(id, turnoDTO);
        return updatedTurno != null ? ResponseEntity.ok(updatedTurno) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTurno(@PathVariable Long id) {
        turnoService.deleteTurno(id);
        return ResponseEntity.noContent().build();
    }
}
