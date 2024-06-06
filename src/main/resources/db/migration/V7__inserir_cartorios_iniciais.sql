-- Primeiro, inserimos os cartórios
INSERT INTO cartorio (id, nome, observacao, situacao_id) VALUES (1, 'Cartório Central', 'Cartório principal da cidade', 'SIT_ATIVO');
INSERT INTO cartorio (id, nome, observacao, situacao_id) VALUES (2, 'Cartório do Bairro', 'Cartório do bairro central', 'SIT_ATIVO');
INSERT INTO cartorio (id, nome, observacao, situacao_id) VALUES (3, 'Cartório da Zona Norte', NULL, 'SIT_BLOQUEADO');

-- Agora, associamos as atribuições aos cartórios
INSERT INTO cartorio_atribuicoes (cartorio_id, atribuicao_id) VALUES (1, 'ATRIB1');
INSERT INTO cartorio_atribuicoes (cartorio_id, atribuicao_id) VALUES (1, 'ATRIB2');
INSERT INTO cartorio_atribuicoes (cartorio_id, atribuicao_id) VALUES (2, 'ATRIB1');
INSERT INTO cartorio_atribuicoes (cartorio_id, atribuicao_id) VALUES (3, 'ATRIB3');
