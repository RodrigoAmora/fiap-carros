package br.com.fiap.fiapcarros.controller.api;

import br.com.fiap.fiapcarros.api.client.AuthAPIClient;
import br.com.fiap.fiapcarros.dto.request.LoginRequest;
import br.com.fiap.fiapcarros.dto.response.LoginResponse;
import br.com.fiap.fiapcarros.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fiap/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = this.authService.login(request);
        return ResponseEntity.ok(response);
    }

}
