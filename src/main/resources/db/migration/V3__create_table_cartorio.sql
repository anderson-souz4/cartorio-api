CREATE TABLE cartorio
(
    id          INT AUTO_INCREMENT NOT NULL,
    nome        VARCHAR(150) NOT NULL,
    observacao  VARCHAR(250),
    situacao_id VARCHAR(20)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_cartorio_situacao FOREIGN KEY (situacao_id) REFERENCES situacao (id)
);
