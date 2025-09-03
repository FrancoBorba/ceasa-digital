CREATE TABLE listas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,          
    usuario_id BIGINT NOT NULL,             
    CONSTRAINT fk_lista_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE lista_produtos (
    lista_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    PRIMARY KEY (lista_id, produto_id),
    CONSTRAINT fk_lp_lista FOREIGN KEY (lista_id) REFERENCES listas(id) ON DELETE CASCADE,
    CONSTRAINT fk_lp_produto FOREIGN KEY (produto_id) REFERENCES produtos(id) ON DELETE CASCADE
);