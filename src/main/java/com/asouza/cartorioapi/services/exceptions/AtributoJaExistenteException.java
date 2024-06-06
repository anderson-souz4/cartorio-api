package com.asouza.cartorioapi.services.exceptions;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AtributoJaExistenteException extends RuntimeException {

    @NotBlank(message = "Mensagem é obrigatória")
    @Size(max = 100, message = "Mensagem deve ter no máximo 100 caracteres")
    private String message;

    public AtributoJaExistenteException(String message) {
        super(message);
        this.message = message;
    }
}
