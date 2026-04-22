package com.api_restaurante.api.dto.produto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record DadosAtualizacaoProduto(
    @NotNull
    Long id,
    String nome,
    String descricao,
    BigDecimal preco,
    Long idCategoria
) {}
