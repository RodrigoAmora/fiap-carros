package br.com.fiap.fiapcarros.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private String idUsuario;

    @ManyToOne
    @JoinColumn(name = "carro_id")
    private Carro carro;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CompraStatus status;

    @OneToOne
    @JoinColumn(name = "pagamento_id")
    private Pagamento pagamento;

    @Column(name = "data_compra")
    private LocalDateTime dataCompra;

    @Column(name = "data_cancelamento")
    private LocalDateTime dataCancelamento;

}
