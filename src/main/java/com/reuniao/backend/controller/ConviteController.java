package com.reuniao.backend.controller;

import com.reuniao.backend.entities.enums.StatusParticipacao;
import com.reuniao.backend.repository.ParticipacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/convites")
public class ConviteController {

    @Autowired
    private ParticipacaoRepository repository;

    @GetMapping("/aceitar/{id}")
    public String aceitar(@PathVariable Long id) {
        var p = repository.findById(id).orElseThrow();
        p.setStatus(StatusParticipacao.ACEITA);
        repository.save(p);

        return "Convite aceito!";
    }

    @GetMapping("/recusar/{id}")
    public String recusar(@PathVariable Long id) {
        var p = repository.findById(id).orElseThrow();
        p.setStatus(StatusParticipacao.RECUSADA);
        repository.save(p);

        return "Convite recusado!";
    }
}
