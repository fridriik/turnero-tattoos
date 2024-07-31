package com.fridriik.turnerotattoos.service;

import com.fridriik.turnerotattoos.dto.TatuajeDTO;
import com.fridriik.turnerotattoos.model.Tatuaje;
import com.fridriik.turnerotattoos.repository.TatuajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TatuajeService {
    @Autowired
    private TatuajeRepository tatuajeRepository;

    public List<TatuajeDTO> getAllTatuajes() {
        return tatuajeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TatuajeDTO getTatuajeById(Long id) {
        return tatuajeRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public TatuajeDTO saveTatuaje(TatuajeDTO tatuajeDTO) {
        Tatuaje tatuaje = convertToEntity(tatuajeDTO);
        tatuaje = tatuajeRepository.save(tatuaje);
        return convertToDTO(tatuaje);
    }

    public void deleteTatuaje(Long id) {
        tatuajeRepository.deleteById(id);
    }

    private TatuajeDTO convertToDTO(Tatuaje tatuaje) {
        TatuajeDTO dto = new TatuajeDTO();
        // Set properties
        return dto;
    }

    private Tatuaje convertToEntity(TatuajeDTO dto) {
        Tatuaje tatuaje = new Tatuaje();
        // Set properties
        return tatuaje;
    }
}