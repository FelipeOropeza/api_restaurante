package com.api_restaurante.api.repository;

import com.api_restaurante.api.model.Pedido;
import com.api_restaurante.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findAllByUsuario(Usuario usuario);
}
