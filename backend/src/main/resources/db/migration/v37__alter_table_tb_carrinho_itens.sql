-- Migration: Refatora a tabela tb_carrinho_itens para remover a dependência de oferta_produtor.
-- A lógica da fila de prioridade será movida para o momento do checkout (PedidoService),
-- tornando o carrinho independente de qual produtor específico irá vender o item.

-- 1. Remove a restrição de chave estrangeira que liga o item do carrinho à oferta
ALTER TABLE tb_carrinho_itens
DROP CONSTRAINT IF EXISTS fk_carrinho_itens_oferta;

-- 2. Remove a coluna que armazenava o ID da oferta
ALTER TABLE tb_carrinho_itens
DROP COLUMN IF EXISTS oferta_produtor_id;