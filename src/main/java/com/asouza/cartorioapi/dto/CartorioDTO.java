package com.asouza.cartorioapi.dto;

import com.asouza.cartorioapi.models.Atribuicao;
import com.asouza.cartorioapi.models.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartorioDTO {

    private Long id;

    private String nome;

    private String observacao;

    private Situacao situacao;

    private List<Atribuicao> atribuicoes;
}
