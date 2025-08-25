package br.com.fiap.fiapcarros.service;

import br.com.fiap.fiapcarros.api.client.AuthAPIClient;
import br.com.fiap.fiapcarros.api.dto.UsuarioDTO;
import br.com.fiap.fiapcarros.dto.request.LoginRequest;
import br.com.fiap.fiapcarros.dto.response.LoginResponse;
import br.com.fiap.fiapcarros.manager.TokenManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final TokenManager tokenManager;
    private final AuthAPIClient authAPIClient;

    public AuthService(TokenManager tokenManager,
                       AuthAPIClient authAPIClient) {
        this.tokenManager = tokenManager;
        this.authAPIClient = authAPIClient;
    }

    public LoginResponse login(LoginRequest request) {
        LoginResponse response = this.authAPIClient.login(request);

        if (response != null) {
            tokenManager.storeToken(response.token());
        }

        return response;
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }

    public UsuarioDTO getUsuarioLogado() {
        return this.authAPIClient.getUsuarioLogado();
    }
}
