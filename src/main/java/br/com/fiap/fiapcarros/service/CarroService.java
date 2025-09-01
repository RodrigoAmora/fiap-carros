package br.com.fiap.fiapcarros.service;

import br.com.fiap.fiapcarros.dto.request.CarroRequestDTO;
import br.com.fiap.fiapcarros.dto.response.CarroResponseDTO;
import br.com.fiap.fiapcarros.exception.CarroException;
import br.com.fiap.fiapcarros.manager.CarroMapper;
import br.com.fiap.fiapcarros.model.Carro;
import br.com.fiap.fiapcarros.model.CarroStatus;
import br.com.fiap.fiapcarros.repository.CarroReposiotry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarroService {

    private final CarroReposiotry carroReposiotry;
    private final CarroMapper carroMapper;

    public CarroService(CarroReposiotry carroReposiotry, CarroMapper carroMapper) {
        this.carroReposiotry = carroReposiotry;
        this.carroMapper = carroMapper;
    }

    public CarroResponseDTO salvarCarro(CarroRequestDTO request) {
        Carro carro = carroMapper.toEntity(request);
        carro.setCarroStatus(CarroStatus.A_VENDA);
        return carroMapper.toDto(carroReposiotry.save(carro));
    }

    public CarroResponseDTO atualizarCarro(CarroRequestDTO request, Long id) {
        Carro carro = buscarCarroPeloId(id);
        if (carro.getCarroStatus() != CarroStatus.A_VENDA) {
            throw new CarroException("Carro já vendido");
        }
        carro.setMarca(request.marca());
        carro.setModelo(request.modelo());
        carro.setCor(request.cor());
        carro.setAno(request.ano());
        carro.setPreco(request.preco());
        return carroMapper.toDto(carroReposiotry.save(carro));
    }

    public CarroResponseDTO buscarCarro(Long id) {
        Carro carro = buscarCarroPeloId(id);
        return carroMapper.toDto(carro);
    }

    public Page<CarroResponseDTO> buscarCarrosAVenda(int page, int size) {
        return buscarCarrosPeloStatus(page, size, CarroStatus.A_VENDA);
    }

    public Page<CarroResponseDTO> buscarCarrosVendidos(int page, int size) {
        return buscarCarrosPeloStatus(page, size, CarroStatus.VENDIDO);
    }

    private Page<CarroResponseDTO> buscarCarrosPeloStatus(int page, int size, CarroStatus status) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Carro> carrosPage = carroReposiotry.findAllByStatusOrderByPrecoAsc(status, pageable);
        return carrosPage.map(carroMapper::toDto);
    }

    private Carro buscarCarroPeloId(Long id) {
        Optional<Carro> carro = carroReposiotry.findById(id);
        if (carro.isPresent()) {
            return carro.get();
        } else {
            throw new CarroException("Carro não encontrado");
        }
    }
}
