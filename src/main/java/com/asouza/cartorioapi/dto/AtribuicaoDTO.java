package com.asouza.cartorioapi.dto;

import lombok.Data;



@Data
public class AtribuicaoDTO {
    private String id;
    private String nome;

    private boolean situacao;
    public AtribuicaoDTO() {
    }

    public AtribuicaoDTO(String id, String nome, boolean situacao) {
        this.id = id;
        this.nome = nome;
        this.situacao = situacao;
    }

    public AtribuicaoDTO(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
