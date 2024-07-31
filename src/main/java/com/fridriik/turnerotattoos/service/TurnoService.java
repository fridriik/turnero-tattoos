package com.fridriik.turnerotattoos.service;

import com.fridriik.turnerotattoos.dto.TurnoDTO;
import com.fridriik.turnerotattoos.model.Cliente;
import com.fridriik.turnerotattoos.model.Sesion;
import com.fridriik.turnerotattoos.model.Tatuador;
import com.fridriik.turnerotattoos.model.Turno;
import com.fridriik.turnerotattoos.repository.ClienteRepository;
import com.fridriik.turnerotattoos.repository.SesionRepository;
import com.fridriik.turnerotattoos.repository.TatuadorRepository;
import com.fridriik.turnerotattoos.repository.TurnoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private TatuadorRepository tatuadorRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private SesionRepository sesionRepository;

    public List<TurnoDTO> getAllTurnos() {
        return turnoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TurnoDTO getTurnoById(Long id) {
        return turnoRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public TurnoDTO saveTurno(TurnoDTO turnoDTO) {
        Turno turno = convertToEntity(turnoDTO);
        turno = turnoRepository.save(turno);
        return convertToDTO(turno);
    }

    public void deleteTurno(Long id) {
        turnoRepository.deleteById(id);
    }

    public TurnoDTO updateTurno(Long id, TurnoDTO turnoDTO) {
        return turnoRepository.findById(id)
                .map(turno -> {
                    if (hasTurnoChanged(turno, turnoDTO)) {
                        Tatuador tatuador = tatuadorRepository.findById(turnoDTO.getTatuadorId()).orElse(null);
                        Cliente cliente = clienteRepository.findById(turnoDTO.getClienteId()).orElse(null);
                        Sesion sesion = sesionRepository.findById(turnoDTO.getSesionId()).orElse(null);
                        instancesForUpdate(turnoDTO, turno, tatuador, cliente, sesion);
                        turno = turnoRepository.save(turno);
                    }
                    return convertToDTO(turno);
                })
                .orElse(null);
    }

    private TurnoDTO convertToDTO(Turno turno) {
        TurnoDTO dto = new TurnoDTO();
        dto.setId(turno.getId());
        dto.setTatuadorId(turno.getTatuador().getId());
        dto.setClienteId(turno.getCliente().getId());
        dto.setFechaHora(turno.getFechaHora());
        dto.setReservado(turno.isReservado());
        dto.setSesionId(turno.getSesion().getId());
        return dto;
    }

    private Turno convertToEntity(TurnoDTO dto) {
        Turno turno = new Turno();
        Tatuador tatuador = tatuadorRepository.findById(dto.getTatuadorId()).orElseThrow(() -> new EntityNotFoundException("Tatuador no encontrado"));
        Cliente cliente = clienteRepository.findById(dto.getClienteId()).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        Sesion sesion = sesionRepository.findById(dto.getSesionId()).orElseThrow(() -> new EntityNotFoundException("Sesión no encontrada"));
        instancesForUpdate(dto, turno, tatuador, cliente, sesion);
        return turno;
    }

    private boolean hasTurnoChanged(Turno turno, TurnoDTO turnoDTO) {
        return !turno.getTatuador().getId().equals(turnoDTO.getTatuadorId()) ||
                !turno.getCliente().getId().equals(turnoDTO.getClienteId()) ||
                !turno.getFechaHora().equals(turnoDTO.getFechaHora()) ||
                turno.isReservado() != turnoDTO.isReservado() ||
                !turno.getSesion().getId().equals(turnoDTO.getSesionId());
    }

    private void instancesForUpdate(TurnoDTO dto, Turno turno, Tatuador tatuador, Cliente cliente, Sesion sesion) {
        turno.setId(dto.getId());
        turno.setFechaHora(dto.getFechaHora());
        turno.setReservado(dto.isReservado());
        turno.setTatuador(tatuador);
        turno.setCliente(cliente);
        turno.setSesion(sesion);
    }
}