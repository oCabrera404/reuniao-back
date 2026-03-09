package com.reuniao.backend.controller;

import com.reuniao.backend.entities.Usuario;
import com.reuniao.backend.repository.UsuarioRepository;
import com.reuniao.backend.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public Usuario cadastrar(@RequestBody Usuario usuario){
        return usuarioService.salvar(usuario);
    }
}
