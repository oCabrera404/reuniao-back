package com.reuniao.backend.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.reuniao.backend.entities.Usuario;
import com.reuniao.backend.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @PostMapping("/criar")
    public Usuario salvar(Usuario usuario){

        usuario.setSenha(encoder.encode(usuario.getSenha()));

        return repository.save(usuario);
    }

    @GetMapping("/teste")
    public String teste(){
        return "rota protegida funcionando";
    }

}
