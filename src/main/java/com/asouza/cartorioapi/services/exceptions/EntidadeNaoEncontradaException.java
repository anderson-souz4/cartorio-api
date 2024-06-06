package com.asouza.cartorioapi.services.exceptions;

public class EntidadeNaoEncontradaException extends RuntimeException {

    public EntidadeNaoEncontradaException(String message, String id) {
        super(message + ": " + id);
    }
}
