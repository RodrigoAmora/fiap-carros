package br.com.fiap.fiapcarros.service;

import br.com.fiap.fiapcarros.api.client.AuthAPIClient;
import br.com.fiap.fiapcarros.dto.request.LoginRequest;
import br.com.fiap.fiapcarros.dto.response.LoginResponse;
import br.com.fiap.fiapcarros.manager.TokenManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final TokenManager tokenManager;
    private final RestTemplate restTemplate;
    private final AuthAPIClient authAPIClient;

    public AuthService(TokenManager tokenManager,
                       RestTemplate restTemplate,
                       AuthAPIClient authAPIClient) {
        this.tokenManager = tokenManager;
        this.restTemplate = restTemplate;
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

}
