package com.seleccion.seleccion.exception;

public class FileCopyException extends RuntimeException{
    private String message;

    public FileCopyException(String message) {
        this.message = message;
    }

    public FileCopyException(String message, Throwable cause) {
        super(cause);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
