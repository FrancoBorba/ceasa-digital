-- Adicionar coluna produto_id na tabela tb_pedido_itens para armazenar referência direta ao produto
ALTER TABLE tb_pedido_itens 
ADD COLUMN produto_id BIGINT;

-- Adicionar constraint de foreign key
ALTER TABLE tb_pedido_itens 
ADD CONSTRAINT fk_pedido_itens_produto 
FOREIGN KEY (produto_id) 
REFERENCES tb_produtos(id);

-- Adicionar comentário explicativo
COMMENT ON COLUMN tb_pedido_itens.produto_id IS 'ID do produto para facilitar consultas e exibição de informações do produto no pedido';

