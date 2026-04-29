package com.api_restaurante.api.dto.dashboard;

public record ProdutoMaisVendido(
    String produto,
    Long quantidade
) {
}
