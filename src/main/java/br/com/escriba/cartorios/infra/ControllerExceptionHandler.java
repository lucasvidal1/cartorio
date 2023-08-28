package br.com.escriba.cartorios.infra;

import br.com.escriba.cartorios.domain.exception.ResourceDuplicateException;
import br.com.escriba.cartorios.domain.exception.ResourceIntegrityViolationException;
import br.com.escriba.cartorios.domain.exception.ResourceNotFoundException;
import br.com.escriba.cartorios.dtos.ExceptionDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Registro utilizado em outro cadastro.", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceIntegrityViolationException.class)
    public ResponseEntity<?> handleResourceIntegrityViolationException(ResourceIntegrityViolationException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceDuplicateException.class)
    public ResponseEntity<?> handleResourceDuplicateException(ResourceDuplicateException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
}
