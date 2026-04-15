package com.reuniao.backend.service;

import com.reuniao.backend.dto.ReuniaoDTO;
import com.reuniao.backend.entities.ParticipacaoReuniao;
import com.reuniao.backend.entities.Sala;
import com.reuniao.backend.entities.Usuario;
import com.reuniao.backend.entities.enums.StatusParticipacao;
import com.reuniao.backend.entities.enums.StatusReuniao;
import com.reuniao.backend.entities.enums.StatusSala;
import com.reuniao.backend.repository.ParticipacaoRepository;
import com.reuniao.backend.repository.SalaRepository;
import com.reuniao.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

import com.reuniao.backend.entities.Reuniao;
import com.reuniao.backend.repository.ReuniaoRepository;

@Service
public class ReuniaoService {

    private final ReuniaoRepository reuniaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final SalaRepository salaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ParticipacaoRepository participacaoRepository;

    public ReuniaoService(ReuniaoRepository reuniaoRepository, UsuarioRepository usuarioRepository, SalaRepository salaRepository) {
        this.reuniaoRepository = reuniaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaRepository = salaRepository;
    }

    public Reuniao criar(ReuniaoDTO dto, Authentication auth) {

        Usuario criador = usuarioRepository
                .findByEmail(auth.getName())
                .orElseThrow();

        Sala sala = salaRepository.findById(dto.getSalaId())
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));

        if (sala.getStatus() != StatusSala.DISPONIVEL) {
            throw new RuntimeException("Sala não está disponível");
        }

        boolean conflito = reuniaoRepository.existsConflito(
                dto.getData(),
                dto.getInicio(),
                dto.getTermino(),
                dto.getSalaId()
        );

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
        reuniao.setStatus(StatusReuniao.CONFIRMADA);

        reuniao = reuniaoRepository.save(reuniao);

        // cria convites
        if (dto.getParticipantesEmails() != null && !dto.getParticipantesEmails().isEmpty()) {

            List<Usuario> participantes =
                    usuarioRepository.findByEmailIn(dto.getParticipantesEmails());

            for (Usuario usuario : participantes) {

                ParticipacaoReuniao participacao = new ParticipacaoReuniao();
                participacao.setUsuario(usuario);
                participacao.setReuniao(reuniao);
                participacao.setStatus(StatusParticipacao.PENDENTE);

                reuniao.getParticipacoes().add(participacao);
                participacao.setReuniao(reuniao);

                participacaoRepository.save(participacao);

                reuniaoRepository.save(reuniao);

                emailService.enviarConvite(
                        usuario.getEmail(),
                        reuniao.getTitulo(),
                        participacao.getId()
                );
            }
        }

        return reuniao;
    }

    public List<Reuniao> listar() {
        return reuniaoRepository.findAll();
    }

    public List<Reuniao> minhasReunioes(Authentication auth) {

        String email = auth.getName();

        return reuniaoRepository.findMinhasReunioes(email);
    }

    public Reuniao buscarPorId(Long id, Authentication auth) {

        Reuniao reuniao = reuniaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reunião não encontrada"));

        if (!reuniao.getCriador().getEmail().equals(auth.getName())) {
            throw new RuntimeException("Acesso negado");
        }

        return reuniao;
    }

    public void cancelar(Long id) {

        Reuniao reuniao = reuniaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reunião não encontrada"));

        reuniao.setStatus(StatusReuniao.CANCELADA);

        reuniaoRepository.save(reuniao);
    }

    public List<Sala> buscarSalasDisponiveis(LocalDate data, LocalTime inicio, LocalTime termino) {

        List<Sala> todasSalas = salaRepository.findAll();

        return todasSalas.stream()
                .filter(sala -> !reuniaoRepository.existsConflito(
                        data,
                        inicio,
                        termino,
                        sala.getId()
                ))
                .toList();
    }
}
