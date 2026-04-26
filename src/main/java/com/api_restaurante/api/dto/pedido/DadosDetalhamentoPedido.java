package com.api_restaurante.api.dto.pedido;

import com.api_restaurante.api.model.MetodoPagamento;
import com.api_restaurante.api.model.Pedido;
import com.api_restaurante.api.model.StatusPedido;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoPedido(
    Long id,
    LocalDateTime data,
    StatusPedido status,
    String cep,
    String complemento,
    MetodoPagamento metodoPagamento,
    BigDecimal valorTotal,
    List<DadosDetalhamentoItemPedido> itens
) {
    public DadosDetalhamentoPedido(Pedido pedido) {
        this(
            pedido.getId(),
            pedido.getData(),
            pedido.getStatus(),
            pedido.getCep(),
            pedido.getComplemento(),
            pedido.getMetodoPagamento(),
            pedido.getValorTotal(),
            pedido.getItens().stream().map(DadosDetalhamentoItemPedido::new).toList()
        );
    }
}
