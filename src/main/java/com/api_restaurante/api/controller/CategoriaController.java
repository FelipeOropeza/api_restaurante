package com.api_restaurante.api.controller;

import com.api_restaurante.api.dto.categoria.DadosCadastroCategoria;
import com.api_restaurante.api.dto.categoria.DadosListagemCategoria;
import com.api_restaurante.api.model.Categoria;
import com.api_restaurante.api.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaRepository repository;

    public CategoriaController(CategoriaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosListagemCategoria> cadastrar(@RequestBody @Valid DadosCadastroCategoria dados, UriComponentsBuilder uriBuilder) {
        var categoria = new Categoria(dados.nome());
        repository.save(categoria);

        var uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemCategoria(categoria));
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemCategoria>> listar() {
        var categorias = repository.findAll().stream().map(DadosListagemCategoria::new).toList();
        return ResponseEntity.ok(categorias);
    }
}
