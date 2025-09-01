package br.com.fiap.fiapcarros.dto.response;

import br.com.fiap.fiapcarros.dto.PagamentoDTO;
import br.com.fiap.fiapcarros.model.CompraStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record CompraResponseDTO(
        @JsonProperty("id")
        Long id,

        @JsonProperty("nome_cliente")
        String nomeCliente,

        @JsonProperty("carro")
        CarroResponseDTO carro,

        @JsonProperty("satus")
        CompraStatus status,

        @JsonProperty("pagamento")
        PagamentoDTO pagamento,

        @JsonProperty("data_compra")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dataCompra
) {}
