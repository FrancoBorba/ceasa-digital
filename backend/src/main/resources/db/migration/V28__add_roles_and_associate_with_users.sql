-- Cria os registros de perfis que o sistema utilizará (USER, ADMIN, PRODUTOR, ENTREGADOR)
INSERT INTO tb_role (id, authority) VALUES
(1, 'ROLE_USER'),    
(2, 'ROLE_ADMIN'),   
(3, 'ROLE_PRODUTOR'), 
(4, 'ROLE_ENTREGADOR')
ON CONFLICT (id) DO NOTHING;

INSERT INTO tb_user_roles (user_id, role_id) VALUES
-- Admin Ceasa (user_id = 1) recebe as roles: USER (role_id = 1) e ADMIN (role_id = 2)
(1, 1),
(1, 2),

-- Produtor João (user_id = 10) recebe as roles: USER (role_id = 1) e PRODUTOR (role_id = 3)
(10, 1),
(10, 3),

-- Produtora Maria (user_id = 11) recebe as roles: USER (role_id = 1) e PRODUTOR (role_id = 3)
(11, 1),
(11, 3),

-- Entregador Pedro (user_id = 20) recebe as roles: USER (role_id = 1) e ENTREGADOR (role_id = 4)
(20, 1),
(20, 4),

-- Entregador Ana (user_id = 21) recebe as roles: USER (role_id = 1) e ENTREGADOR (role_id = 4)
(21, 1),
(21, 4),

-- Cliente Franco (user_id = 30) recebe apenas a role: USER (role_id = 1)
(30, 1)
ON CONFLICT DO NOTHING;