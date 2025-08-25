package br.com.fiap.fiapcarros.dto.response;

import br.com.fiap.fiapcarros.api.dto.Role;

public record LoginResponse(
        String token,
        String tipo,
        String nome,
        String email,
        Role role
) {}
