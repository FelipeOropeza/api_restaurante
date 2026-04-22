package com.api_restaurante.api.dto.security;

public record DadosTokenJWT(
    String token,
    String nome,
    String email
) {
}
