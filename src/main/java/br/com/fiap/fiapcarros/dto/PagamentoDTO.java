package br.com.fiap.fiapcarros.dto;

import br.com.fiap.fiapcarros.model.TipoPagamento;

import java.math.BigDecimal;

public record PagamentoDTO(

        Long id,
        TipoPagamento tipoPagamento,
        BigDecimal valorEntrada,
        BigDecimal valorFinanciado

) {}
