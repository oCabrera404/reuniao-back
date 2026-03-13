package com.reuniao.backend.repository;

import com.reuniao.backend.entities.Reuniao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReuniaoRepository extends JpaRepository<Reuniao, Long> {
    boolean existsByDataAndHorarioAndSala_Id(
            LocalDate data,
            LocalTime horario,
            Long salaId
    );
}
