-- Migration: Limpa o banco e o popula com um conjunto rico de dados para teste.

-- 1. LIMPA TUDO PRIMEIRO para garantir um ambiente limpo.
TRUNCATE
    tb_pedido_itens, tb_carrinho_itens, tb_carrinhos,
    tb_ofertas_produtor, tb_metas_estoque, tb_produtor_produto,
    tb_perfis_entregador, tb_perfis_produtor, tb_produtos, tb_pedidos, tb_user, tb_role
RESTART IDENTITY CASCADE;


-- 2. Inserir PRODUTOS
-- 2. Inserir PRODUTOS (com os caminhos de URL corretos)
INSERT INTO tb_produtos (id, nome, preco, unidade_de_medida, descricao, foto_url) VALUES
(1, 'Batata Inglesa', 4.50, 'KG', 'Batata de primeira qualidade.', '/produtos/batata.jpg'),
(2, 'Cebola Pêra', 5.20, 'KG', 'Cebola de sabor intenso.', '/produtos/cebola.jpg'),
(3, 'Cenoura', 3.80, 'KG', 'Cenoura fresca e crocante.', '/produtos/cenoura.jpg'),
(4, 'Coentro', 2.00, 'UN', 'Maço de coentro fresco.', '/produtos/coentro.jpg'),
(5, 'Couve-folha', 3.50, 'UN', 'Maço de couve-folha.', '/produtos/couve.jpg'),
(6, 'Laranja Pêra', 4.10, 'KG', 'Laranja doce e suculenta.', '/produtos/laranja.jpg'),
(7, 'Maracujá Azedo', 8.50, 'KG', 'Ideal para sucos e sobremesas.', '/produtos/maracuja.jpg'),
(8, 'Maçã Fuji', 9.80, 'KG', 'Maçã Fuji crocante e adocicada.', '/produtos/maca.jpg'),
(9, 'Tomate Italiano', 7.99, 'KG', 'Tomate maduro para molhos.', '/produtos/tomate.jpg')
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