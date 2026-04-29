package com.api_restaurante.api.dto.security;

import com.api_restaurante.api.model.TipoUsuario;
import com.api_restaurante.api.model.Usuario;

public record DadosListagemUsuario(
    Long id,
    String nome,
    String email,
    TipoUsuario tipo
) {
    public DadosListagemUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTipo());
    }
}
