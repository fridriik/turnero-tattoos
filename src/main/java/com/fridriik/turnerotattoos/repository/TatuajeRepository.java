package com.fridriik.turnerotattoos.repository;

import com.fridriik.turnerotattoos.model.Tatuaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TatuajeRepository extends JpaRepository<Tatuaje, Long> {
}