CREATE TABLE entregadores (
    usuario_id BIGINT PRIMARY KEY,
    cnt VARCHAR(255),                      -- Atributo 'cnt: string' [cite: 123]
    CONSTRAINT fk_entregador_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);