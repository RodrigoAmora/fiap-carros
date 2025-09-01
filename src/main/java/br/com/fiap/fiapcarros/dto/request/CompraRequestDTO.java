package br.com.fiap.fiapcarros.dto.request;

import br.com.fiap.fiapcarros.model.TipoPagamento;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CompraRequestDTO(
       @NotBlank(message = "O ID do usuário é obrigatório")
       @JsonProperty("id_usuário")
       @Schema(description = "ID do usuário", example = "95220135-ed5e-49f5-aea3-668d25d68d42")
       String idUsuario,

       @NotNull(message = "O ID do carro é obrigatório")
       @JsonProperty("id_carro")
       @Schema(description = "ID do carro", example = "1")
       Long idCarro,

       @NotNull(message = "O tipo de pagamento é obrigatório")
       @JsonProperty("tipo_pagamento")
       @Schema(description = "Tipo do pagamento", example = "A_VISTA")
       TipoPagamento tipoPagamento,

       @JsonProperty("valor_entrada")
       @Schema(description = "Valor de entrada, se for a vista é o valor total do veículo", example = "10000")
       BigDecimal valorEntrada
) {}
