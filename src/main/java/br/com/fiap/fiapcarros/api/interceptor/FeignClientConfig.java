package br.com.fiap.fiapcarros.api.interceptor;

import br.com.fiap.fiapcarros.manager.TokenManager;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    private final TokenManager tokenManager;

    public FeignClientConfig(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = tokenManager.getStoredToken();
            if (token != null) {
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}
