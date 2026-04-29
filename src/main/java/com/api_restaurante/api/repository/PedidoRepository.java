package com.api_restaurante.api.repository;

import com.api_restaurante.api.model.Pedido;
import com.api_restaurante.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findAllByUsuario(Usuario usuario);
    org.springframework.data.domain.Page<Pedido> findAllByStatus(com.api_restaurante.api.model.StatusPedido status, org.springframework.data.domain.Pageable pageable);
    
    @org.springframework.data.jpa.repository.Query("SELECT SUM(p.valorTotal) FROM Pedido p WHERE p.data >= :inicio AND p.data <= :fim AND p.status = 'ENTREGUE'")
    java.math.BigDecimal somarFaturamentoEntreDatas(java.time.LocalDateTime inicio, java.time.LocalDateTime fim);
    
    long countByStatus(com.api_restaurante.api.model.StatusPedido status);
    
    long countByStatusAndDataBetween(com.api_restaurante.api.model.StatusPedido status, java.time.LocalDateTime start, java.time.LocalDateTime end);
    
    @org.springframework.data.jpa.repository.Query("SELECT new com.api_restaurante.api.dto.dashboard.ProdutoMaisVendido(i.produto.nome, SUM(i.quantidade)) FROM Pedido p JOIN p.itens i WHERE p.status = 'ENTREGUE' GROUP BY i.produto.nome ORDER BY SUM(i.quantidade) DESC")
    org.springframework.data.domain.Page<com.api_restaurante.api.dto.dashboard.ProdutoMaisVendido> findProdutosMaisVendidos(org.springframework.data.domain.Pageable pageable);
}
