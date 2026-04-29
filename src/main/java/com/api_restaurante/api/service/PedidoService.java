package com.api_restaurante.api.service;

import com.api_restaurante.api.dto.pedido.DadosCadastroPedido;
import com.api_restaurante.api.dto.pedido.DadosDetalhamentoPedido;
import com.api_restaurante.api.model.ItemPedido;
import com.api_restaurante.api.model.Pedido;
import com.api_restaurante.api.model.Produto;
import com.api_restaurante.api.model.Usuario;
import com.api_restaurante.api.repository.PedidoRepository;
import com.api_restaurante.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.api_restaurante.api.model.StatusPedido;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public DadosDetalhamentoPedido criarPedido(DadosCadastroPedido dados, Usuario usuario) {
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setCep(dados.cep());
        pedido.setComplemento(dados.complemento());
        pedido.setMetodoPagamento(dados.metodoPagamento());

        BigDecimal total = BigDecimal.ZERO;

        for (var itemDto : dados.itens()) {
            Produto produto = produtoRepository.findById(itemDto.produtoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDto.produtoId()));
            
            ItemPedido item = new ItemPedido(produto, itemDto.quantidade());
            pedido.adicionarItem(item);
            
            BigDecimal subtotal = produto.getPreco().multiply(new BigDecimal(itemDto.quantidade()));
            total = total.add(subtotal);
        }

        pedido.setValorTotal(total);
        pedidoRepository.save(pedido);

        return new DadosDetalhamentoPedido(pedido);
    }

    public List<DadosDetalhamentoPedido> listarPedidosDoUsuario(Usuario usuario) {
        return pedidoRepository.findAllByUsuario(usuario).stream()
                .map(DadosDetalhamentoPedido::new)
                .toList();
    }

    public Page<DadosDetalhamentoPedido> listarPedidosAdmin(Pageable paginacao, StatusPedido status) {
        if (status != null) {
            return pedidoRepository.findAllByStatus(status, paginacao).map(DadosDetalhamentoPedido::new);
        }
        return pedidoRepository.findAll(paginacao).map(DadosDetalhamentoPedido::new);
    }

    public DadosDetalhamentoPedido buscarPedidoAdmin(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        return new DadosDetalhamentoPedido(pedido);
    }

    @Transactional
    public DadosDetalhamentoPedido atualizarStatusPedido(Long id, StatusPedido status) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setStatus(status);
        return new DadosDetalhamentoPedido(pedido);
    }
}
