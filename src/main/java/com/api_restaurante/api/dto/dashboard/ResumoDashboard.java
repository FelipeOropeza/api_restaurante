package com.api_restaurante.api.dto.dashboard;

import java.math.BigDecimal;
import java.util.List;

public record ResumoDashboard(
    BigDecimal faturamentoHoje,
    Long pedidosPendentes,
    Long pedidosFinalizadosHoje,
    List<ProdutoMaisVendido> produtosMaisVendidos
) {
}
