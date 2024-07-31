package com.fridriik.turnerotattoos.controller;

import com.fridriik.turnerotattoos.dto.SesionDTO;
import com.fridriik.turnerotattoos.dto.TurnoDTO;
import com.fridriik.turnerotattoos.model.Sesion;
import com.fridriik.turnerotattoos.service.SesionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sesion")
public class SesionController {

    @Autowired
    private SesionService sesionService;

    @GetMapping
    public ResponseEntity<List<SesionDTO>> getAllSesiones() {
        return ResponseEntity.ok(sesionService.getAllSesiones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SesionDTO> getSesionById(@PathVariable Long id) {
        SesionDTO sesionDTO = sesionService.getSesionById(id);
        if (sesionDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sesionDTO);
    }

    @PostMapping
    public ResponseEntity<SesionDTO> saveSesion(@RequestBody SesionDTO sesionDTO) {
        SesionDTO savedSesion = sesionService.saveSesion(sesionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSesion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SesionDTO> updateSesion(@PathVariable Long id, @Valid @RequestBody SesionDTO sesionDTO) {
        SesionDTO updatedSesion = sesionService.updateSesion(id, sesionDTO);
        return updatedSesion != null ? ResponseEntity.ok(updatedSesion) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSesion(@PathVariable Long id) {
        sesionService.deleteSesion(id);
        return ResponseEntity.noContent().build();
    }
}

