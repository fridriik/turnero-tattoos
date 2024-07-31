package com.fridriik.turnerotattoos.repository;

import com.fridriik.turnerotattoos.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
}