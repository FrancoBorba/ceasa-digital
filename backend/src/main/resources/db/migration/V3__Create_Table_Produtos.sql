CREATE TABLE produtos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    imagem VARCHAR(255),
    preco DECIMAL(10, 2) NOT NULL,
    produtor_id BIGINT NOT NULL,
    CONSTRAINT fk_produto_produtor FOREIGN KEY (produtor_id) REFERENCES produtores(usuario_id) ON DELETE CASCADE
);