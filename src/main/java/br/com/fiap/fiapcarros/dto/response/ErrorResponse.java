package br.com.fiap.fiapcarros.dto.response;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String mensagem,
        LocalDateTime timestamp
) {}
