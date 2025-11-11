-- Migration: Insere categorias e relacionamentos entre produtos e categorias para desenvolvimento

-- Inserir categorias
INSERT INTO tb_categorias (id, name) VALUES
(1, 'Hortaliças'),
(2, 'Frutas'),
(3, 'Verduras'),
(4, 'Temperos')
ON CONFLICT (id) DO NOTHING;

-- Relacionar produtos com categorias
-- Hortaliças: Batata, Cebola, Cenoura, Tomate
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES
(1, 1), -- Batata Inglesa -> Hortaliças
(2, 1), -- Cebola Pêra -> Hortaliças
(3, 1), -- Cenoura -> Hortaliças
(9, 1)  -- Tomate Italiano -> Hortaliças
ON CONFLICT DO NOTHING;

-- Frutas: Laranja, Maracujá, Maçã
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES
(6, 2), -- Laranja Pêra -> Frutas
(7, 2), -- Maracujá Azedo -> Frutas
(8, 2)  -- Maçã Fuji -> Frutas
ON CONFLICT DO NOTHING;

-- Verduras: Couve-folha
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES
(5, 3)  -- Couve-folha -> Verduras
ON CONFLICT DO NOTHING;

-- Temperos: Coentro
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES
(4, 4)  -- Coentro -> Temperos
ON CONFLICT DO NOTHING;

