

CREATE TABLE tb_notas_fiscais (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL UNIQUE, -- Garante uma única nota fiscal por pedido
    chave_acesso VARCHAR(44) UNIQUE,
    numero VARCHAR(20),
    serie VARCHAR(5),
    status VARCHAR(50) NOT NULL, -- Ex: 'AUTORIZADA', 'CANCELADA', 'EM_PROCESSAMENTO'
    url_xml VARCHAR(255),
    url_pdf VARCHAR(255),
    data_emissao TIMESTAMP WITHOUT TIME ZONE,
    criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_notas_fiscais_pedido FOREIGN KEY (pedido_id) REFERENCES tb_pedidos(id)
);