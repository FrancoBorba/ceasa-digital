-- Torna a coluna oferta_produtor_id nullable em tb_pedido_itens
-- Isso permite que pedidos sejam criados sem ofertas reais (apenas com produtos)
ALTER TABLE tb_pedido_itens ALTER COLUMN oferta_produtor_id DROP NOT NULL;

