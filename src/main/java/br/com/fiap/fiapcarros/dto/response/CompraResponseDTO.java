package br.com.fiap.fiapcarros.dto.response;

import br.com.fiap.fiapcarros.dto.CarroDTO;
import br.com.fiap.fiapcarros.dto.PagamentoDTO;
import br.com.fiap.fiapcarros.model.CompraStatus;

import java.time.LocalDateTime;

public record CompraResponseDTO(
        Long id,
        String nomeCliente,
        CarroDTO carro,
        CompraStatus status,
        PagamentoDTO pagamento,
        LocalDateTime dataCompra
) {}
