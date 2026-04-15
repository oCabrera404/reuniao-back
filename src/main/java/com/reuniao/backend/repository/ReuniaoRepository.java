package com.reuniao.backend.repository;

import com.reuniao.backend.entities.Reuniao;
import com.reuniao.backend.entities.Usuario;
import com.reuniao.backend.entities.enums.StatusReuniao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface ReuniaoRepository extends JpaRepository<Reuniao, Long> {
    @Query("""
        SELECT DISTINCT r
        FROM Reuniao r
        LEFT JOIN r.participacoes pr
        LEFT JOIN pr.usuario u
        WHERE r.criador.email = :email
           OR u.email = :email
    """)
    List<Reuniao> findMinhasReunioes(@Param("email") String email);

    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
        FROM Reuniao r
        WHERE r.sala.id = :salaId
        AND r.data = :data
        AND r.status <> 'CANCELADA'
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

    @Query("""
        SELECT r FROM Reuniao r
        WHERE r.status = :status
        AND (
            r.data < :hoje OR
            (r.data = :hoje AND r.termino <= :agora)
        )
    """)
    List<Reuniao> buscarReunioesFinalizadas(
            @Param("status") StatusReuniao status,
            @Param("hoje") LocalDate hoje,
            @Param("agora") LocalTime agora
    );
}
