package com.api_restaurante.api.dto.produto;

import com.api_restaurante.api.model.Produto;
import java.math.BigDecimal;

public record DadosDetalhamentoProduto(Long id, String nome, String descricao, BigDecimal preco, String categoria) {
    public DadosDetalhamentoProduto(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(), 
             produto.getCategoria() != null ? produto.getCategoria().getNome() : "Sem Categoria");
    }
}
