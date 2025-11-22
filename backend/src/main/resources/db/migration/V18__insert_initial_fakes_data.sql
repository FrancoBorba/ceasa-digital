-- Migration: Limpa o banco e o popula com um conjunto rico de dados para teste.

-- 1. LIMPA TUDO PRIMEIRO para garantir um ambiente limpo.
TRUNCATE
    tb_pedido_itens, tb_carrinho_itens, tb_carrinhos,
    tb_ofertas_produtor, tb_metas_estoque, tb_produtor_produto,
    tb_perfis_entregador, tb_perfis_produtor, tb_produtos, tb_pedidos, tb_user, tb_role
RESTART IDENTITY CASCADE;


-- 2. Inserir PRODUTOS
-- 2. Inserir PRODUTOS (com os caminhos de URL corretos)


INSERT INTO tb_produtos (nome, unidade_de_medida, preco, foto_url, descricao) VALUES
('Abacaxi', 'Un.', 6.50, 'Abacaxi.jpg', NULL),
('Abacate', 'Kg', 120.00, 'Abacate.jpg', NULL),
('Abóbora Preta', 'Kg', 65.00, 'Abóbora Preta.jpg', NULL),
('Abobrinha', 'Kg', 55.00, 'Abobrinha.jpg', NULL),
('Alho 4°', 'Kg', 135.00, 'Alho 4°.jpg', NULL),
('Alho Chinês', 'Kg', 200.00, 'Alho Chinês.jpg', NULL),
('Ameixa', 'Kg', 16.00, 'Ameixa.jpg', NULL),
('Banana Preta', 'Kg', 90.00, 'Banana Preta.jpg', NULL),
('Banana Caturra', 'Kg', 60.00, 'Banana Caturra.jpg', NULL),
('Banana da Terra', 'Un.', 120.00, 'Banana da Terra.jpg', NULL),
('Batata Doce B.', 'Kg', 55.00, 'Batata Doce B.jpg', NULL),
('Batata Doce Ver.', 'Kg', 55.00, 'Batata Doce Ver.jpg', NULL),
('Batata Inglesa E', 'Kg', 65.00, 'Batata Inglesa E.jpg', NULL),
('Batata Diversa (Fraca)', 'Kg', 55.00, 'Batata Diversa (Fraca).jpg', NULL),
('Berinjela', 'Kg', 40.00, 'Berinjela.jpg', NULL),
('Beterraba 1°', 'Kg', 70.00, 'Beterraba 1°.jpg', NULL),
('Beterraba 2°', 'Kg', 60.00, 'Beterraba 2°.jpg', NULL),
('Cebola B. Cx 2 (M)', 'Kg', 40.00, 'Cebola B. Cx 2 (M).jpg', NULL),
('Cebola B. Cx 3 (G)', 'Kg', 45.00, 'Cebola B. Cx 3 (G).jpg', NULL),
('Cebola Roxa', 'Kg', 60.00, 'Cebola Roxa.jpg', NULL),
('Cenoura 1°', 'Kg', 72.00, 'Cenoura 1°.jpg', NULL),
('Cenoura 2°', 'Kg', 60.00, 'Cenoura 2°.jpg', NULL),
('Chuchu (Preto)', 'Kg', 80.00, 'Chuchu (Preto).jpg', NULL),
('Coco Seco', 'Un.', 3.00, 'Coco Seco.jpg', NULL),
('Couve-Flor (Saco)', 'Un.', 65.00, 'Couve-Flor (Saco).jpg', NULL),
('Gengibre', 'Kg', 100.00, 'Gengibre.jpg', NULL),
('Goiaba', 'Kg', 100.00, 'Goiaba.jpg', NULL),
('Jiló', 'Kg', 40.00, 'Jiló.jpg', NULL),
('Inhame Paulista', 'Kg', 120.00, 'Inhame Paulista.jpg', NULL),
('Inhame Cará', 'Kg', 110.00, 'Inhame Cará.jpg', NULL),
('Kiwi', 'Kg', 240.00, 'Kiwi.jpg', NULL),
('Laranja', 'Un.', 50.00, 'Laranja.jpg', NULL),
('Limão Taity', 'Cx.', 90.00, 'Limão Taity.jpg', NULL),
('Limão Merim', 'Kg', 110.00, 'Limão Merim.jpg', NULL),
('Maçã 198 (Pequena)', 'Kg', 165.00, 'Maçã 198 (Pequena).jpg', NULL),
('Maçã Verde', 'Kg', 220.00, 'Maçã Verde.jpg', NULL),
('Maçã 135 (grande)', 'Kg', 175.00, 'Maçã 135 (grande).jpg', NULL),
('Maçã Argentil', 'Kg', 220.00, 'Maçã Argentil.jpg', NULL),
('Mamão Havaí', 'Kg', 40.00, 'Mamão Havaí.jpg', NULL),
('Mamão Formosa', 'Kg', 50.00, 'Mamão Formosa.jpg', NULL),
('Mandioca', 'Kg', 45.00, 'Mandioca.jpg', NULL),
('Manga Palmer (G)', 'Kg', 85.00, 'Manga Palmer (G).jpg', NULL),
('Manga Espada', 'Kg', 70.00, 'Manga Espada.jpg', NULL),
('Manga Rosa', 'Kg', 100.00, 'Manga Rosa.jpg', NULL),
('Manga Tommy', 'Kg', 90.00, 'Manga Tommy.jpg', NULL),
('Maracujá Cx.', 'Kg', 100.00, 'Maracujá Cx.jpg', NULL),
('Maxixe', 'Kg', 50.00, 'Maxixe.jpg', NULL),
('Melancia', 'Kg', 1.40, 'Melancia.jpg', NULL),
('Melão Caixa', 'Un.', 110.00, 'Melão Caixa.jpg', NULL),
('Melão Rei', 'Un.', 120.00, 'Melão Rei.jpg', NULL),
('Melão Saco (Unidade)', 'Un.', 70.00, 'Melão Saco (Unidade).jpg', NULL),
('Milho Verde', '100 Un.', 100.00, 'Milho Verde.jpg', NULL),
('Morango Cx. c/ 40bandejas', 'Un.', 20.00, 'Morango Cx. c 40bandejas.jpg', NULL),
('Ovo branco', 'Un.', 190.00, 'Ovo branco.jpg', NULL),
('Pepino', 'Kg', 40.00, 'Pepino.jpg', NULL),
('Pêra 90', 'Kg', 170.00, 'Pêra 90.jpg', NULL),
('Pimentão Maduro', 'Kg', 50.00, 'Pimentão Maduro.jpg', NULL),
('Ponkan', 'Kg', 75.00, 'Ponkan.jpg', NULL),
('Quiabo', 'Kg', 150.00, 'Quiabo.jpg', NULL),
('Repolho Verde', 'Kg', 50.00, 'Repolho Verde.jpg', NULL),
('Repolho Roxo', 'Kg', 80.00, 'Repolho Roxo.jpg', NULL),
('Tomate 1°', 'Kg', 60.00, 'Tomate 1°.jpg', NULL),
('Tomate 2°', 'Kg', 45.00, 'Tomate 2°.jpg', NULL),
('Uva Roxa', 'Kg', 200.00, 'Uva Roxa.jpg', NULL),
('Uva S Semente', 'Kg', 180.00, 'Uva S Semente.jpg', NULL),
('Uva Verde', 'Kg', 210.00, 'Uva Verde.jpg', NULL)
ON CONFLICT (id) DO NOTHING;


-- 3. Inserir USUÁRIOS
INSERT INTO tb_user (id, name, email, password, cpf, telefone, ativo) VALUES
(1, 'Admin Ceasa', 'admin@ceasa.com', 'senha_admin', '111.111.111-11', '(77)91111-1111', true),
(10, 'Produtor João', 'joao@produtor.com', 'senha_produtor', '222.222.222-22', '(77)92222-2222', true),
(11, 'Produtora Maria', 'maria@produtor.com', 'senha_produtor', '333.333.333-33', '(77)93333-3333', true),
(20, 'Entregador Pedro', 'pedro@entregador.com', 'senha_entregador', '444.444.444-44', '(77)94444-4444', true),
(21, 'Entregador Ana', 'ana@entregador.com', 'senha_entregador', '555.555.555-55', '(77)95555-5555', true),
(30, 'Cliente Franco', 'franco@cliente.com', 'senha_cliente', '666.666.666-66', '(77)96666-6666', true)
ON CONFLICT (id) DO NOTHING;


-- 4. Inserir PERFIS (Produtores e Entregadores)
INSERT INTO tb_perfis_produtor (id, usuario_id, numero_de_identificacao, tipo_de_identificacao) VALUES
(101, 10, '12.345.678/0001-99', 'CNPJ'),
(102, 11, '98.765.432/0001-11', 'CNPJ')
ON CONFLICT (id) DO NOTHING;

INSERT INTO tb_perfis_entregador (id, usuario_id, cnh, tipo_veiculo) VALUES
(201, 20, '123456789', 'Moto'),
(202, 21, '987654321', 'Carro')
ON CONFLICT (id) DO NOTHING;


-- 5. Ligar PRODUTORES aos PRODUTOS que eles podem vender
INSERT INTO tb_produtor_produto (produtor_id, produto_id, admin_autorizador_id, status) VALUES
(101, 1, 1, 'ATIVO'), -- Produtor João pode vender Batata
(101, 9, 1, 'ATIVO'), -- Produtor João pode vender Tomate
(102, 1, 1, 'ATIVO'), -- Produtora Maria também pode vender Batata
(102, 2, 1, 'ATIVO'), -- Produtora Maria pode vender Cebola
(102, 3, 1, 'ATIVO')  -- Produtora Maria pode vender Cenoura
ON CONFLICT DO NOTHING;


-- 6. Inserir METAS DE ESTOQUE (definidas pelo admin, sem o preço)
INSERT INTO tb_metas_estoque (id, produto_id, admin_criador_id, quantidade_meta, status) VALUES
(1, 1, 1, 500.000, 'ABERTA'), -- Meta para Batata
(2, 2, 1, 300.000, 'ABERTA'), -- Meta para Cebola
(3, 9, 1, 400.000, 'ABERTA')  -- Meta para Tomate
ON CONFLICT (id) DO NOTHING;


-- 7. Inserir OFERTAS DOS PRODUTORES (vinculadas às metas)
INSERT INTO tb_ofertas_produtor (id, meta_estoque_id, produtor_id, quantidade_ofertada, quantidade_disponivel, status) VALUES
-- Produtor João (101) ofertando Batata (meta 1) e Tomate (meta 3)
(1001, 1, 101, 150.000, 150.000, 'ATIVA'),
(1002, 3, 101, 100.000, 100.000, 'ATIVA'),
-- Produtora Maria (102) ofertando Batata (meta 1) e Cebola (meta 2)
(1003, 1, 102, 200.000, 200.000, 'ATIVA'),
(1004, 2, 102, 100.000, 100.000, 'ATIVA')
ON CONFLICT (id) DO NOTHING;