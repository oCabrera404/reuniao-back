package com.reuniao.backend.controller;

import com.reuniao.backend.dto.ReuniaoDTO;
import com.reuniao.backend.entities.Reuniao;
import com.reuniao.backend.service.ReuniaoService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reunioes")
@CrossOrigin("*")
public class ReuniaoController {

    private final ReuniaoService reuniaoService;

    public ReuniaoController(ReuniaoService service){
        this.reuniaoService = service;
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criar(
            @RequestBody ReuniaoDTO dto,
            Authentication authentication) {

        return ResponseEntity.ok(
                reuniaoService.criar(dto, authentication)
        );
    }

    @GetMapping("/{id}")
    public Reuniao buscarPorId(@PathVariable Long id, Authentication auth) {
        return reuniaoService.buscarPorId(id, auth);
    }

    @GetMapping("/listar")
    public List<Reuniao> listar(){
        return reuniaoService.listar();
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        reuniaoService.deletar(id);
    }
}