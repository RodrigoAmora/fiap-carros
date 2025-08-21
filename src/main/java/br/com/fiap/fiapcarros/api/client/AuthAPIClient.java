package br.com.fiap.fiapcarros.api.client;

import br.com.fiap.fiapcarros.dto.request.LoginRequest;
import br.com.fiap.fiapcarros.dto.response.LoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", url = "${ms-auth.url}")
public interface AuthAPIClient {

    @PostMapping("/api/auth/login")
    LoginResponse login(@RequestBody LoginRequest request);

}
