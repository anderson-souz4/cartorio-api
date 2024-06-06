package com.asouza.cartorioapi.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Entity
@Data
public class Cartorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1)
    private Long id;

    @Column(length = 150, nullable = false)
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
    private String nome;

    @Column(length = 250)
    @Size(max = 250, message = "Observação deve ter no máximo 250 caracteres")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "situacao_id", nullable = false)
    private Situacao situacao;

    @ManyToMany
    @JoinTable(
            name = "cartorio_atribuicoes",
            joinColumns = @JoinColumn(name = "cartorio_id"),
            inverseJoinColumns = @JoinColumn(name = "atribuicao_id"))
    @NotEmpty(message = "Deve haver pelo menos uma atribuição")
    private List<Atribuicao> atribuicoes;
}
