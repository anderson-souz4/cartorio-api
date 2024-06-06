package com.asouza.cartorioapi.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
public class Atribuicao {

    @Id
    @Column(length = 20, nullable = false)
    @NotBlank(message = "ID é obrigatório")
    @Size(max = 20, message = "ID deve ter no máximo 20 caracteres")
    private String id;

    @Column(length = 50, nullable = false)
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 50, message = "Nome deve ter no máximo 50 caracteres")
    private String nome;

    @Column(nullable = false)
    private boolean situacao = true;
}
