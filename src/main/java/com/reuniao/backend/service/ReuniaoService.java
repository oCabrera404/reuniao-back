package com.reuniao.backend.service;

import com.reuniao.backend.dto.ReuniaoDTO;
import com.reuniao.backend.entities.Sala;
import com.reuniao.backend.entities.Usuario;
import com.reuniao.backend.entities.enums.StatusSala;
import com.reuniao.backend.repository.SalaRepository;
import com.reuniao.backend.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.reuniao.backend.entities.Reuniao;
import com.reuniao.backend.repository.ReuniaoRepository;

@Service
public class ReuniaoService {

    private final ReuniaoRepository reuniaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final SalaRepository salaRepository;

    public ReuniaoService(ReuniaoRepository reuniaoRepository, UsuarioRepository usuarioRepository, SalaRepository salaRepository) {
        this.reuniaoRepository = reuniaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaRepository = salaRepository;
    }

    public Reuniao criar(ReuniaoDTO dto, Authentication auth) {

        String email = auth.getName();

        Usuario criador = usuarioRepository
                .findByEmail(email)
                .orElseThrow();

        Reuniao reuniao = new Reuniao();

        reuniao.setTitulo(dto.getTitulo());
        reuniao.setData(dto.getData());
        reuniao.setHorario(dto.getHorario());
        reuniao.setCriador(criador);

        Sala sala = salaRepository.findById(dto.getSalaId())
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));

        if(sala.getStatus() != StatusSala.DISPONIVEL){
            throw new RuntimeException("Sala não está disponível");
        }

        reuniao.setSala(sala);

        reuniao.setSala(sala);

        if (dto.getParticipantesIds() != null && !dto.getParticipantesIds().isEmpty()) {

            List<Usuario> participantesList =
                    usuarioRepository.findAllById(dto.getParticipantesIds());

            Set<Usuario> participantes = new HashSet<>(participantesList);

            reuniao.setParticipantes(participantes);

            for (Usuario usuario : participantes) {

                boolean conflito = reuniaoRepository.existsByDataAndHorarioAndSala_Id(
                        dto.getData(),
                        dto.getHorario(),
                        dto.getSalaId()
                );

                if(conflito){
                    throw new RuntimeException("Sala já reservada nesse horário");
                }
            }

        }
        return reuniaoRepository.save(reuniao);
    }

    public List<Reuniao> listar() {
        return reuniaoRepository.findAll();
    }

    public Reuniao buscarPorId(Long id) {
        return reuniaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reunião não encontrada"));
    }

    public void deletar(Long id) {
        reuniaoRepository.deleteById(id);
    }
}
