-- Corrige o nome da coluna tipo_endereco para nome_endereco na tabela tb_enderecos
ALTER TABLE tb_enderecos
RENAME COLUMN tipo_endereco TO nome_endereco;
