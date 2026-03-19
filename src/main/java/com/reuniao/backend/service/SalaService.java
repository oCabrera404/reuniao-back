package com.reuniao.backend.service;

import com.reuniao.backend.entities.Sala;
import com.reuniao.backend.entities.enums.StatusSala;
import com.reuniao.backend.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository){
        this.salaRepository = salaRepository;
    }

    public Sala criar(Sala sala){
        return salaRepository.save(sala);
    }

    public List<Sala> listarDiposniveis(){
        return salaRepository.findByStatus(StatusSala.DISPONIVEL);
    }

    public List<Sala> listar(){
        return salaRepository.findAll();
    }

    public Sala buscar(Long id){
        return salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));
    }
}