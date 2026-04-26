package com.api_restaurante.api.dto.pedido;

import jakarta.validation.constraints.NotNull;

public record DadosItemPedido(
    @NotNull
    Long produtoId,
    @NotNull
    Integer quantidade
) {}
