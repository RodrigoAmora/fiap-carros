package br.com.fiap.fiapcarros.controller.api;

import br.com.fiap.fiapcarros.controller.api.doc.CompraControllerDoc;
import br.com.fiap.fiapcarros.dto.request.CompraRequestDTO;
import br.com.fiap.fiapcarros.dto.response.CompraResponseDTO;
import br.com.fiap.fiapcarros.service.CompraService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/compra")
@SecurityRequirement(name = "bearerAuth")
public class CompraController implements CompraControllerDoc {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @Override
    @PostMapping
    public ResponseEntity<CompraResponseDTO> realizarCompra(@RequestBody CompraRequestDTO request) {
        return ResponseEntity.ok(compraService.realizarCompra(request));
    }

}
