#!/bin/bash

# ================================================================
# Script backend-dev.sh - Banco de dados PostgreSQL
# Uso: Para desenvolvimento do backend com hot reload
# ================================================================

echo "🗄️  Iniciando banco de dados PostgreSQL..."
echo "📋 Script: backend-dev.sh - Para desenvolvimento do backend local com hot reload"
echo ""

# Verificar se o arquivo .env existe
if [ ! -f ".env" ]; then
    echo "❌ Erro: Arquivo .env não encontrado!"
    echo "💡 Copie o arquivo .env.example para .env e configure as variáveis"
    exit 1
fi

# Parar containers existentes e limpar volumes
echo "🛑 Parando containers existentes e removendo volumes..."
docker-compose down -v

# Remover container do banco de dados
echo "🧹 Limpando container do banco de dados..."
docker-compose rm -f database-ceasa-digital

# Iniciar apenas o banco de dados
echo "🚀 Iniciando banco de dados..."
docker-compose up -d database-ceasa-digital

# Aguardar banco estar pronto
echo "⏳ Aguardando banco de dados ficar disponível..."
sleep 5

# Verificar status
if docker-compose ps database-ceasa-digital | grep -q "Up"; then
    echo ""
    echo "✅ Banco de dados iniciado com sucesso!"
    echo "📊 PostgreSQL disponível em: localhost:$(grep DATASOURCE_EXTERNAL_PORT .env | cut -d'=' -f2)"
    echo "🔧 Para desenvolvimento: Execute sua aplicação Spring Boot localmente"
    echo ""
    echo "🎯 Comandos úteis:"
    echo "   - Ver logs: docker-compose logs -f database-ceasa-digital"
    echo "   - Parar: docker-compose down"
    echo "   - Status: docker-compose ps"
else
    echo "❌ Erro ao iniciar banco de dados"
    docker-compose logs database-ceasa-digital
    exit 1
fi
