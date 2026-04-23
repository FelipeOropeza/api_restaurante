package com.api_restaurante.api.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoCategoria(
    @NotNull
    Long id,
    
    @NotBlank
    String nome
) {}
