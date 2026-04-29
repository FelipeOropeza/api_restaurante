package com.api_restaurante.api.dto.dashboard;

import java.math.BigDecimal;
import java.util.List;

public record DadosResumoDashboard(
    BigDecimal faturamentoHoje,
    long pedidosPendentes,
    long pedidosFinalizadosHoje,
    List<ProdutoMaisVendido> produtosMaisVendidos
) {}
