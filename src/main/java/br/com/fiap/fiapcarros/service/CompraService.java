package br.com.fiap.fiapcarros.service;

import br.com.fiap.fiapcarros.api.client.AuthAPIClient;
import br.com.fiap.fiapcarros.api.dto.UsuarioDTO;
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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CompraService {

    private final CarroReposiotry carroReposiotry;
    private final CarroMapper carroMapper;

    private final CompraRepository compraRepository;
    private final CompraMapper compraMapper;

    private final PagamentoRepository pagamentoRepository;

    private final AuthAPIClient authAPIClient;

    public CompraService(CarroReposiotry carroReposiotry,
                         CarroMapper carroMapper,
                         CompraRepository compraRepository,
                         CompraMapper compraMapper,
                         PagamentoRepository pagamentoRepository,
                         AuthAPIClient authAPIClient) {
        this.carroReposiotry = carroReposiotry;
        this.carroMapper = carroMapper;
        this.compraRepository = compraRepository;
        this.compraMapper = compraMapper;
        this.pagamentoRepository = pagamentoRepository;
        this.authAPIClient = authAPIClient;
    }

    public CompraResponseDTO realizarCompra(CompraRequestDTO request) {
        CarroResponseDTO carroResponseDTO = buscarCarroPeloId(request.idCarro());
        UsuarioDTO usuarioDTO = getUsuarioLogado(request.idUsuario());

        Compra compra = new Compra();
        Pagamento pagamento = new Pagamento();
        if (request.tipoPagamento().equals(TipoPagamento.A_VISTA)) {
            pagamento.setTipoPagamento(TipoPagamento.A_VISTA);
            pagamento.setValor(carroResponseDTO.preco());
            compra.setStatus(CompraStatus.CONCLUIDA);
        } else {
            if (request.valorEntrada() == null) {
                throw new CarroException("Valor de entrada é obrigatório para financiamento");
            }

            BigDecimal valorFinanciado = carroResponseDTO.preco().subtract(request.valorEntrada());

            Financiamento financiamento = new Financiamento();
            financiamento.setFinanciamentoStatus(FinanciamentoStatus.EM_ANALISE);
            financiamento.setValorEntreda(request.valorEntrada());
            financiamento.setValorFinanciado(valorFinanciado);

            pagamento.setFinanciamento(financiamento);
            pagamento.setTipoPagamento(TipoPagamento.FINANCIADO);
            pagamento.setValor(request.valorEntrada());

            compra.setStatus(CompraStatus.EM_ANDAMENTO);
        }
        pagamento = pagamentoRepository.save(pagamento);

        // Configura os dados da compra e troca o status do carro para VENDIDO
        Carro carro = carroMapper.toEntity(carroResponseDTO);
        carro.setCarroStatus(CarroStatus.VENDIDO);

        compra.setCarro(carro);
        compra.setPagamento(pagamento);
        compra.setIdUsuario(usuarioDTO.id());
        compra.setDataCompra(LocalDateTime.now());

        // Salva e retorna a compra
        Compra compraSalva = compraRepository.save(compra);
        CompraResponseDTO compraResponseDTO = compraMapper.toDTO(compraSalva);

        // Atualiza o status do carro para VENDIDO e salva novamente
        carroReposiotry.save(carro);

        return compraResponseDTO;
    }

    private CarroResponseDTO buscarCarroPeloId(Long id) {
        Optional<Carro> carro = carroReposiotry.findById(id);
        if (carro.isPresent()) {
            return carroMapper.toDto(carro.get());
        } else {
            throw new CarroException("Carro não encontrado");
        }
    }

    private UsuarioDTO getUsuarioLogado(String idUsuario) {
        UsuarioDTO usuarioDTO = authAPIClient.getUsuarioLogado();
        if (!usuarioDTO.id().equals(idUsuario)) {
            throw new CarroException("Usuário logado diferente do usuário informado");
        }
        return usuarioDTO;
    }
}
