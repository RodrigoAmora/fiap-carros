package br.com.fiap.fiapcarros.manager;

import br.com.fiap.fiapcarros.dto.response.CarroResponseDTO;
import br.com.fiap.fiapcarros.model.Carro;
import org.springframework.stereotype.Component;

@Component
public class CarroMapper {

    public CarroResponseDTO toDto(Carro carro) {
        return new CarroResponseDTO(carro.getId(), carro.getNome(), carro.getModelo(), carro.getCor(), carro.getAno(),
                            carro.getPreco(), carro.getCarroStatus());
    }
}
