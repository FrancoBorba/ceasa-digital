
CREATE TABLE tb_repasses_produtores (
    id BIGSERIAL PRIMARY KEY,
    produtor_id BIGINT NOT NULL,
    valor_repassado DECIMAL(10, 2) NOT NULL,
    periodo_referencia_inicio DATE NOT NULL,
    periodo_referencia_fim DATE NOT NULL,
    data_pagamento TIMESTAMP WITHOUT TIME ZONE,
    status VARCHAR(50) NOT NULL, -- Ex: 'PENDENTE', 'PAGO', 'ERRO'
    transacao_pagamento_id VARCHAR(255), -- ID do pagamento no sistema financeiro que você usar
    criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_repasses_produtor FOREIGN KEY (produtor_id) REFERENCES tb_perfis_produtor(id)
);