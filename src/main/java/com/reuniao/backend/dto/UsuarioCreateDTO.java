package com.reuniao.backend.dto;

public record UsuarioCreateDTO(
        String nome,
        String email,
        String senha
) {}
