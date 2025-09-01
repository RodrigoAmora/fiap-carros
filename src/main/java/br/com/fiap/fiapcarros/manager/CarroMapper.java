package br.com.fiap.fiapcarros.manager;

import br.com.fiap.fiapcarros.dto.request.CarroRequestDTO;
import br.com.fiap.fiapcarros.dto.response.CarroResponseDTO;
import br.com.fiap.fiapcarros.model.Carro;
import org.springframework.stereotype.Component;

@Component
public class CarroMapper {

    public CarroResponseDTO toDto(Carro carro) {
        return new CarroResponseDTO(carro.getId(), carro.getMarca(), carro.getModelo(), carro.getCor(), carro.getAno(),
                            carro.getPreco(), carro.getCarroStatus());
    }

    public Carro toEntity(CarroRequestDTO requestDTO) {
        Carro carro = new Carro();
        carro.setMarca(requestDTO.marca());
        carro.setModelo(requestDTO.modelo());
        carro.setCor(requestDTO.cor());
        carro.setAno(requestDTO.ano());
        carro.setPreco(requestDTO.preco());
        return carro;
    }

    public Carro toEntity(CarroResponseDTO requestDTO) {
        Carro carro = new Carro();
        carro.setId(requestDTO.id());
        carro.setMarca(requestDTO.marca());
        carro.setModelo(requestDTO.modelo());
        carro.setCor(requestDTO.cor());
        carro.setAno(requestDTO.ano());
        carro.setPreco(requestDTO.preco());
        carro.setCarroStatus(requestDTO.carroStatus());
        return carro;
    }
}
