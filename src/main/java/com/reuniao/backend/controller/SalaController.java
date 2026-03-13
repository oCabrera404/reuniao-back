package com.reuniao.backend.controller;

import com.reuniao.backend.entities.Sala;
import com.reuniao.backend.service.SalaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/salas")
public class SalaController {

    private final SalaService service;

    public SalaController(SalaService service){
        this.service = service;
    }

    @PostMapping("/criar")
    public Sala criar(@RequestBody Sala sala){
        return service.criar(sala);
    }

    @GetMapping("/disponiveis")
    public List<Sala> listarDisponiveis(){
        return service.listarDiposniveis();
    }

    @GetMapping("/todas")
    public List<Sala> listar(){
        return service.listar();
    }

    @GetMapping("/buscar/{id}")
    public Sala buscar(@PathVariable Long id){
        return service.buscar(id);
    }
}
