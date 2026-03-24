package com.reuniao.backend.controller;

import com.reuniao.backend.dto.ReuniaoDTO;
import com.reuniao.backend.entities.Reuniao;
import com.reuniao.backend.entities.enums.StatusReuniao;
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

    public ReuniaoController(ReuniaoService service) {
        this.reuniaoService = service;
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criar(
            @RequestBody ReuniaoDTO dto,
            Authentication authentication) {

            dto.setStatus(StatusReuniao.CONFIRMADA);

        return ResponseEntity.ok(
                reuniaoService.criar(dto, authentication)
        );
    }

    @GetMapping("/{id}")
    public Reuniao buscarPorId(@PathVariable Long id, Authentication auth) {
        return reuniaoService.buscarPorId(id, auth);
    }

    @GetMapping("/minhas")
    public List<Reuniao> minhasReunioes(Authentication auth) {
        return reuniaoService.minhasReunioes(auth);
    }

    @PutMapping("/cancelar/{id}")
    public void cancelar(@PathVariable Long id) {
        reuniaoService.cancelar(id);
    }
}