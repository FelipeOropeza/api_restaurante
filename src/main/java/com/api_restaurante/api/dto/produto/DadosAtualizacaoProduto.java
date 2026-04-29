package com.api_restaurante.api.dto.produto;

import java.math.BigDecimal;

public record DadosAtualizacaoProduto(
    String nome,
    String descricao,
    BigDecimal preco,
    Long idCategoria
) {}
