
CREATE TABLE tb_auditoria_logs (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT, -- Pode ser nulo para ações feitas pelo sistema
    acao VARCHAR(100) NOT NULL,
    detalhes JSONB,
    timestamp TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_audit_logs_usuario FOREIGN KEY (usuario_id) REFERENCES tb_user(id)
);