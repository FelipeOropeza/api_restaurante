package com.api_restaurante.api.dto.security;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(
    @NotBlank(message = "Email é obrigatório")
    String email, 
    
    @NotBlank(message = "Senha é obrigatória")
    String senha
) {
}
