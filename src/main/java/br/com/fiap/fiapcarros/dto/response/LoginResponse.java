package br.com.fiap.fiapcarros.dto.response;

public record LoginResponse(
        String token,
        String tipo,
        String nome,
        String email
) {}
