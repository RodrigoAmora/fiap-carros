package br.com.fiap.fiapcarros.dto.request;

import br.com.fiap.fiapcarros.model.TipoPagamento;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CompraRequestDTO(
       @NotBlank(message = "O nome do cliente é obrigatório")
       @JsonProperty("nome_cliente")
       String nomeCliente,

       @NotNull(message = "O ID do carro é obrigatório")
       @JsonProperty("carro_id")
       Long carroId,

       @NotNull(message = "O tipo de pagamento é obrigatório")
       @JsonProperty("tipo_pagamento")
       TipoPagamento tipoPagamento,

       @JsonProperty("valor_entrada")
       BigDecimal valorEntrada,

       @JsonProperty("valor_financiado")
       BigDecimal valorFinanciado
) {}
