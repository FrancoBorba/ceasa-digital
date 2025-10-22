-- Torna a coluna oferta_produtor_id nullable em tb_carrinho_itens
-- Isso permite que o carrinho funcione sem ofertas reais (apenas mock)
ALTER TABLE tb_carrinho_itens ALTER COLUMN oferta_produtor_id DROP NOT NULL;

