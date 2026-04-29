package com.api_restaurante.api.controller;

import com.api_restaurante.api.dto.dashboard.DadosResumoDashboard;
import com.api_restaurante.api.dto.dashboard.ProdutoMaisVendido;
import com.api_restaurante.api.model.StatusPedido;
import com.api_restaurante.api.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/resumo")
    public ResponseEntity<DadosResumoDashboard> resumo() {
        LocalDateTime inicioDoDia = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime fimDoDia = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        BigDecimal faturamentoHoje = pedidoRepository.somarFaturamentoEntreDatas(inicioDoDia, fimDoDia);
        if (faturamentoHoje == null) {
            faturamentoHoje = BigDecimal.ZERO;
        }

        long pedidosPendentes = pedidoRepository.countByStatus(StatusPedido.PENDENTE) +
                                pedidoRepository.countByStatus(StatusPedido.PREPARANDO);

        long pedidosFinalizadosHoje = pedidoRepository.countByStatusAndDataBetween(
                StatusPedido.ENTREGUE, inicioDoDia, fimDoDia);

        List<ProdutoMaisVendido> produtosMaisVendidos = pedidoRepository.findProdutosMaisVendidos(PageRequest.of(0, 5)).getContent();

        DadosResumoDashboard resumo = new DadosResumoDashboard(
                faturamentoHoje,
                pedidosPendentes,
                pedidosFinalizadosHoje,
                produtosMaisVendidos
        );

        return ResponseEntity.ok(resumo);
    }
}
