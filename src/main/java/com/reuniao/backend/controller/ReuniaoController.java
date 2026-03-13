package com.reuniao.backend.controller;

import com.reuniao.backend.dto.ReuniaoDTO;
import com.reuniao.backend.entities.Reuniao;
import com.reuniao.backend.service.ReuniaoService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reunioes")
@CrossOrigin("*")
public class ReuniaoController {

    private final ReuniaoService service;

    public ReuniaoController(ReuniaoService service){
        this.service = service;
    }

    @PostMapping("/criar")
    public Reuniao criar(@RequestBody ReuniaoDTO dto, Authentication auth){
        return service.criar(dto, auth);
    }

    @GetMapping("/listar")
    public List<Reuniao> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public Reuniao buscar(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }
}