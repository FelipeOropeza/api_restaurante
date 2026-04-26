package com.api_restaurante.api.dto.pedido;

import com.api_restaurante.api.model.MetodoPagamento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record DadosCadastroPedido(
    @NotBlank
    String cep,
    
    String complemento,
    
    @NotNull
    MetodoPagamento metodoPagamento,
    
    @NotEmpty
    @Valid
    List<DadosItemPedido> itens
) {}
