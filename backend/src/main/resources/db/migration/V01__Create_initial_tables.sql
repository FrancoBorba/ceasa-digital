-- Migration inicial para criar as tabelas básicas do sistema
-- Flyway Migration V1

-- Tabela de roles
CREATE TABLE IF NOT EXISTS tb_role (
    id BIGSERIAL PRIMARY KEY,
    authority VARCHAR(100) UNIQUE NOT NULL
);

-- Tabela de usuários
CREATE TABLE IF NOT EXISTS tb_user (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Tabela de relacionamento usuário-role (many-to-many)
CREATE TABLE IF NOT EXISTS tb_user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES tb_user(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES tb_role(id) ON DELETE CASCADE
);

-- Inserir roles básicas
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_USER');

-- Inserir usuários padrão (senhas: 123456)
INSERT INTO tb_user (name, email, password) VALUES ('Alex', 'alex@gmail.com', '$2a$10$7S.2emMl7nOgjaZpSfdZte0iem3zOH7megfDrAImcgYYld48yHzjS');
INSERT INTO tb_user (name, email, password) VALUES ('Maria', 'maria@gmail.com', '$2a$10$7S.2emMl7nOgjaZpSfdZte0iem3zOH7megfDrAImcgYYld48yHzjS');

-- Associar usuários com roles
INSERT INTO tb_user_roles (user_id, role_id) VALUES (1, 1); -- Alex: ADMIN
INSERT INTO tb_user_roles (user_id, role_id) VALUES (1, 2); -- Alex: USER
INSERT INTO tb_user_roles (user_id, role_id) VALUES (2, 2); -- Maria: USER
