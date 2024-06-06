package com.asouza.cartorioapi.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Situacao {

    @Id
    @Column(length = 20, nullable = false)
    @NotBlank(message = "ID é obrigatório")
    @Size(max = 20, message = "ID deve ter no máximo 20 caracteres")
    private String id;

    @Column(length = 50, nullable = false)
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 50, message = "Nome deve ter no máximo 50 caracteres")
    private String nome;
}
