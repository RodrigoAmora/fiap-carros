package br.com.fiap.fiapcarros.repository;

import br.com.fiap.fiapcarros.model.Carro;
import br.com.fiap.fiapcarros.model.CarroStatus;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarroReposiotry extends JpaRepository<Carro, Long> {

    @Query("SELECT c FROM Carro c WHERE c.carroStatus = :status ORDER BY c.preco ASC")
    Page<Carro> findAllByStatusOrderByPrecoAsc(@Param("status") CarroStatus status, Pageable pageable);

}
