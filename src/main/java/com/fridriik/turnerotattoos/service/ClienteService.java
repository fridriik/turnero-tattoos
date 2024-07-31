package com.fridriik.turnerotattoos.service;

import com.fridriik.turnerotattoos.dto.ClienteDTO;
import com.fridriik.turnerotattoos.model.Cliente;
import com.fridriik.turnerotattoos.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Page<ClienteDTO> getAllClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(this::convertToDTO);
    }

    public Optional<ClienteDTO> getClienteById(Long id) {
        return clienteRepository.findById(id).map(this::convertToDTO);
    }

    public ClienteDTO saveCliente(ClienteDTO clienteDTO) {
        Cliente cliente = convertToEntity(clienteDTO);
        Cliente savedCliente = clienteRepository.save(cliente);
        return convertToDTO(savedCliente);
    }

    public Optional<ClienteDTO> updateCliente(Long id, ClienteDTO clienteDTO) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    if (hasClienteChanged(cliente, clienteDTO)) {
                        cliente.setNombre(clienteDTO.getNombre());
                        cliente.setApellido(clienteDTO.getApellido());
                        cliente.setEmail(clienteDTO.getEmail());
                        cliente.setTelefono(clienteDTO.getTelefono());
                        cliente.setVip(clienteDTO.isVip());
                        cliente = clienteRepository.save(cliente);
                    }
                    return convertToDTO(cliente);
                });
    }

    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    private ClienteDTO convertToDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        dto.setVip(cliente.isVip());
        return dto;
    }

    private Cliente convertToEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setVip(dto.isVip());
        return cliente;
    }

    private boolean hasClienteChanged(Cliente cliente, ClienteDTO clienteDTO) {
        return !cliente.getNombre().equals(clienteDTO.getNombre()) ||
                !cliente.getApellido().equals(clienteDTO.getApellido()) ||
                !cliente.getEmail().equals(clienteDTO.getEmail()) ||
                !cliente.getTelefono().equals(clienteDTO.getTelefono()) ||
                cliente.isVip() != clienteDTO.isVip();
    }
}