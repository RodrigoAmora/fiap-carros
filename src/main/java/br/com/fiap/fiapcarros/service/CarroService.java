package br.com.fiap.fiapcarros.service;

import br.com.fiap.fiapcarros.dto.response.CarroResponseDTO;
import br.com.fiap.fiapcarros.exception.CarroException;
import br.com.fiap.fiapcarros.manager.CarroMapper;
import br.com.fiap.fiapcarros.model.Carro;
import br.com.fiap.fiapcarros.repository.CarroReposiotry;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarroService {

    private final CarroReposiotry carroReposiotry;
    private final CarroMapper carroMapper;

    public CarroService(CarroReposiotry carroReposiotry,
                        CarroMapper carroMapper) {
        this.carroReposiotry = carroReposiotry;
        this.carroMapper = carroMapper;
    }

    public CarroResponseDTO buscarCarroPeloId(Long id) {
        Optional<Carro> carro = carroReposiotry.findById(id);
        if (carro.isPresent()) {
            return carroMapper.toDto(carro.get());
        } else {
            throw new CarroException("Carro não encontrado");
        }
    }
}
