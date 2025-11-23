-- Adiciona a coluna nome_endereco na tabela tb_enderecos, se ainda não existir
ALTER TABLE tb_enderecos
ADD COLUMN IF NOT EXISTS nome_endereco VARCHAR(50);
