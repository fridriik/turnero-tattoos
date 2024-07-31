package com.fridriik.turnerotattoos.service;

import com.fridriik.turnerotattoos.dto.TatuadorDTO;
import com.fridriik.turnerotattoos.model.Tatuador;
import com.fridriik.turnerotattoos.repository.TatuadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TatuadorService {

    private final TatuadorRepository tatuadorRepository;

    @Autowired
    public TatuadorService(TatuadorRepository tatuadorRepository) {
        this.tatuadorRepository = tatuadorRepository;
    }

    public Page<TatuadorDTO> getAllTatuadores(Pageable pageable) {
        return tatuadorRepository.findAll(pageable).map(this::convertToDTO);
    }

    public Optional<TatuadorDTO> getTatuadorById(Long id) {
        return tatuadorRepository.findById(id).map(this::convertToDTO);
    }

    public TatuadorDTO saveTatuador(TatuadorDTO tatuadorDTO) {
        Tatuador tatuador = convertToEntity(tatuadorDTO);
        Tatuador savedTatuador = tatuadorRepository.save(tatuador);
        return convertToDTO(savedTatuador);
    }

    public Optional<TatuadorDTO> updateTatuador(Long id, TatuadorDTO tatuadorDTO) {
        return tatuadorRepository.findById(id)
                .map(tatuador -> {
                    if (hasTatuadorChanged(tatuador, tatuadorDTO)) {
                        tatuador.setNombre(tatuadorDTO.getNombre());
                        tatuador.setApellido(tatuadorDTO.getApellido());
                        tatuador.setEspecialidad(tatuadorDTO.getEspecialidad());
                        tatuador = tatuadorRepository.save(tatuador);
                    }
                    return convertToDTO(tatuador);
                });
    }

    public void deleteTatuador(Long id) {
        tatuadorRepository.deleteById(id);
    }

    private TatuadorDTO convertToDTO(Tatuador tatuador) {
        TatuadorDTO dto = new TatuadorDTO();
        dto.setId(tatuador.getId());
        dto.setNombre(tatuador.getNombre());
        dto.setApellido(tatuador.getApellido());
        dto.setEspecialidad(tatuador.getEspecialidad());
        return dto;
    }

    private Tatuador convertToEntity(TatuadorDTO dto) {
        Tatuador tatuador = new Tatuador();
        tatuador.setId(dto.getId());
        tatuador.setNombre(dto.getNombre());
        tatuador.setApellido(dto.getApellido());
        tatuador.setEspecialidad(dto.getEspecialidad());
        return tatuador;
    }

    private boolean hasTatuadorChanged(Tatuador tatuador, TatuadorDTO tatuadorDTO) {
        return !tatuador.getNombre().equals(tatuadorDTO.getNombre()) ||
                !tatuador.getApellido().equals(tatuadorDTO.getApellido()) ||
                !tatuador.getEspecialidad().equals(tatuadorDTO.getEspecialidad());
    }
}