package com.seleccion.seleccion.exception;

public class DeleteProductException extends RuntimeException{
    private String message;

    public DeleteProductException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
