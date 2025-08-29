package br.com.fiap.fiapcarros.manager;

import br.com.fiap.fiapcarros.dto.response.CarroResponseDTO;
import br.com.fiap.fiapcarros.dto.PagamentoDTO;
import br.com.fiap.fiapcarros.dto.request.CompraRequestDTO;
import br.com.fiap.fiapcarros.dto.response.CompraResponseDTO;
import br.com.fiap.fiapcarros.model.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class CompraMapper {

    public Compra toEntity(CompraRequestDTO dto) {
        Carro carro = new Carro();

        Compra compra = new Compra();
        compra.setCarro(carro);
        compra.setNomeCliente(dto.nomeCliente());

        Pagamento pagamento = new Pagamento();
        pagamento.setValor(dto.valorEntrada());
        if (dto.tipoPagamento().equals(TipoPagamento.A_VISTA)) {
            pagamento.setTipoPagamento(TipoPagamento.A_VISTA);
            compra.setStatus(CompraStatus.CONCLUIDA);
        } else {
            pagamento.setTipoPagamento(TipoPagamento.FINANCIADO);
            compra.setStatus(CompraStatus.EM_ANDAMENTO);

            BigDecimal valorEntrada = dto.valorEntrada();
            BigDecimal valorFinanciado = carro.getPreco().subtract(valorEntrada);

            Financiamento financiamento = new Financiamento();
            financiamento.setFinanciamentoStatus(FinanciamentoStatus.EM_ANALISE);
            financiamento.setValorEntreda(valorEntrada);
            financiamento.setValorFinanciado(valorFinanciado);

            pagamento.setFinanciamento(financiamento);
        }

        compra.setPagamento(pagamento);
        compra.setDataCompra(LocalDateTime.now());

        return compra;
    }

    public CompraResponseDTO toDTO(Compra compra) {
        CarroResponseDTO carroDTO = new CarroResponseDTO(
                compra.getCarro().getId(),
                compra.getCarro().getNome(),
                compra.getCarro().getModelo(),
                compra.getCarro().getCor(),
                compra.getCarro().getAno(),
                compra.getCarro().getPreco()
        );

        PagamentoDTO pagamentoDTO = new PagamentoDTO(
                compra.getPagamento().getId(),
                compra.getPagamento().getTipoPagamento(),
                compra.getPagamento().getValor(),
                compra.getPagamento().getFinanciamento().getValorFinanciado()
        );
        return new CompraResponseDTO(
                compra.getId(),
                compra.getNomeCliente(),
                carroDTO,
                compra.getStatus(),
                pagamentoDTO,
                compra.getDataCompra()
        );
    }
}
