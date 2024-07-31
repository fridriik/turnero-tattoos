package com.fridriik.turnerotattoos.controller;

import com.fridriik.turnerotattoos.dto.TatuadorDTO;
import com.fridriik.turnerotattoos.service.TatuadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tatuadores")
public class TatuadorController {

    private final TatuadorService tatuadorService;

    @Autowired
    public TatuadorController(TatuadorService tatuadorService) {
        this.tatuadorService = tatuadorService;
    }

    @GetMapping
    public ResponseEntity<Page<TatuadorDTO>> getAllTatuadores(Pageable pageable) {
        return ResponseEntity.ok(tatuadorService.getAllTatuadores(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TatuadorDTO> getTatuadorById(@PathVariable Long id) {
        return tatuadorService.getTatuadorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TatuadorDTO> saveTatuador(@Valid @RequestBody TatuadorDTO tatuadorDTO) {
        TatuadorDTO savedTatuador = tatuadorService.saveTatuador(tatuadorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTatuador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TatuadorDTO> updateTatuador(@PathVariable Long id, @Valid @RequestBody TatuadorDTO tatuadorDTO) {
        return tatuadorService.updateTatuador(id, tatuadorDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTatuador(@PathVariable Long id) {
        tatuadorService.deleteTatuador(id);
        return ResponseEntity.noContent().build();
    }
}