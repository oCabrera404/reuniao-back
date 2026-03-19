package com.reuniao.backend.service;

import com.reuniao.backend.dto.UsuarioCreateDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.reuniao.backend.entities.Usuario;
import com.reuniao.backend.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public Usuario salvar(UsuarioCreateDTO dto){

        Usuario usuario = new Usuario();

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());

        usuario.setSenha(encoder.encode(dto.senha()));

        return repository.save(usuario);
    }

    public List<Usuario> listar(){
        return repository.findAll();
    }

    public Usuario buscarPorEmail(String email){
        return repository.findByEmail(email)
                .orElseThrow();
    }
}
