-- Tabela de Pedidos: O "cabeçalho" da nota fiscal
CREATE TABLE tb_pedidos (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    entregador_id BIGINT, -- Pode ser nulo inicialmente e atribuído depois
    endereco_id BIGINT NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL, -- Ex: 'AGUARDANDO_PAGAMENTO', 'PAGO', 'EM_TRANSPORTE', etc.
    data_pedido TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_pedidos_usuario FOREIGN KEY (usuario_id) REFERENCES tb_user(id),
    CONSTRAINT fk_pedidos_entregador FOREIGN KEY (entregador_id) REFERENCES tb_perfis_entregador(id),
    CONSTRAINT fk_pedidos_endereco FOREIGN KEY (endereco_id) REFERENCES tb_enderecos(id)
);

-- Tabela de Itens do Pedido: A lista de produtos da nota fiscal
CREATE TABLE tb_pedido_itens (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    oferta_produtor_id BIGINT NOT NULL,
    quantidade DECIMAL(10, 3) NOT NULL,
    preco_unitario_compra DECIMAL(10, 2) NOT NULL,
    criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_pedido_itens_pedido FOREIGN KEY (pedido_id) REFERENCES tb_pedidos(id),
    CONSTRAINT fk_pedido_itens_oferta FOREIGN KEY (oferta_produtor_id) REFERENCES tb_ofertas_produtor(id)
);