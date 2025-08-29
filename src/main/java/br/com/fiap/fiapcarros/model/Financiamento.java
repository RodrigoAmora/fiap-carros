package br.com.fiap.fiapcarros.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Entity
@Table(name = "financiamentos")
public class Financiamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FinanciamentoStatus financiamentoStatus;

    @OneToOne(mappedBy = "financiamento")
    private Pagamento pagamento;

    @Column(name = "valor_entrada")
    private BigDecimal valorEntreda;

    @Column(name = "valor_financiado")
    private BigDecimal valorFinanciado;

}
