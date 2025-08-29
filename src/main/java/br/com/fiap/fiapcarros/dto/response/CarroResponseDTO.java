package br.com.fiap.fiapcarros.dto.response;

import br.com.fiap.fiapcarros.model.CarroStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CarroResponseDTO(

        @JsonProperty("id")
        Long id,

        @JsonProperty("marca")
        String marca,

        @JsonProperty("modelo")
        String modelo,

        @JsonProperty("cor")
        String cor,

        @JsonProperty("ano")
        String ano,

        @JsonProperty("preco")
        BigDecimal preco,

        @JsonProperty("status")
        CarroStatus carroStatus

) {}
