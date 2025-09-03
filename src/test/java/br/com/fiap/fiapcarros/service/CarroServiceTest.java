package br.com.fiap.fiapcarros.service;

import br.com.fiap.fiapcarros.dto.request.CarroRequestDTO;
import br.com.fiap.fiapcarros.dto.response.CarroResponseDTO;
import br.com.fiap.fiapcarros.exception.CarroException;
import br.com.fiap.fiapcarros.manager.CarroMapper;
import br.com.fiap.fiapcarros.model.Carro;
import br.com.fiap.fiapcarros.model.CarroStatus;
import br.com.fiap.fiapcarros.repository.CarroReposiotry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarroServiceTest {

    @Mock
    private CarroReposiotry carroReposiotry;

    @Mock
    private CarroMapper carroMapper;

    @InjectMocks
    private CarroService carroService;

    @Test
    void salvarCarroComSucesso() {
        // Arrange
        CarroRequestDTO requestDTO = new CarroRequestDTO(
                "Fiat",
                "Mobi",
                "Azul",
                "2025",
                new BigDecimal("50000.00")
        );

        Carro carro = new Carro();
        carro.setId(1L);
        carro.setMarca("Fiat");
        carro.setModelo("Mobi");
        carro.setCor("Azul");
        carro.setAno("2025");
        carro.setPreco(new BigDecimal("50000.00"));
        carro.setCarroStatus(CarroStatus.A_VENDA);

        CarroResponseDTO responseDTO = new CarroResponseDTO(
                1L,
                "Fiat",
                "Mobi",
                "Azul",
                "2025",
                new BigDecimal("50000.00"),
                CarroStatus.A_VENDA
        );

        when(carroMapper.toEntity(requestDTO)).thenReturn(carro);
        when(carroReposiotry.save(any(Carro.class))).thenReturn(carro);
        when(carroMapper.toDto(carro)).thenReturn(responseDTO);

        // Act
        CarroResponseDTO resultado = carroService.salvarCarro(requestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("Fiat", resultado.marca());
        assertEquals("Mobi", resultado.modelo());
        assertEquals(CarroStatus.A_VENDA, resultado.carroStatus());
        verify(carroReposiotry).save(any(Carro.class));
    }

    @Test
    void atualizarCarroComSucesso() {
        // Arrange
        Long carroId = 1L;
        CarroRequestDTO requestDTO = new CarroRequestDTO(
                "Fiat",
                "Argo",
                "Vermelho",
                "2025",
                new BigDecimal("60000.00")
        );

        Carro carroExistente = new Carro();
        carroExistente.setId(carroId);
        carroExistente.setCarroStatus(CarroStatus.A_VENDA);

        CarroResponseDTO responseDTO = new CarroResponseDTO(
                carroId,
                "Fiat",
                "Argo",
                "Vermelho",
                "2025",
                new BigDecimal("60000.00"),
                CarroStatus.A_VENDA
        );

        when(carroReposiotry.findById(carroId)).thenReturn(Optional.of(carroExistente));
        when(carroReposiotry.save(any(Carro.class))).thenReturn(carroExistente);
        when(carroMapper.toDto(carroExistente)).thenReturn(responseDTO);

        // Act
        CarroResponseDTO resultado = carroService.atualizarCarro(requestDTO, carroId);

        // Assert
        assertNotNull(resultado);
        assertEquals("Fiat", resultado.marca());
        assertEquals("Argo", resultado.modelo());
        assertEquals("Vermelho", resultado.cor());
        verify(carroReposiotry).save(any(Carro.class));
    }

    @Test
    void atualizarCarroVendidoDeveLancarExcecao() {
        // Arrange
        Long carroId = 1L;
        CarroRequestDTO requestDTO = new CarroRequestDTO(
                "Fiat",
                "Argo",
                "Vermelho",
                "2025",
                new BigDecimal("60000.00")
        );

        Carro carroVendido = new Carro();
        carroVendido.setId(carroId);
        carroVendido.setCarroStatus(CarroStatus.VENDIDO);

        when(carroReposiotry.findById(carroId)).thenReturn(Optional.of(carroVendido));

        // Act & Assert
        assertThrows(CarroException.class, () ->
                carroService.atualizarCarro(requestDTO, carroId)
        );
        verify(carroReposiotry, never()).save(any(Carro.class));
    }

    @Test
    void buscarCarroExistenteComSucesso() {
        // Arrange
        Long carroId = 1L;
        Carro carro = new Carro();
        carro.setId(carroId);

        CarroResponseDTO responseDTO = new CarroResponseDTO(
                carroId,
                "Fiat",
                "Mobi",
                "Azul",
                "2025",
                new BigDecimal("50000.00"),
                CarroStatus.A_VENDA
        );

        when(carroReposiotry.findById(carroId)).thenReturn(Optional.of(carro));
        when(carroMapper.toDto(carro)).thenReturn(responseDTO);

        // Act
        CarroResponseDTO resultado = carroService.buscarCarro(carroId);

        // Assert
        assertNotNull(resultado);
        assertEquals(carroId, resultado.id());
    }

    @Test
    void buscarCarroInexistenteDeveLancarExcecao() {
        // Arrange
        Long carroId = 1L;
        when(carroReposiotry.findById(carroId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CarroException.class, () ->
                carroService.buscarCarro(carroId)
        );
    }

    @Test
    void buscarCarrosAVendaComSucesso() {
        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        List<Carro> carros = List.of(new Carro(), new Carro());
        Page<Carro> carrosPage = new PageImpl<>(carros);

        when(carroReposiotry.findAllByStatusOrderByPrecoAsc(eq(CarroStatus.A_VENDA), eq(pageable)))
                .thenReturn(carrosPage);
        when(carroMapper.toDto(any(Carro.class))).thenReturn(
                new CarroResponseDTO(1L, "Marca", "Modelo", "Cor", "2025",
                        new BigDecimal("50000.00"), CarroStatus.A_VENDA)
        );

        // Act
        Page<CarroResponseDTO> resultado = carroService.buscarCarrosAVenda(page, size);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());
        verify(carroReposiotry).findAllByStatusOrderByPrecoAsc(eq(CarroStatus.A_VENDA), eq(pageable));
    }

    @Test
    void buscarCarrosVendidosComSucesso() {
        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        List<Carro> carros = List.of(new Carro(), new Carro());
        Page<Carro> carrosPage = new PageImpl<>(carros);

        when(carroReposiotry.findAllByStatusOrderByPrecoAsc(eq(CarroStatus.VENDIDO), eq(pageable)))
                .thenReturn(carrosPage);
        when(carroMapper.toDto(any(Carro.class))).thenReturn(
                new CarroResponseDTO(1L, "Marca", "Modelo", "Cor", "2025",
                        new BigDecimal("50000.00"), CarroStatus.VENDIDO)
        );

        // Act
        Page<CarroResponseDTO> resultado = carroService.buscarCarrosVendidos(page, size);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());
        verify(carroReposiotry).findAllByStatusOrderByPrecoAsc(eq(CarroStatus.VENDIDO), eq(pageable));
    }
}
