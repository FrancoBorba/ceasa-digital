-- 1. Adiciona a coluna 'foto_url' na tabela de produtos.
ALTER TABLE tb_produtos
ADD COLUMN foto_url VARCHAR(255);

-- 2. Adiciona a coluna 'quantidade_reservada' na tabela de ofertas para controle de estoque.
ALTER TABLE tb_ofertas_produtor
ADD COLUMN quantidade_reservada DECIMAL(10, 3) NOT NULL DEFAULT 0;