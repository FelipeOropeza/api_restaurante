package com.api_restaurante.api.dto.pedido;

import com.api_restaurante.api.model.StatusPedido;
import jakarta.validation.constraints.NotNull;

public record AtualizacaoStatusPedido(
    @NotNull
    StatusPedido status
) {
}
