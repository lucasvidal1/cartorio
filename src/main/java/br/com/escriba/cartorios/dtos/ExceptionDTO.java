package br.com.escriba.cartorios.dtos;

import lombok.Data;

@Data
public class ExceptionDTO {
    private String message;
    private int statusCode;

    public ExceptionDTO(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
