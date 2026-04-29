package com.api_restaurante.api.controller;

import com.api_restaurante.api.dto.dashboard.ProdutoMaisVendido;
import com.api_restaurante.api.dto.dashboard.ResumoDashboard;
import com.api_restaurante.api.dto.pedido.AtualizacaoStatusPedido;
import com.api_restaurante.api.dto.pedido.DadosDetalhamentoPedido;
import com.api_restaurante.api.dto.security.AtualizacaoTipoUsuario;
import com.api_restaurante.api.dto.security.DadosListagemUsuario;
import com.api_restaurante.api.model.StatusPedido;
import com.api_restaurante.api.repository.PedidoRepository;
import com.api_restaurante.api.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;

    public AdminController(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // 1. Gestao de Pedidos

    @GetMapping("/pedidos")
    public ResponseEntity<Page<DadosDetalhamentoPedido>> listarPedidos(
            @RequestParam(required = false) StatusPedido status,
            @PageableDefault(size = 10, sort = {"data"}) Pageable paginacao) {
        
        Page<com.api_restaurante.api.model.Pedido> page;
        if (status != null) {
            page = pedidoRepository.findAllByStatus(status, paginacao);
        } else {
            page = pedidoRepository.findAll(paginacao);
        }
        return ResponseEntity.ok(page.map(DadosDetalhamentoPedido::new));
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<DadosDetalhamentoPedido> detalharPedido(@PathVariable Long id) {
        var pedido = pedidoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPedido(pedido));
    }

    @PutMapping("/pedidos/{id}/status")
    @Transactional
    public ResponseEntity<DadosDetalhamentoPedido> atualizarStatusPedido(@PathVariable Long id, @RequestBody @Valid AtualizacaoStatusPedido dados) {
        var pedido = pedidoRepository.getReferenceById(id);
        pedido.setStatus(dados.status());
        return ResponseEntity.ok(new DadosDetalhamentoPedido(pedido));
    }

    // 4. Dashboard e Metricas

    @GetMapping("/dashboard/resumo")
    public ResponseEntity<ResumoDashboard> resumoDashboard() {
        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        LocalDateTime fimDia = LocalDate.now().atTime(LocalTime.MAX);

        var faturamentoHoje = pedidoRepository.somarFaturamentoEntreDatas(inicioDia, fimDia);
        if (faturamentoHoje == null) {
            faturamentoHoje = java.math.BigDecimal.ZERO;
        }

        var pedidosPendentes = pedidoRepository.countByStatus(StatusPedido.PENDENTE);
        var pedidosFinalizadosHoje = pedidoRepository.countByStatusAndDataBetween(StatusPedido.ENTREGUE, inicioDia, fimDia);

        var pageRequest = PageRequest.of(0, 5);
        var pageProdutos = pedidoRepository.findProdutosMaisVendidos(pageRequest);
        List<ProdutoMaisVendido> produtosMaisVendidos = pageProdutos.getContent();

        var resumo = new ResumoDashboard(faturamentoHoje, pedidosPendentes, pedidosFinalizadosHoje, produtosMaisVendidos);
        return ResponseEntity.ok(resumo);
    }

    // 5. Gestao de Usuarios/Clientes

    @GetMapping("/usuarios")
    public ResponseEntity<Page<DadosListagemUsuario>> listarUsuarios(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = usuarioRepository.findAll(paginacao).map(DadosListagemUsuario::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/usuarios/{id}/tipo")
    @Transactional
    public ResponseEntity<DadosListagemUsuario> alterarTipoUsuario(@PathVariable Long id, @RequestBody @Valid AtualizacaoTipoUsuario dados) {
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.setTipo(dados.tipo());
        return ResponseEntity.ok(new DadosListagemUsuario(usuario));
    }
}
