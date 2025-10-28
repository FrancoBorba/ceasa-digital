-- Migration V20: Add columns for password reset functionality
ALTER TABLE tb_user ADD COLUMN reset_token VARCHAR(255);
ALTER TABLE tb_user ADD COLUMN reset_token_expires TIMESTAMP WITHOUT TIME ZONE;

-- Optional: Add an index for faster token lookup
CREATE INDEX idx_user_reset_token ON tb_user (reset_token);