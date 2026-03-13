package com.reuniao.backend.controller;

import com.reuniao.backend.dto.UsuarioCreateDTO;
import com.reuniao.backend.entities.Usuario;
import com.reuniao.backend.repository.UsuarioRepository;
import com.reuniao.backend.service.UsuarioService;
import org.springframework.security.core.Authentication;
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

    @PostMapping("/cadastrar")
    public Usuario cadastrar(@RequestBody UsuarioCreateDTO usuario){
        return usuarioService.salvar(usuario);
    }

    @GetMapping("/listar")
    public List<Usuario> listar(){
        return usuarioService.listar();
    }

    @GetMapping("/me")
    public Usuario usuarioLogado(Authentication auth){

        String email = auth.getName();

        return usuarioService.buscarPorEmail(email);
    }
}
