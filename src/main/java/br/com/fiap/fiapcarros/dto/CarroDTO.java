package br.com.fiap.fiapcarros.dto;

import br.com.fiap.fiapcarros.model.CarroStatus;

import java.math.BigDecimal;

public record CarroDTO(

        Long id,
        String nome,
        String modelo,
        String cor,
        String ano,
        BigDecimal preco,
        CarroStatus carroStatus

) {}
