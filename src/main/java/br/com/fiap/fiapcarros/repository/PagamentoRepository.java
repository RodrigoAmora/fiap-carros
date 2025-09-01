package br.com.fiap.fiapcarros.repository;

import br.com.fiap.fiapcarros.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
