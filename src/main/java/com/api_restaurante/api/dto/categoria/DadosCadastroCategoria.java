package com.api_restaurante.api.dto.categoria;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroCategoria(
    @NotBlank(message = "O nome da categoria é obrigatório")
    String nome
) {}
