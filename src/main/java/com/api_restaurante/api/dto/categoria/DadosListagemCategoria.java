package com.api_restaurante.api.dto.categoria;

import com.api_restaurante.api.model.Categoria;

public record DadosListagemCategoria(Long id, String nome) {
    public DadosListagemCategoria(Categoria categoria) {
        this(categoria.getId(), categoria.getNome());
    }
}
