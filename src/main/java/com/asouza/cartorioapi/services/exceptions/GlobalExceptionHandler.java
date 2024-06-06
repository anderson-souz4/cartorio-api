package com.asouza.cartorioapi.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse createErrorResponse(Exception ex, HttpStatus status, String error, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now().toString());
        errorResponse.setStatus(status.value());
        errorResponse.setError(error);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(request.getRequestURI());
        return errorResponse;
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErrorResponse> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorResponse(ex, HttpStatus.NOT_FOUND, "Not Found", request));
    }

    @ExceptionHandler(ViolacaoDaIntegridadeDosDados.class)
    public ResponseEntity<ErrorResponse> handleViolacaoDaIntegridadeDosDados(ViolacaoDaIntegridadeDosDados ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(ex, HttpStatus.BAD_REQUEST, "Bad Request", request));
    }

    @ExceptionHandler(AtributoJaExistenteException.class)
    public ResponseEntity<ErrorResponse> handleAtibutoJaExistente(AtributoJaExistenteException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(ex, HttpStatus.BAD_REQUEST, "Bad Request", request));
    }
}