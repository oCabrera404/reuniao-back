package com.reuniao.backend.controller;

import com.reuniao.backend.entities.Reuniao;
import com.reuniao.backend.repository.ReuniaoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reunioes")
@CrossOrigin("*")
public class ReuniaoController {

    private final ReuniaoRepository repository;

    public ReuniaoController(ReuniaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Reuniao> listar() {
        return repository.findAll();
    }

    @PostMapping()
    public Reuniao criar(@RequestBody Reuniao reuniao) {
        return repository.save(reuniao);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
