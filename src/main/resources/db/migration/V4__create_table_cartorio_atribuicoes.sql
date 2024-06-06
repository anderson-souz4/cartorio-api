CREATE TABLE cartorio_atribuicoes
(
    cartorio_id   INT         NOT NULL,
    atribuicao_id VARCHAR(20) NOT NULL,
    PRIMARY KEY (cartorio_id, atribuicao_id),
    CONSTRAINT fk_cartorio_atribuicoes_cartorio FOREIGN KEY (cartorio_id) REFERENCES cartorio (id),
    CONSTRAINT fk_cartorio_atribuicoes_atribuicao FOREIGN KEY (atribuicao_id) REFERENCES atribuicao (id)
);
