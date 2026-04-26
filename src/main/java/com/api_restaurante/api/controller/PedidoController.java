package com.api_restaurante.api.controller;

import com.api_restaurante.api.dto.pedido.DadosCadastroPedido;
import com.api_restaurante.api.dto.pedido.DadosDetalhamentoPedido;
import com.api_restaurante.api.model.Usuario;
import com.api_restaurante.api.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    public ResponseEntity criar(@RequestBody @Valid DadosCadastroPedido dados, 
                                @AuthenticationPrincipal Usuario usuario,
                                UriComponentsBuilder uriBuilder) {
        var detalhamento = service.criarPedido(dados, usuario);
        var uri = uriBuilder.path("/pedidos/{id}").buildAndExpand(detalhamento.id()).toUri();
        return ResponseEntity.created(uri).body(detalhamento);
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoPedido>> listar(@AuthenticationPrincipal Usuario usuario) {
        var pedidos = service.listarPedidosDoUsuario(usuario);
        return ResponseEntity.ok(pedidos);
    }
}
