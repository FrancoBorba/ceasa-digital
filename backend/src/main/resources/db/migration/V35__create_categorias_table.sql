-- Migration: Cria a tabela de categorias e a tabela de relacionamento produto-categoria

-- Tabela de categorias
CREATE TABLE IF NOT EXISTS tb_categorias (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de relacionamento produto-categoria (many-to-many)
CREATE TABLE IF NOT EXISTS tb_produto_categoria (
    produto_id BIGINT NOT NULL,
    categoria_id BIGINT NOT NULL,
    PRIMARY KEY (produto_id, categoria_id),
    FOREIGN KEY (produto_id) REFERENCES tb_produtos(id) ON DELETE CASCADE,
    FOREIGN KEY (categoria_id) REFERENCES tb_categorias(id) ON DELETE CASCADE
);

