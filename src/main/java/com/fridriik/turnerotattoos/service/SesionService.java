package com.fridriik.turnerotattoos.service;

import com.fridriik.turnerotattoos.dto.SesionDTO;
import com.fridriik.turnerotattoos.model.*;
import com.fridriik.turnerotattoos.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SesionService {
    @Autowired
    private SesionRepository sesionRepository;
    @Autowired
    private TatuadorRepository tatuadorRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TatuajeRepository tatuajeRepository;
    @Autowired
    private TurnoRepository turnoRepository;

    public List<SesionDTO> getAllSesiones() {
        return sesionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SesionDTO getSesionById(Long id) {
        return sesionRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public SesionDTO saveSesion(SesionDTO sesionDTO) {
        Sesion sesion = convertToEntity(sesionDTO);
        sesion = sesionRepository.save(sesion);
        return convertToDTO(sesion);
    }

    public void deleteSesion(Long id) {
        sesionRepository.deleteById(id);
    }

    public SesionDTO updateSesion(Long id, SesionDTO sesionDTO) {
        return sesionRepository.findById(id)
                .map(sesion -> {
                    if (hasSesionChanged(sesion, sesionDTO)) {
                        Tatuador tatuador = tatuadorRepository.findById(sesionDTO.getTatuadorId()).orElse(null);
                        Cliente cliente = clienteRepository.findById(sesionDTO.getClienteId()).orElse(null);
                        Tatuaje tatuaje = tatuajeRepository.findById(sesionDTO.getTatuajeId()).orElse(null);
                        instancesForUpdate(sesionDTO, sesion, tatuador, cliente, tatuaje);
                        sesion = sesionRepository.save(sesion);
                    }
                    return convertToDTO(sesion);
                })
                .orElse(null);
    }

    private SesionDTO convertToDTO(Sesion sesion) {
        SesionDTO dto = new SesionDTO();
        dto.setId(sesion.getId());
        dto.setTatuajeId(sesion.getTatuaje().getId());
        dto.setClienteId(sesion.getCliente().getId());
        dto.setTatuadorId(sesion.getTatuador().getId());
        dto.setFecha(sesion.getFecha());
        dto.setHoraInicio(sesion.getHoraInicio());
        dto.setHoraFin(sesion.getHoraFin());
        dto.setTurnosIds(sesion.getTurnos().stream().map(Turno::getId).collect(Collectors.toList()));
        dto.setTrabajoRealizado(sesion.getTrabajoRealizado());
        dto.setCosto(sesion.getCosto());
        return dto;
    }

    private Sesion convertToEntity(SesionDTO dto) {
        Sesion sesion = new Sesion();
        Tatuador tatuador = tatuadorRepository.findById(dto.getTatuadorId()).orElseThrow(() -> new EntityNotFoundException("Tatuador no encontrado"));
        Cliente cliente = clienteRepository.findById(dto.getClienteId()).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        Tatuaje tatuaje = tatuajeRepository.findById(dto.getTatuajeId()).orElseThrow(() -> new EntityNotFoundException("Tatuaje no encontrado"));
        instancesForUpdate(dto, sesion, tatuador, cliente, tatuaje);
        return sesion;
    }

    private boolean hasSesionChanged(Sesion sesion, SesionDTO sesionDTO) {
        return !sesion.getTatuaje().getId().equals(sesionDTO.getTatuajeId()) ||
                !sesion.getCliente().getId().equals(sesionDTO.getClienteId()) ||
                !sesion.getTatuador().getId().equals(sesionDTO.getTatuadorId()) ||
                !sesion.getFecha().equals(sesionDTO.getFecha()) ||
                !sesion.getHoraInicio().equals(sesionDTO.getHoraInicio()) ||
                !sesion.getHoraFin().equals(sesionDTO.getHoraFin()) ||
                !sesion.getTurnos().stream().map(Turno::getId).collect(Collectors.toList()).equals(sesionDTO.getTurnosIds()) ||
                !Objects.equals(sesion.getTrabajoRealizado(), sesionDTO.getTrabajoRealizado()) ||
                !Objects.equals(sesion.getCosto(), sesionDTO.getCosto());
    }

    private void instancesForUpdate(SesionDTO sesionDTO, Sesion sesion, Tatuador tatuador, Cliente cliente, Tatuaje tatuaje) {
        List<Turno> turnos = turnoRepository.findAllById(sesionDTO.getTurnosIds());

        sesion.setId(sesionDTO.getId());
        sesion.setTatuaje(tatuaje);
        sesion.setCliente(cliente);
        sesion.setTatuador(tatuador);
        sesion.setFecha(sesionDTO.getFecha());
        sesion.setHoraInicio(sesionDTO.getHoraInicio());
        sesion.setHoraFin(sesionDTO.getHoraFin());
        sesion.setTurnos(turnos);
        sesion.setTrabajoRealizado(sesionDTO.getTrabajoRealizado());
        sesion.setCosto(sesionDTO.getCosto());
    }
}