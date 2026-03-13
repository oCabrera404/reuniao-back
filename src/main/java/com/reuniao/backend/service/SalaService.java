package com.reuniao.backend.service;

import com.reuniao.backend.entities.Sala;
import com.reuniao.backend.entities.enums.StatusSala;
import com.reuniao.backend.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    private final SalaRepository repository;

    public SalaService(SalaRepository repository){
        this.repository = repository;
    }

    public Sala criar(Sala sala){
        return repository.save(sala);
    }

    public List<Sala> listarDiposniveis(){
        return repository.findByStatus(StatusSala.DISPONIVEL);
    }

    public List<Sala> listar(){
        return repository.findAll();
    }

    public Sala buscar(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));
    }
}