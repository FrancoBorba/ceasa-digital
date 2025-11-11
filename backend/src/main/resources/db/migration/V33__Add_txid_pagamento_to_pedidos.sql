-- Adiciona a coluna para armazenar o ID da transação (txid) do gateway de pagamento
ALTER TABLE tb_pedidos
ADD COLUMN txid_pagamento VARCHAR(255);