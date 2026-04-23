package com.api_restaurante.api.controller;

import com.api_restaurante.api.dto.categoria.DadosAtualizacaoCategoria;
import com.api_restaurante.api.dto.categoria.DadosCadastroCategoria;
import com.api_restaurante.api.dto.categoria.DadosListagemCategoria;
import com.api_restaurante.api.model.Categoria;
import com.api_restaurante.api.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<Page<DadosListagemCategoria>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAll(paginacao).map(DadosListagemCategoria::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosListagemCategoria> buscarPorId(@PathVariable Long id) {
        var categoria = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosListagemCategoria(categoria));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosListagemCategoria> atualizar(@RequestBody @Valid DadosAtualizacaoCategoria dados) {
        var categoria = repository.getReferenceById(dados.id());
        categoria.atualizarInformacoes(dados.nome());
        return ResponseEntity.ok(new DadosListagemCategoria(categoria));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
