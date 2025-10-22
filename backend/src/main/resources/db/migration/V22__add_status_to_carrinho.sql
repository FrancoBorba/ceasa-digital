-- Adiciona coluna status ao carrinho
ALTER TABLE tb_carrinhos ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'ATIVO';

-- Atualiza carrinhos existentes para ATIVO
UPDATE tb_carrinhos SET status = 'ATIVO' WHERE status IS NULL;

