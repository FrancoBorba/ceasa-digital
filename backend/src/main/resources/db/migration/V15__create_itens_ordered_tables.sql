-- V16_create_tb_itens_pedido.sql
-- Migration para criar a tabela tb_itens_pedido

CREATE TABLE tb_itens_pedido (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    oferta_id BIGINT NOT NULL,
    quantidade DECIMAL(10, 0),
    preco_unitario DECIMAL(10, 2),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_itempedido_pedido
        FOREIGN KEY (pedido_id) REFERENCES tb_pedidos(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_itempedido_oferta
        FOREIGN KEY (oferta_id) REFERENCES tb_ofertas_produtor(id)
        ON DELETE CASCADE
);

-- Índices opcionais para otimização de consultas
-- filtrando por pedido ou oferta, evitando varreduras completas da tabela
-- e tornando as consultas mais rápidas mesmo com muitos registros.
-- Sem indice a pesquisa é feita em full scan
-- Com indice a pesquisa é feita em index scan, ou seja, vai olhar no indice em qual linha está o dado e vai direto nela, sem precisar olhar todas as linhas da tabela.
CREATE INDEX idx_itens_pedido_pedido_id ON tb_itens_pedido(pedido_id);
CREATE INDEX idx_itens_pedido_oferta_id ON tb_itens_pedido(oferta_id);
