-- Migration: Atualiza as senhas dos usuários de teste para hashes BCrypt
-- As senhas em texto plano são substituídas por hashes BCrypt correspondentes
-- 
-- Senhas originais -> Hashes BCrypt:
-- 'senha_admin' -> $2a$10$KPO5Cog600I0Uk2qVvQnHu8dnv0zI25ht/DzNfA9yn3b0SEadP9Sa
-- 'senha_produtor' -> $2a$10$MAUL5sdzzdyMSmRpeyC7i.Tr4NtJ0eQF6afRGk4zyXiG0OQLUq1M6
-- 'senha_entregador' -> $2a$10$F7S0kkldD1dp9OSqt6FPJuewidHy9Cz1rQ8LsMt1rh3hpdq2CyLgW
-- 'senha_cliente' -> $2a$10$nL7hFIFMtTVGBFCQE3.6g.5yvMDGA1W2KE0kqjlJVY93bYWeA.sGW

-- Admin Ceasa (id=1)
UPDATE tb_user 
SET password = '$2a$10$KPO5Cog600I0Uk2qVvQnHu8dnv0zI25ht/DzNfA9yn3b0SEadP9Sa'
WHERE id = 1 AND email = 'admin@ceasa.com';

-- Produtor João (id=10)
UPDATE tb_user 
SET password = '$2a$10$MAUL5sdzzdyMSmRpeyC7i.Tr4NtJ0eQF6afRGk4zyXiG0OQLUq1M6'
WHERE id = 10 AND email = 'joao@produtor.com';

-- Produtora Maria (id=11)
UPDATE tb_user 
SET password = '$2a$10$MAUL5sdzzdyMSmRpeyC7i.Tr4NtJ0eQF6afRGk4zyXiG0OQLUq1M6'
WHERE id = 11 AND email = 'maria@produtor.com';

-- Entregador Pedro (id=20)
UPDATE tb_user 
SET password = '$2a$10$F7S0kkldD1dp9OSqt6FPJuewidHy9Cz1rQ8LsMt1rh3hpdq2CyLgW'
WHERE id = 20 AND email = 'pedro@entregador.com';

-- Entregador Ana (id=21)
UPDATE tb_user 
SET password = '$2a$10$F7S0kkldD1dp9OSqt6FPJuewidHy9Cz1rQ8LsMt1rh3hpdq2CyLgW'
WHERE id = 21 AND email = 'ana@entregador.com';

-- Cliente Franco (id=30)
UPDATE tb_user 
SET password = '$2a$10$nL7hFIFMtTVGBFCQE3.6g.5yvMDGA1W2KE0kqjlJVY93bYWeA.sGW'
WHERE id = 30 AND email = 'franco@cliente.com';

