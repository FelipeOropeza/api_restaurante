package com.api_restaurante.api.controller;

import com.api_restaurante.api.model.Produto;
import com.api_restaurante.api.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        var produtos = repository.findAll();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        var produto = repository.getReferenceById(id);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Produto> cadastrar(@RequestBody @Valid Produto produto, UriComponentsBuilder uriBuilder) {
        var produtoSalvo = repository.save(produto);
        var uri = uriBuilder.path("/produtos/{id}").buildAndExpand(produtoSalvo.getId()).toUri();
        return ResponseEntity.created(uri).body(produtoSalvo);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody @Valid Produto dados) {
        var produto = repository.getReferenceById(id);
        
        // Atualiza os dados
        produto.setNome(dados.getNome());
        produto.setDescricao(dados.getDescricao());
        produto.setPreco(dados.getPreco());
        produto.setCategoria(dados.getCategoria());

        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
