package com.reuniao.backend.repository;

import com.reuniao.backend.entities.Sala;
import com.reuniao.backend.entities.enums.StatusSala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaRepository extends JpaRepository<Sala, Long> {
    List<Sala> findByStatus(StatusSala status);
}
