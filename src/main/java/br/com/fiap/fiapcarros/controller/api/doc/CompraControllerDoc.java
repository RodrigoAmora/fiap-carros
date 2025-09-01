package br.com.fiap.fiapcarros.controller.api.doc;

import br.com.fiap.fiapcarros.dto.request.CompraRequestDTO;
import br.com.fiap.fiapcarros.dto.response.CompraResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Endpoints de Compra")
public interface CompraControllerDoc {

    @Operation(summary = "Realizar compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Realizar compra de um veículo", content = @Content(schema = @Schema(implementation = CompraRequestDTO.class))),
    })
    ResponseEntity<CompraResponseDTO> realizarCompra(CompraRequestDTO request);

}
