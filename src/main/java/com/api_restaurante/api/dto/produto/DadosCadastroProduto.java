package com.api_restaurante.api.dto.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record DadosCadastroProduto(
    @NotBlank(message = "O nome do produto é obrigatório")
    String nome,
    
    String descricao,
    
    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    BigDecimal preco,
    
    @NotNull(message = "O ID da categoria é obrigatório")
    Long idCategoria
) {}
