-- Migration V23: Cria a tabela para armazenar tokens de confirmação de e-mail
CREATE TABLE tb_confirmation_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    expires_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    confirmed_at TIMESTAMP WITHOUT TIME ZONE,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_confirmation_token_user FOREIGN KEY (user_id) REFERENCES tb_user(id) ON DELETE CASCADE
);

CREATE INDEX idx_confirmation_token ON tb_confirmation_tokens(token);