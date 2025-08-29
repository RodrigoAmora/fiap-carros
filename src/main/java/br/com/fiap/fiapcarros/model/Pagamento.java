package br.com.fiap.fiapcarros.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;


@Entity
@Data
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "pagamento")
    private Compra compra;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pagamento")
    private TipoPagamento tipoPagamento;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "financiamento_id")
    private Financiamento financiamento;

    @Column(name = "valor_pago")
    private BigDecimal valor;
}

