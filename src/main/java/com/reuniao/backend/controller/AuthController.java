package com.reuniao.backend.controller;

import com.reuniao.backend.dto.LoginDTO;
import com.reuniao.backend.entities.Usuario;
import com.reuniao.backend.repository.UsuarioRepository;
import com.reuniao.backend.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository repository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder;

    public AuthController(UsuarioRepository repository,
                          JwtService jwtService,
                          BCryptPasswordEncoder encoder) {

        this.repository = repository;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO data){

        Usuario usuario = repository.findByEmail(data.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if(!encoder.matches(data.senha(), usuario.getSenha())){
            throw new RuntimeException("Senha inválida");
        }

        return jwtService.gerarToken(usuario.getEmail());
    }
}