package br.com.fiap.fiapcarros.service;

import br.com.fiap.fiapcarros.api.client.AuthAPIClient;
import br.com.fiap.fiapcarros.api.dto.ERole;
import br.com.fiap.fiapcarros.api.dto.Role;
import br.com.fiap.fiapcarros.api.dto.UsuarioDTO;
import br.com.fiap.fiapcarros.dto.PagamentoDTO;
import br.com.fiap.fiapcarros.dto.request.CompraRequestDTO;
import br.com.fiap.fiapcarros.dto.response.CarroResponseDTO;
import br.com.fiap.fiapcarros.dto.response.CompraResponseDTO;
import br.com.fiap.fiapcarros.exception.CarroException;
import br.com.fiap.fiapcarros.manager.CarroMapper;
import br.com.fiap.fiapcarros.manager.CompraMapper;
import br.com.fiap.fiapcarros.model.*;
import br.com.fiap.fiapcarros.repository.CarroReposiotry;
import br.com.fiap.fiapcarros.repository.CompraRepository;
import br.com.fiap.fiapcarros.repository.PagamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompraServiceTest {

    @Mock
    private CarroReposiotry carroReposiotry;

    @Mock
    private CarroMapper carroMapper;

    @Mock
    private CompraRepository compraRepository;

    @Mock
    private CompraMapper compraMapper;

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private AuthAPIClient authAPIClient;

    @InjectMocks
    private CompraService compraService;

    @Test
    void realizarCompraAVistaComSucesso() {
        // Arrange
        Long idCarro = 1L;
        String idUsuario = "123";
        BigDecimal precoCarro = new BigDecimal("50000.00");

        CompraRequestDTO requestDTO = new CompraRequestDTO(
                idUsuario,
                idCarro,
                TipoPagamento.A_VISTA,
                null
        );

        Carro carro = new Carro();
        carro.setId(idCarro);
        carro.setPreco(precoCarro);
        carro.setCarroStatus(CarroStatus.A_VENDA);

        CarroResponseDTO carroResponseDTO = new CarroResponseDTO(
                idCarro,
                "Fiat",
                "Mobi",
                "Azul",
                "2025",
                precoCarro,
                CarroStatus.A_VENDA
        );

        Role role = new Role();
        role.setName(ERole.ROLE_USER);
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                idUsuario,
                "Usuario Teste",
                "teste@email.com",
                "11122233344455",
                LocalDate.now(),
                LocalDateTime.now(),
                role
        );

        // Configuração dos mocks
        when(carroReposiotry.findById(idCarro)).thenReturn(Optional.of(carro));
        when(carroMapper.toDto(carro)).thenReturn(carroResponseDTO);
        when(carroMapper.toEntity(carroResponseDTO)).thenReturn(carro);
        when(authAPIClient.getUsuarioLogado()).thenReturn(usuarioDTO);
        when(pagamentoRepository.save(any(Pagamento.class))).thenAnswer(i -> i.getArgument(0));
        when(compraRepository.save(any(Compra.class))).thenAnswer(i -> {
            Compra compra = i.getArgument(0);
            assertNotNull(compra.getCarro());
            assertNotNull(compra.getPagamento());
            assertEquals(CompraStatus.CONCLUIDA, compra.getStatus());
            assertEquals(idUsuario, compra.getIdUsuario());
            return compra;
        });

        PagamentoDTO pagamentoDTO = new PagamentoDTO(1L, TipoPagamento.A_VISTA, precoCarro, null);

        when(compraMapper.toDTO(any(Compra.class))).thenReturn(new CompraResponseDTO(
                1L, // id da compra
                idUsuario,
                carroResponseDTO,
                CompraStatus.CONCLUIDA,
                pagamentoDTO,
                LocalDateTime.now()
        ));


        // Act
        CompraResponseDTO resultado = compraService.realizarCompra(requestDTO);

        // Assert
        assertNotNull(resultado);

        // Verifica se o pagamento foi salvo com os valores corretos
        verify(pagamentoRepository).save(argThat(pagamento ->
                pagamento.getTipoPagamento() == TipoPagamento.A_VISTA &&
                        pagamento.getValor().equals(precoCarro)
        ));

        // Verifica se a compra foi salva
        verify(compraRepository).save(any(Compra.class));

        // Verifica se o carro foi atualizado para VENDIDO
        verify(carroReposiotry).save(argThat(c ->
                c.getCarroStatus() == CarroStatus.VENDIDO
        ));
    }

    @Test
    void realizarCompraFinanciadaComSucesso() {
        // Arrange
        Long idCarro = 1L;
        String idUsuario = "123";
        BigDecimal precoCarro = new BigDecimal("50000.00");
        BigDecimal valorEntrada = new BigDecimal("10000.00");

        CompraRequestDTO requestDTO = new CompraRequestDTO(
                idUsuario,
                idCarro,
                TipoPagamento.FINANCIADO,
                valorEntrada
        );

        Carro carro = new Carro();
        carro.setId(idCarro);
        carro.setPreco(precoCarro);

        CarroResponseDTO carroResponseDTO = new CarroResponseDTO(
                idCarro,
                "Fiat",
                "Mobi",
                "Azul",
                "2025",
                precoCarro,
                CarroStatus.A_VENDA
        );

        Role role = new Role();
        role.setName(ERole.ROLE_USER);
        UsuarioDTO usuarioDTO = new UsuarioDTO(idUsuario, "Outro Usuario", "outro@email.com", "11122233344455",
                LocalDate.now(), LocalDateTime.now(), role);

        when(carroReposiotry.findById(idCarro)).thenReturn(Optional.of(carro));
        when(carroMapper.toDto(carro)).thenReturn(carroResponseDTO);
        when(carroMapper.toEntity(carroResponseDTO)).thenReturn(carro);
        when(authAPIClient.getUsuarioLogado()).thenReturn(usuarioDTO);
        when(pagamentoRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(compraRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        PagamentoDTO pagamentoDTO = new PagamentoDTO(1L, TipoPagamento.A_VISTA, valorEntrada, new BigDecimal("40000.00"));

        when(compraMapper.toDTO(any(Compra.class))).thenReturn(new CompraResponseDTO(
                1L, // id da compra
                idUsuario,
                carroResponseDTO,
                CompraStatus.CONCLUIDA,
                pagamentoDTO,
                LocalDateTime.now()
        ));

        // Act
        CompraResponseDTO resultado = compraService.realizarCompra(requestDTO);

        // Assert
        assertNotNull(resultado);
        verify(pagamentoRepository).save(argThat(p ->
                p.getTipoPagamento().equals(TipoPagamento.FINANCIADO) &&
                        p.getValor().equals(valorEntrada)
        ));
        verify(compraRepository).save(argThat(c ->
                c.getStatus().equals(CompraStatus.EM_ANDAMENTO)
        ));
    }

    @Test
    void realizarCompraFinanciadaSemEntradaDeveLancarExcecao() {
        // Arrange
        CompraRequestDTO requestDTO = new CompraRequestDTO(
                "123",
                1L,
                TipoPagamento.FINANCIADO,
                null
        );

        // Act & Assert
        assertThrows(CarroException.class, () ->
                compraService.realizarCompra(requestDTO)
        );
    }

    @Test
    void realizarCompraComCarroInexistenteDeveLancarExcecao() {
        // Arrange
        CompraRequestDTO requestDTO = new CompraRequestDTO(
                "123",
                1L,
                TipoPagamento.A_VISTA,
                null
        );

        when(carroReposiotry.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CarroException.class, () ->
                compraService.realizarCompra(requestDTO)
        );
    }

    @Test
    void realizarCompraComUsuarioDiferenteDeveLancarExcecao() {
        // Arrange
        Long idCarro = 1L;
        String idUsuario = "123";

        CompraRequestDTO requestDTO = new CompraRequestDTO(
                idUsuario,
                idCarro,
                TipoPagamento.A_VISTA,
                null
        );

        Carro carro = new Carro();
        when(carroReposiotry.findById(idCarro)).thenReturn(Optional.of(carro));
        when(carroMapper.toDto(carro)).thenReturn(new CarroResponseDTO(
                idCarro, "Teste", "2023", "Azul", "2024", new BigDecimal("50000.00"), CarroStatus.A_VENDA
        ));

        Role role = new Role();
        role.setName(ERole.ROLE_USER);

        when(authAPIClient.getUsuarioLogado()).thenReturn(
                new UsuarioDTO("456", "Outro Usuario", "outro@email.com", "11122233344455",
                                LocalDate.now(), LocalDateTime.now(), role));

        // Act & Assert
        assertThrows(CarroException.class, () ->
                compraService.realizarCompra(requestDTO)
        );
    }
}
