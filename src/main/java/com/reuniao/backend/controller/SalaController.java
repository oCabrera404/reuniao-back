package com.reuniao.backend.controller;

import com.reuniao.backend.entities.Sala;
import com.reuniao.backend.service.ReuniaoService;
import com.reuniao.backend.service.SalaService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/salas")
public class SalaController {

    private final SalaService salaServiceService;
    private final ReuniaoService reuniaoService;

    public SalaController(SalaService service, ReuniaoService reuniaoService){
        this.salaServiceService = service;
        this.reuniaoService = reuniaoService;
    }

    @PostMapping("/criar")
    public Sala criar(@RequestBody Sala sala){
        return salaServiceService.criar(sala);
    }

    @GetMapping("/disponiveis")
    public List<Sala> salasDisponiveis(
            @RequestParam LocalDate data,
            @RequestParam LocalTime inicio,
            @RequestParam LocalTime termino
    ) {
        return reuniaoService.buscarSalasDisponiveis(data, inicio, termino);
    }

    @GetMapping("/todas")
    public List<Sala> listar(){
        return salaServiceService.listar();
    }

    @GetMapping("/buscar/{id}")
    public Sala buscar(@PathVariable Long id){
        return salaServiceService.buscar(id);
    }
}
