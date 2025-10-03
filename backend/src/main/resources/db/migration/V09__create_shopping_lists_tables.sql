

-- Tabela Principal: O "container" da lista
CREATE TABLE tb_listas_de_compras (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_listas_de_compras_usuario FOREIGN KEY (usuario_id) REFERENCES tb_user(id)
);

-- Tabela de Itens: Os produtos dentro de cada lista
CREATE TABLE tb_lista_de_compras_itens (
    id BIGSERIAL PRIMARY KEY,
    lista_de_compras_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade_desejada DECIMAL(10, 3) NOT NULL DEFAULT 1,
    observacao VARCHAR(255),
    criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_lista_itens_lista FOREIGN KEY (lista_de_compras_id) REFERENCES tb_listas_de_compras(id),
    CONSTRAINT fk_lista_itens_produto FOREIGN KEY (produto_id) REFERENCES tb_produtos(id)
);