-- Migration V32: Cria tabela para armazenar tokens de redefinição de senha

CREATE TABLE tb_password_reset_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    expires_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_password_reset_token_user
        FOREIGN KEY (user_id) REFERENCES tb_user(id)
        ON DELETE CASCADE -- Se o usuário for deletado, o token também é.
);

-- Índice para buscar tokens rapidamente
CREATE INDEX idx_password_reset_token ON tb_password_reset_tokens(token);

-- Remove as colunas antigas da tabela tb_user (caso existam de tentativas anteriores)
-- O 'IF EXISTS' evita erro se as colunas não existirem mais.
ALTER TABLE tb_user DROP COLUMN IF EXISTS reset_token;
ALTER TABLE tb_user DROP COLUMN IF EXISTS reset_token_expires;