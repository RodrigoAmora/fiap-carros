package br.com.fiap.fiapcarros.manager;

import br.com.fiap.fiapcarros.dto.CarroDTO;
import br.com.fiap.fiapcarros.model.Carro;
import org.springframework.stereotype.Component;

@Component
public class CarroMapper {

    public CarroDTO toDto(Carro carro) {
        return new CarroDTO(carro.getId(), carro.getNome(), carro.getModelo(), carro.getCor(), carro.getAno(),
                            carro.getPreco(), carro.getCarroStatus());
    }
}
