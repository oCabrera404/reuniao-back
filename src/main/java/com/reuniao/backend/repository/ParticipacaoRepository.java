package com.reuniao.backend.repository;

import com.reuniao.backend.entities.ParticipacaoReuniao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipacaoRepository extends JpaRepository<ParticipacaoReuniao, Long> {
}
