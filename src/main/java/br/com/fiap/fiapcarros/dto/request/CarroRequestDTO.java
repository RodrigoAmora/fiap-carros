package br.com.fiap.fiapcarros.dto.request;

import java.math.BigDecimal;

public record CarroRequestDTO(
        String marca,
        String modelo,
        String cor,
        String ano,
        BigDecimal preco
) {}
