package com.api_restaurante.api.controller;

import com.api_restaurante.api.dto.pedido.DadosDetalhamentoPedido;
import com.api_restaurante.api.dto.pedido.DadosAtualizacaoStatusPedido;
import com.api_restaurante.api.model.StatusPedido;
import com.api_restaurante.api.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/pedidos")
public class AdminPedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoPedido>> listar(
            @PageableDefault(size = 10, sort = {"data"}) Pageable paginacao,
            @RequestParam(required = false) StatusPedido status) {
        var pedidos = service.listarPedidosAdmin(paginacao, status);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPedido> buscarPorId(@PathVariable Long id) {
        var pedido = service.buscarPedidoAdmin(id);
        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<DadosDetalhamentoPedido> atualizarStatus(
            @PathVariable Long id,
            @RequestBody DadosAtualizacaoStatusPedido dados) {
        var pedido = service.atualizarStatusPedido(id, dados.status());
        return ResponseEntity.ok(pedido);
    }
}
