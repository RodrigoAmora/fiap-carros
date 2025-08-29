package br.com.fiap.fiapcarros.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "carros")
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String cor;

    @Column(nullable = false)
    private String ano;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private CarroStatus carroStatus;

}
