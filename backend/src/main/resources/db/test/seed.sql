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

INSERT INTO tb_pedidos (usuario_id, valor_total, status, data_pedido) VALUES (1, 100.00, 'AGUARDANDO_PAGAMENTO', '2025-01-01 10:00:00');
INSERT INTO tb_pedidos (usuario_id, valor_total, status, data_pedido) VALUES (2, 200.00, 'AGUARDANDO_PAGAMENTO', '2025-01-01 10:00:00');
INSERT INTO tb_pedidos (usuario_id, valor_total, status, data_pedido) VALUES (2, 300.00, 'PAGO', '2025-01-01 10:00:00');
INSERT INTO tb_pedidos (usuario_id, valor_total, status, data_pedido) VALUES (2, 500.00, 'EM_TRANSPORTE', '2025-01-01 21:00:00');
INSERT INTO tb_pedidos (usuario_id, valor_total, status, data_pedido) VALUES (2, 300.00, 'ENTREGUE', '2025-01-01 12:00:00');
INSERT INTO tb_pedidos (usuario_id, valor_total, status, data_pedido) VALUES (2, 300.00, 'CANCELADO', '2025-01-01 12:00:00');