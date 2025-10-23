-- Remove a constraint UNIQUE de usuario_id que impede múltiplos carrinhos
ALTER TABLE tb_carrinhos DROP CONSTRAINT IF EXISTS tb_carrinhos_usuario_id_key;

-- Cria um índice UNIQUE parcial que garante apenas UM carrinho ATIVO por usuário
-- Isso permite múltiplos carrinhos FINALIZADOS para o mesmo usuário
CREATE UNIQUE INDEX idx_carrinhos_usuario_ativo 
ON tb_carrinhos (usuario_id) 
WHERE status = 'ATIVO';

