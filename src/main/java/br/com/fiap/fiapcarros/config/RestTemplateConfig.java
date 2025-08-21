package br.com.fiap.fiapcarros.config;

import br.com.fiap.fiapcarros.api.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(authInterceptor);
        return restTemplate;
    }
}
