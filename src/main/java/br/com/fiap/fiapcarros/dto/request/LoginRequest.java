package br.com.fiap.fiapcarros.dto.request;

public record LoginRequest(
        String email,
        String senha
) {}
