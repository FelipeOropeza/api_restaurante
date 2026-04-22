package com.api_restaurante.api.dto.produto;

import com.api_restaurante.api.model.Produto;
import java.math.BigDecimal;

public record DadosListagemProduto(Long id, String nome, BigDecimal preco, String categoria) {
    public DadosListagemProduto(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getPreco(), 
             produto.getCategoria() != null ? produto.getCategoria().getNome() : "Sem Categoria");
    }
}
