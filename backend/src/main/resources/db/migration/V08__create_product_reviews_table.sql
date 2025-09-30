
-- 1. Criar a tabela de avaliações de produtos (tb_avaliacoes_produtos).

CREATE TABLE tb_avaliacoes_produtos (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    pedido_item_id BIGINT NOT NULL UNIQUE, -- Garante que cada item de pedido só pode ser avaliado uma vez
    nota INT NOT NULL CHECK (nota >= 1 AND nota <= 5), -- Garante que a nota seja sempre entre 1 e 5
    comentario TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDENTE', -- Para moderação: 'PENDENTE', 'APROVADO', 'REJEITADO'
    criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_avaliacoes_usuario FOREIGN KEY (usuario_id) REFERENCES tb_user(id),
    CONSTRAINT fk_avaliacoes_produto FOREIGN KEY (produto_id) REFERENCES tb_produtos(id),
    CONSTRAINT fk_avaliacoes_pedido_item FOREIGN KEY (pedido_item_id) REFERENCES tb_pedido_itens(id)
);