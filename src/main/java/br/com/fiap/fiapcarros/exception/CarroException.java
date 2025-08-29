package br.com.fiap.fiapcarros.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CarroException extends RuntimeException {
    public CarroException(String message) {
        super(message);
    }
}
