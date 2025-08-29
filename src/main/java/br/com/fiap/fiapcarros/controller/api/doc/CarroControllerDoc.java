package br.com.fiap.fiapcarros.controller.api.doc;

import br.com.fiap.fiapcarros.dto.CarroDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Endpoints de Carros")
public interface CarroControllerDoc {

    @Operation(summary = "Busca de Carro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca de Carro pelo id.", content = @Content(schema = @Schema(implementation = CarroDTO.class))),
    })
    ResponseEntity<CarroDTO> buscarCarroPeloId(Long id);

}
