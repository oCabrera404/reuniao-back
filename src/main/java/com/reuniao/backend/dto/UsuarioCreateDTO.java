package com.reuniao.backend.dto;

public record UsuarioCreateDTO(
        String nome,
        String email,
        String senha


) {
    @Override
    public String nome() {
        return nome;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public String senha() {
        return senha;
    }
}
