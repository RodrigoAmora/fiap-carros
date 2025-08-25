package br.com.fiap.fiapcarros.controller.api;

import br.com.fiap.fiapcarros.api.dto.UsuarioDTO;
import br.com.fiap.fiapcarros.controller.api.doc.AuthControllerDoc;
import br.com.fiap.fiapcarros.dto.request.LoginRequest;
import br.com.fiap.fiapcarros.dto.response.LoginResponse;
import br.com.fiap.fiapcarros.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fiap/auth")
public class AuthController implements AuthControllerDoc {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = this.authService.login(request);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> getUsuarioLogado() {
        return ResponseEntity.ok(authService.getUsuarioLogado());
    }
}
