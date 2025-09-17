-- ===================================================================
-- SEED DE DADOS PARA TESTES
-- ===================================================================

-- Inserir roles básicas (igual ao V1)
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_USER');

-- Inserir usuários padrão (igual ao V1) - senhas: 123456
INSERT INTO tb_user (name, email, password) VALUES ('Alex', 'alex@gmail.com', '$2a$10$7S.2emMl7nOgjaZpSfdZte0iem3zOH7megfDrAImcgYYld48yHzjS');
INSERT INTO tb_user (name, email, password) VALUES ('Maria', 'maria@gmail.com', '$2a$10$7S.2emMl7nOgjaZpSfdZte0iem3zOH7megfDrAImcgYYld48yHzjS');

-- Associar usuários com roles (igual ao V1)
INSERT INTO tb_user_roles (user_id, role_id) VALUES (1, 1); -- Alex: ADMIN
INSERT INTO tb_user_roles (user_id, role_id) VALUES (1, 2); -- Alex: USER
INSERT INTO tb_user_roles (user_id, role_id) VALUES (2, 2); -- Maria: USER