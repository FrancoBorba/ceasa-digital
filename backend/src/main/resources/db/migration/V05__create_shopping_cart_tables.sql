
-- Tabela de Carrinhos: O "container" da compra de cada usuário
CREATE TABLE tb_carrinhos (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE,
    criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_carrinhos_usuario FOREIGN KEY (usuario_id) REFERENCES tb_user(id)
);

-- Tabela de Itens do Carrinho: Onde os produtos são "reservados"
CREATE TABLE tb_carrinho_itens (
    id BIGSERIAL PRIMARY KEY,
    carrinho_id BIGINT NOT NULL,
    oferta_produtor_id BIGINT NOT NULL,
    quantidade DECIMAL(10, 3) NOT NULL,
    preco_unitario_armazenado DECIMAL(10, 2) NOT NULL,
    criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_carrinho_itens_carrinho FOREIGN KEY (carrinho_id) REFERENCES tb_carrinhos(id),
    CONSTRAINT fk_carrinho_itens_oferta FOREIGN KEY (oferta_produtor_id) REFERENCES tb_ofertas_produtor(id)
);