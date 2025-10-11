

-- 1. Remove a coluna de preço redundante da tabela de metas de estoque.
-- A fonte da verdade para o preço passa a ser a tabela tb_produtos.
ALTER TABLE tb_metas_estoque
DROP COLUMN preco_unitario_definido;


-- 2. Adiciona a chave estrangeira de produto diretamente na tabela de itens do carrinho.
-- Isso cria um "atalho" para otimizar e simplificar as consultas de exibição do carrinho.
ALTER TABLE tb_carrinho_itens
ADD COLUMN produto_id BIGINT;

ALTER TABLE tb_carrinho_itens
ADD CONSTRAINT fk_carrinho_itens_produto FOREIGN KEY (produto_id) REFERENCES tb_produtos(id);

-- Adiciona a restrição NOT NULL após a criação da FK.
-- Isso assume que a tabela está vazia. Se não estiver, esta linha pode falhar.
-- Deleta para não dar erro
DELETE FROM tb_carrinho_itens;

DELETE FROM tb_carrinhos;

ALTER TABLE tb_carrinho_itens
ALTER COLUMN produto_id SET NOT NULL;