-- Tornar o campo endereco_id nullable para compatibilidade com pedidos existentes
-- e permitir que pedidos antigos continuem existindo sem endereço
ALTER TABLE tb_pedidos 
ALTER COLUMN endereco_id DROP NOT NULL;

-- Adicionar comentário explicativo
COMMENT ON COLUMN tb_pedidos.endereco_id IS 'ID do endereço de entrega. Pode ser nulo para pedidos antigos criados antes da implementação do fluxo de finalização de carrinho.';

