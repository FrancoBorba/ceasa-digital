-- Flyway Migration V2

-- Adiciona a coluna CPF
ALTER TABLE tb_user ADD COLUMN cpf VARCHAR(14)  UNIQUE;

-- Insere um CPF para não dar erro ao ser NOT NULL
UPDATE tb_user SET cpf = '000.000.000-01' WHERE email = 'alex@gmail.com';
UPDATE tb_user SET cpf = '000.000.000-02' WHERE email = 'maria@gmail.com';

ALTER TABLE tb_user ALTER COLUMN cpf SET NOT NULL;
--  Adiciona as demais colunas

ALTER TABLE tb_user ADD COLUMN telefone VARCHAR(20);
ALTER TABLE tb_user ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT TRUE;
ALTER TABLE tb_user ADD COLUMN criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE tb_user ADD COLUMN atualizado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE tb_role ADD COLUMN criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE tb_role ADD COLUMN atualizado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP;

-- 

-- Tabela de Perfis para Produtores
CREATE TABLE tb_perfis_produtor (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE,
    numero_de_identificacao VARCHAR(50),
    tipo_de_identificacao VARCHAR(50),
    criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_perfis_produtor_usuario FOREIGN KEY (usuario_id) REFERENCES tb_user(id)
);

-- Tabela de Perfis para Entregadores
CREATE TABLE tb_perfis_entregador (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE,
    cnh VARCHAR(20) NOT NULL,
    tipo_veiculo VARCHAR(30),
    criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_perfis_entregador_usuario FOREIGN KEY (usuario_id) REFERENCES tb_user(id)
);

-- Tabela de Endereços
CREATE TABLE tb_enderecos (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    cep VARCHAR(9) NOT NULL,
    logradouro VARCHAR(255) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    complemento VARCHAR(100),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado CHAR(2) NOT NULL,
    principal BOOLEAN NOT NULL DEFAULT FALSE,
    criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_enderecos_usuario FOREIGN KEY (usuario_id) REFERENCES tb_user(id)
);