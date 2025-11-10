-- ===================================================================
-- SEED DE DADOS PARA TESTES
-- ===================================================================

-- Inserir roles básicas (igual ao V1)
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_USER');

INSERT INTO tb_user (name, email, password, cpf, ativo, email_confirmado) VALUES ('Alex', 'alex@gmail.com', '$2a$10$7S.2emMl7nOgjaZpSfdZte0iem3zOH7megfDrAImcgYYld48yHzjS', '000.000.000-01', true, true);
INSERT INTO tb_user (name, email, password, cpf, ativo, email_confirmado) VALUES ('Maria', 'maria@gmail.com', '$2a$10$7S.2emMl7nOgjaZpSfdZte0iem3zOH7megfDrAImcgYYld48yHzjS', '000.000.000-02', true, true);

-- Associar usuários com roles (igual ao V1)
INSERT INTO tb_user_roles (user_id, role_id) VALUES (1, 1); -- Alex: ADMIN
INSERT INTO tb_user_roles (user_id, role_id) VALUES (1, 2); -- Alex: USER
INSERT INTO tb_user_roles (user_id, role_id) VALUES (2, 2); -- Maria: USER

-- Inserir produtos para testes
INSERT INTO tb_produtos (id, nome, preco, unidade_de_medida, descricao, foto_url) VALUES
(1, 'Batata Inglesa', 4.50, 'KG', 'Batata de primeira qualidade.', '/produtos/batata.jpg'),
(2, 'Cebola Pêra', 5.20, 'KG', 'Cebola de sabor intenso.', '/produtos/cebola.jpg'),
(3, 'Cenoura', 3.80, 'KG', 'Cenoura fresca e crocante.', '/produtos/cenoura.jpg'),
(4, 'Coentro', 2.00, 'UN', 'Maço de coentro fresco.', '/produtos/coentro.jpg'),
(5, 'Couve-folha', 3.50, 'UN', 'Maço de couve-folha.', '/produtos/couve.jpg'),
(6, 'Laranja Pêra', 4.10, 'KG', 'Laranja doce e suculenta.', '/produtos/laranja.jpg'),
(7, 'Maracujá Azedo', 8.50, 'KG', 'Ideal para sucos e sobremesas.', '/produtos/maracuja.jpg'),
(8, 'Maçã Fuji', 9.80, 'KG', 'Maçã Fuji crocante e adocicada.', '/produtos/maca.jpg'),
(9, 'Tomate Italiano', 7.99, 'KG', 'Tomate maduro para molhos.', '/produtos/tomate.jpg');

-- Inserir categorias
INSERT INTO tb_categorias (id, name) VALUES
(1, 'Hortaliças'),
(2, 'Frutas'),
(3, 'Verduras'),
(4, 'Temperos');

-- Relacionar produtos com categorias
-- Hortaliças: Batata, Cebola, Cenoura, Tomate
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES
(1, 1), -- Batata Inglesa -> Hortaliças
(2, 1), -- Cebola Pêra -> Hortaliças
(3, 1), -- Cenoura -> Hortaliças
(9, 1); -- Tomate Italiano -> Hortaliças

-- Frutas: Laranja, Maracujá, Maçã
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES
(6, 2), -- Laranja Pêra -> Frutas
(7, 2), -- Maracujá Azedo -> Frutas
(8, 2); -- Maçã Fuji -> Frutas

-- Verduras: Couve-folha
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES
(5, 3); -- Couve-folha -> Verduras

-- Temperos: Coentro
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES
(4, 4); -- Coentro -> Temperos