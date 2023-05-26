package com.seleccion.seleccion.exception;

public class DeleteFileException extends RuntimeException {
    private String message;

    public DeleteFileException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
