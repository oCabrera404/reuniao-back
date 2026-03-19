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

        Usuario criador = usuarioRepository.findByEmail(auth.getName()).orElseThrow();

        Sala sala = salaRepository.findById(dto.getSalaId()).orElseThrow(() -> new RuntimeException("Sala não encontrada"));

        if (sala.getStatus() != StatusSala.DISPONIVEL) {
            throw new RuntimeException("Sala não está disponível");
        }

        boolean conflito = reuniaoRepository.existsConflito(dto.getData(), dto.getInicio(), dto.getTermino(), dto.getSalaId());

        if (conflito) {
            throw new RuntimeException("Sala já reservada nesse horário");
        }

        Reuniao reuniao = new Reuniao();
        reuniao.setTitulo(dto.getTitulo());
        reuniao.setDescricao(dto.getDescricao());
        reuniao.setData(dto.getData());
        reuniao.setInicio(dto.getInicio());
        reuniao.setTermino(dto.getTermino());
        reuniao.setCriador(criador);
        reuniao.setSala(sala);

        if (dto.getParticipantesEmails() != null && !dto.getParticipantesEmails().isEmpty()) {

            List<Usuario> participantes = usuarioRepository.findByEmailIn(dto.getParticipantesEmails());

            reuniao.setParticipantes(new HashSet<>(participantes));
        }
        return reuniaoRepository.save(reuniao);
    }

    public List<Reuniao> listar() {
        return reuniaoRepository.findAll();
    }

    public Reuniao buscarPorId(Long id, Authentication auth) {

        Reuniao reuniao = reuniaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reunião não encontrada"));

        if (!reuniao.getCriador().getEmail().equals(auth.getName())) {
            throw new RuntimeException("Acesso negado");
        }

        return reuniao;
    }

    public void deletar(Long id) {
        reuniaoRepository.deleteById(id);
    }
}
