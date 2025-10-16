-- Migration 19: Deleta a tabela duplicada

-- Remove tabela duplicada tb_itens_pedido se existir
DROP TABLE IF EXISTS tb_itens_pedido;