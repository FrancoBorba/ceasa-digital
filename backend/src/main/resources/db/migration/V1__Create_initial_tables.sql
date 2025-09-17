-- Migration inicial para criar as tabelas básicas do sistema
-- Flyway Migration V1

-- Tabela de usuários (baseada na entidade User que vi no projeto)
CREATE TABLE IF NOT EXISTS tb_users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de roles (se existir no projeto)
--CREATE TABLE IF NOT EXISTS roles (
    --id BIGSERIAL PRIMARY KEY,
    --name VARCHAR(100) UNIQUE NOT NULL,
    --description TEXT,
    --created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
--);

-- Tabela de relacionamento usuário-role (many-to-many)
--CREATE TABLE IF NOT EXISTS user_roles (
    --user_id BIGINT NOT NULL,
    --role_id BIGINT NOT NULL,
    --PRIMARY KEY (user_id, role_id),
    --FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    --FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
--);

-- Inserir roles básicas
--INSERT INTO roles (name, description) VALUES 
--('ADMIN', 'Administrador do sistema'),
--('USER', 'Usuário comum do sistema')
--ON CONFLICT (name) DO NOTHING;
