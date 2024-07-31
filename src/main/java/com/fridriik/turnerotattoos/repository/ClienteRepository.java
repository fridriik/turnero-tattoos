package com.fridriik.turnerotattoos.repository;

import com.fridriik.turnerotattoos.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}