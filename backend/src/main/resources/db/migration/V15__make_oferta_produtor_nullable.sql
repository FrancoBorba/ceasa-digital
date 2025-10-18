
-- Remove a restrição NOT NULL da coluna oferta_produtor_id na tabela tb_carrinho_itens
-- Isso permite que o sistema funcione sem ofertas reais durante o desenvolvimento
ALTER TABLE tb_carrinho_itens
ALTER COLUMN oferta_produtor_id DROP NOT NULL;

-- Remove a constraint de foreign key existente
ALTER TABLE tb_carrinho_itens
DROP CONSTRAINT IF EXISTS fk_carrinho_itens_oferta;

-- Recria a constraint de foreign key permitindo NULL
ALTER TABLE tb_carrinho_itens
ADD CONSTRAINT fk_carrinho_itens_oferta 
FOREIGN KEY (oferta_produtor_id) 
REFERENCES tb_ofertas_produtor(id);
