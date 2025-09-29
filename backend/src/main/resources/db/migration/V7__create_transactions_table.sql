
-- Criar a tabela de transações (tb_transacoes) para registrar as interações com o gateway de pagamento.

CREATE TABLE tb_transacoes (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    gateway VARCHAR(50) NOT NULL,
    gateway_transacao_id VARCHAR(255) UNIQUE, -- ID único retornado pelo provedor (ex: Sicoob)
    valor DECIMAL(10, 2) NOT NULL,
    metodo_pagamento VARCHAR(50) NOT NULL, -- Ex: 'PIX', 'CARTAO_CREDITO'
    status VARCHAR(50) NOT NULL, -- Ex: 'PENDENTE', 'APROVADO', 'RECUSADO'
    dados_adicionais JSONB, -- Usando JSONB para flexibilidade e performance no PostgreSQL
    criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_transacoes_pedido FOREIGN KEY (pedido_id) REFERENCES tb_pedidos(id)
);