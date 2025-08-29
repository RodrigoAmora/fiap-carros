package br.com.fiap.fiapcarros.controller.api;

import br.com.fiap.fiapcarros.dto.response.CarroResponseDTO;
import br.com.fiap.fiapcarros.service.CarroService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CarroResponseDTO> buscarCarroPeloId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(carroService.buscarCarroPeloId(id));
    }

}
