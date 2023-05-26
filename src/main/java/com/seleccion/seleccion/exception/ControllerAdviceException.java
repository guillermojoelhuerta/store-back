package com.seleccion.seleccion.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdviceException extends ResponseEntityExceptionHandler {

    public final static Logger log = LoggerFactory.getLogger(ControllerAdviceException.class);

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = FileCopyException.class)
    public ResponseEntity<ErrorResponse> fileCopyException(FileCopyException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = DeleteProductException.class)
    public ResponseEntity<ErrorResponse> deleteProductException(DeleteProductException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = GenericException.class)
    public ResponseEntity<ErrorResponse> genericException(GenericException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = BindingResultException.class)
    public ResponseEntity<ErrorResponse> bindingResultException(BindingResultException ex) {
        //List<String> messages = ex.getBindingResult().getFieldErrors().stream().map(e ->e.getDefaultMessage()).collect(Collectors.toList());
        String result = "";
        if (ex.getBindingResult().hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ObjectError error : ex.getBindingResult().getAllErrors()) {
                errorMessages.append(error.getDefaultMessage()).append(".");
            }
            result = errorMessages.toString();
        }
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), result);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
