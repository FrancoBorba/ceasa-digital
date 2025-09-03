CREATE TABLE produtores (
    usuario_id BIGINT PRIMARY KEY,
    CONSTRAINT fk_produtor_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);