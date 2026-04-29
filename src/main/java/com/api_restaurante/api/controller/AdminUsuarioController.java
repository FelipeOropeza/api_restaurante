package com.api_restaurante.api.controller;

import com.api_restaurante.api.dto.security.DadosAtualizacaoTipoUsuario;
import com.api_restaurante.api.dto.security.DadosListagemUsuario;
import com.api_restaurante.api.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/usuarios")
public class AdminUsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    public ResponseEntity<Page<DadosListagemUsuario>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAll(paginacao).map(DadosListagemUsuario::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}/tipo")
    @Transactional
    public ResponseEntity<DadosListagemUsuario> atualizarTipo(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoTipoUsuario dados) {
        var usuario = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setTipo(dados.tipo());
        return ResponseEntity.ok(new DadosListagemUsuario(usuario));
    }
}
