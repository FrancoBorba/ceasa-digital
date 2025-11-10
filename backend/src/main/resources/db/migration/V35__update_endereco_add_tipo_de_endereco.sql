-- Adiciona a coluna tipo_endereco na tabela tb_enderecos para armazenar o tipo do endereço (casa, trabalho, outro)
ALTER TABLE tb_enderecos
ADD COLUMN tipo_endereco VARCHAR(50);

