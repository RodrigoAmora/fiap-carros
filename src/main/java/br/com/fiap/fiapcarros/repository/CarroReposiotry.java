package br.com.fiap.fiapcarros.repository;

import br.com.fiap.fiapcarros.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarroReposiotry extends JpaRepository<Carro, Long> {

}
