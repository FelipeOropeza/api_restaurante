package com.api_restaurante.api.dto.security;

import com.api_restaurante.api.model.TipoUsuario;
import jakarta.validation.constraints.NotNull;

public record AtualizacaoTipoUsuario(
    @NotNull
    TipoUsuario tipo
) {
}
