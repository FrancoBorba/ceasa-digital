-- Migration V22: Adiciona coluna para controle de confirmação de e-mail na tabela de usuários

-- Adiciona a coluna email_confirmado
ALTER TABLE tb_user ADD COLUMN email_confirmado BOOLEAN NOT NULL DEFAULT FALSE;

-- Considera os usuários existentes como já confirmados:
UPDATE tb_user SET email_confirmado = TRUE;
