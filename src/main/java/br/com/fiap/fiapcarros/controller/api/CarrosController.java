package br.com.fiap.fiapcarros.controller.api;

import br.com.fiap.fiapcarros.dto.CarroDTO;
import br.com.fiap.fiapcarros.manager.TokenManager;
import br.com.fiap.fiapcarros.service.AuthService;
import br.com.fiap.fiapcarros.service.CarroService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carro")
@SecurityRequirement(name = "bearerAuth")
public class CarrosController {

    private final CarroService carroService;

    public CarrosController(CarroService carroService) {
        this.carroService = carroService;
    }

    @GetMapping("{id}")
    public ResponseEntity<CarroDTO> buscarCarroPeloId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(carroService.buscarCarroPeloId(id));
    }

}
