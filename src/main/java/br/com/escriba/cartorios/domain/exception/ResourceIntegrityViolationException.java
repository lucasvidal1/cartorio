package br.com.escriba.cartorios.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ResourceIntegrityViolationException extends RuntimeException {
    public ResourceIntegrityViolationException(String mensagem){
        super(mensagem);
    }
}
