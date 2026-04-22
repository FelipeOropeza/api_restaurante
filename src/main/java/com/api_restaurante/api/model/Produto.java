package com.api_restaurante.api.model;

import jakarta.persistence.*;
import lombok.*;
import com.api_restaurante.api.dto.produto.DadosAtualizacaoProduto;
import com.api_restaurante.api.dto.produto.DadosCadastroProduto;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Produto(DadosCadastroProduto dados, Categoria categoria) {
        this.nome = dados.nome();
        this.descricao = dados.descricao();
        this.preco = dados.preco();
        this.categoria = categoria;
    }

    public void atualizarInformacoes(DadosAtualizacaoProduto dados, Categoria categoria) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.descricao() != null) {
            this.descricao = dados.descricao();
        }
        if (dados.preco() != null) {
            this.preco = dados.preco();
        }
        if (categoria != null) {
            this.categoria = categoria;
        }
    }
}
