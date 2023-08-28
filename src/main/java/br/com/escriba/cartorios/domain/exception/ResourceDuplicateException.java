package br.com.escriba.cartorios.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ResourceDuplicateException extends RuntimeException {

    public ResourceDuplicateException(String mensagem){
        super(mensagem);
    }
}
