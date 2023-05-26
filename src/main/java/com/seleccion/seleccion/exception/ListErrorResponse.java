package com.seleccion.seleccion.exception;

import java.util.List;

public class ListErrorResponse {
    private int errorCode;
    List<String> errorMessage;

    public ListErrorResponse(int errorCode, List<String> errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(List<String> errorMessage) {
        this.errorMessage = errorMessage;
    }
}
