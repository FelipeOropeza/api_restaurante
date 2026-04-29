package com.api_restaurante.api.repository;

import com.api_restaurante.api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    boolean existsByCategoriaId(Long idCategoria);
    org.springframework.data.domain.Page<Produto> findAllByAtivoTrue(org.springframework.data.domain.Pageable paginacao);
}
