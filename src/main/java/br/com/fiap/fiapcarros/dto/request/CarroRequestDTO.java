package br.com.fiap.fiapcarros.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record CarroRequestDTO(
        @Schema(description = "Marco do veículo", example = "Fiat")
        String marca,

        @Schema(description = "Modelo do veículo", example = "Mobi")
        String modelo,

        @Schema(description = "Cor do veículo", example = "Preto")
        String cor,

        @Schema(description = "Ano do veículo", example = "2025")
        String ano,

        @Schema(description = "Preço do veículo", example = "50000.00")
        BigDecimal preco
) {}
