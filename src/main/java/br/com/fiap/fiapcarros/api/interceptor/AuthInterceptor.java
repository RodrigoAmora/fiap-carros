package br.com.fiap.fiapcarros.api.interceptor;

import br.com.fiap.fiapcarros.manager.TokenManager;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthInterceptor implements ClientHttpRequestInterceptor {

    private final TokenManager tokenManager;

    public AuthInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {

        String token = tokenManager.getStoredToken();
        if (token != null) {
            request.getHeaders().add("Authorization", "Bearer " + token);
        }

        return execution.execute(request, body);
    }
}
