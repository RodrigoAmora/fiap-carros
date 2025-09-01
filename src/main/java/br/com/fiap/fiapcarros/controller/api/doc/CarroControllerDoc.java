package br.com.fiap.fiapcarros.controller.api.doc;

import br.com.fiap.fiapcarros.dto.request.CarroRequestDTO;
import br.com.fiap.fiapcarros.dto.response.CarroResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

@Tag(name = "Endpoints de Carros")
public interface CarroControllerDoc {

    @Operation(summary = "Atualização de Carro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização de Carro.", content = @Content(schema = @Schema(implementation = CarroResponseDTO.class))),
    })
    ResponseEntity<CarroResponseDTO> atualizarCarro(CarroRequestDTO request, Long id);

    @Operation(summary = "Cadastro de Carro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro de Carro.", content = @Content(schema = @Schema(implementation = CarroResponseDTO.class))),
    })
    ResponseEntity<CarroResponseDTO> salvarCarro(CarroRequestDTO request);

    @Operation(summary = "Busca de carro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca de Carro pelo id.", content = @Content(schema = @Schema(implementation = CarroResponseDTO.class))),
    })
    ResponseEntity<CarroResponseDTO> buscarCarroPeloId(Long id);

    @Operation(summary = "Busca de carros a venda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca de carros a venda.", content = @Content(schema = @Schema(implementation = CarroResponseDTO.class))),
    })
    ResponseEntity<Page<CarroResponseDTO>> buscarCarrosAVenda(int page, int size);

    @Operation(summary = "Busca de carros vendidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca de carro vendidos.", content = @Content(schema = @Schema(implementation = CarroResponseDTO.class))),
    })
    ResponseEntity<Page<CarroResponseDTO>> buscarCarrosVendidos(int page, int size);
}
