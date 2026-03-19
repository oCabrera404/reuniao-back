package com.reuniao.backend.repository;

import com.reuniao.backend.entities.Reuniao;
import com.reuniao.backend.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReuniaoRepository extends JpaRepository<Reuniao, Long> {
    List<Reuniao> findByCriadorEmail(String email);
    @Query("""
        SELECT COUNT(r) > 0 FROM Reuniao r
        WHERE r.data = :data
        AND r.sala.id = :salaId
        AND (
              r.inicio < :termino
              AND r.termino > :inicio
        )
    """)
    boolean existsConflito(
            LocalDate data,
            LocalTime inicio,
            LocalTime termino,
            Long salaId
    );
}
