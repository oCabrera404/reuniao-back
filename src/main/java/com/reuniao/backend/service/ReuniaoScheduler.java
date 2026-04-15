package com.reuniao.backend.service;

import com.reuniao.backend.entities.Reuniao;
import com.reuniao.backend.entities.enums.StatusReuniao;
import com.reuniao.backend.repository.ReuniaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ReuniaoScheduler {

    private final ReuniaoRepository reuniaoRepository;

    @Transactional
    @Scheduled(fixedDelay = 60000)
    public void finalizarReunioes() {

        LocalDate hoje = LocalDate.now();
        LocalTime agora = LocalTime.now();

        var reunioes = reuniaoRepository
                .buscarReunioesFinalizadas(
                        StatusReuniao.CONFIRMADA,
                        hoje,
                        agora
                );

        System.out.println("Encontradas: " + reunioes.size());

        for (Reuniao r : reunioes) {
            r.setStatus(StatusReuniao.CONCLUIDA);
        }

        reuniaoRepository.saveAll(reunioes);
    }
}