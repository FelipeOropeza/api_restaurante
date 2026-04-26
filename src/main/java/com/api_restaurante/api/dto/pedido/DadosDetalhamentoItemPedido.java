package com.api_restaurante.api.dto.pedido;

import com.api_restaurante.api.model.ItemPedido;
import java.math.BigDecimal;

public record DadosDetalhamentoItemPedido(Long id, Long produtoId, String nomeProduto, Integer quantidade, BigDecimal precoUnitario) {
    public DadosDetalhamentoItemPedido(ItemPedido item) {
        this(item.getId(), item.getProduto().getId(), item.getProduto().getNome(), item.getQuantidade(), item.getPrecoUnitario());
    }
}
