package com.fridriik.turnerotattoos.repository;

import com.fridriik.turnerotattoos.model.Tatuador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TatuadorRepository extends JpaRepository<Tatuador, Long> {
}