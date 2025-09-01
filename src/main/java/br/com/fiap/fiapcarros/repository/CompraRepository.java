package br.com.fiap.fiapcarros.repository;

import br.com.fiap.fiapcarros.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}
