package br.com.fiap.fiapcarros.controller.api;

import br.com.fiap.fiapcarros.controller.api.doc.CarroControllerDoc;
import br.com.fiap.fiapcarros.dto.request.CarroRequestDTO;
import br.com.fiap.fiapcarros.dto.response.CarroResponseDTO;
import br.com.fiap.fiapcarros.service.CarroService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carro")
@SecurityRequirement(name = "bearerAuth")
public class CarrosController implements CarroControllerDoc {

    private final CarroService carroService;

    public CarrosController(CarroService carroService) {
        this.carroService = carroService;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') || hasRole('ADMIN')")
    public ResponseEntity<CarroResponseDTO> atualizarCarro(@RequestBody CarroRequestDTO request,
                                                           @PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(carroService.atualizarCarro(request, id));
    }

    @PostMapping
    @PreAuthorize("hasRole('MODERATOR') || hasRole('ADMIN')")
    public ResponseEntity<CarroResponseDTO> salvarCarro(@RequestBody CarroRequestDTO request) {
        return ResponseEntity.ok(carroService.salvarCarro(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarroResponseDTO> buscarCarroPeloId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(carroService.buscarCarro(id));
    }

    @GetMapping("/avenda")
    public ResponseEntity<Page<CarroResponseDTO>> buscarCarrosAVenda(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                     @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
        return ResponseEntity.ok(carroService.buscarCarrosAVenda(page, size));
    }

    @GetMapping("/vendidos")
    public ResponseEntity<Page<CarroResponseDTO>> buscarCarrosVendidos(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                       @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
        return ResponseEntity.ok(carroService.buscarCarrosVendidos(page, size));
    }

}
