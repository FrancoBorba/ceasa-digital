-- Tabela de Permissões: Define qual produtor pode vender qual produto
CREATE TABLE tb_produtor_produto (
    id BIGSERIAL PRIMARY KEY,
    produtor_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    admin_autorizador_id BIGINT,
    status VARCHAR(50) NOT NULL DEFAULT 'ATIVO', 
    criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_produtor_produto_produtor FOREIGN KEY (produtor_id) REFERENCES tb_perfis_produtor(id),
    CONSTRAINT fk_produtor_produto_produto FOREIGN KEY (produto_id) REFERENCES tb_produtos(id),
    CONSTRAINT fk_produtor_produto_admin FOREIGN KEY (admin_autorizador_id) REFERENCES tb_user(id)
);

-- Tabela de Metas de Estoque: Onde o gerente define a demanda
CREATE TABLE tb_metas_estoque (
    id BIGSERIAL PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    admin_criador_id BIGINT NOT NULL,
    quantidade_meta DECIMAL(10, 3) NOT NULL,
    preco_unitario_definido DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ABERTA', 
    criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_metas_estoque_produto FOREIGN KEY (produto_id) REFERENCES tb_produtos(id),
    CONSTRAINT fk_metas_estoque_admin FOREIGN KEY (admin_criador_id) REFERENCES tb_user(id)
);

-- Tabela de Ofertas do Produtor: O estoque descentralizado e a fila inteligente
CREATE TABLE tb_ofertas_produtor (
    id BIGSERIAL PRIMARY KEY,
    meta_estoque_id BIGINT NOT NULL,
    produtor_id BIGINT NOT NULL,
    quantidade_ofertada DECIMAL(10, 3) NOT NULL,
    quantidade_disponivel DECIMAL(10, 3) NOT NULL,
    total_volume_vendido DECIMAL(10, 3) NOT NULL DEFAULT 0,
    data_ultima_venda TIMESTAMP WITHOUT TIME ZONE,
    status VARCHAR(50) NOT NULL DEFAULT 'ATIVA', 
    criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_ofertas_produtor_meta FOREIGN KEY (meta_estoque_id) REFERENCES tb_metas_estoque(id),
    CONSTRAINT fk_ofertas_produtor_produtor FOREIGN KEY (produtor_id) REFERENCES tb_perfis_produtor(id)
);