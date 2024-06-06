package com.asouza.cartorioapi.services.exceptions;

public class ViolacaoDaIntegridadeDosDados extends RuntimeException {
    public ViolacaoDaIntegridadeDosDados(String message) {
        super(message);
    }
}