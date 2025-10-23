#!/bin/bash

# ================================================================
# Script backend-dev.sh - Banco de dados PostgreSQL + Backend
# Uso: Para desenvolvimento do backend com hot reload
# Inicia: Docker (PostgreSQL) + Spring Boot automaticamente
# ================================================================

echo "🗄️  Iniciando ambiente de desenvolvimento..."
echo "📋 Script: backend-dev.sh"
echo "   1. Banco de dados PostgreSQL (Docker)"
echo "   2. Aplicação Spring Boot (Maven)"
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
    echo ""
    
    # Aguardar mais um pouco para garantir que o banco está realmente pronto
    echo "⏳ Aguardando banco de dados ficar completamente pronto..."
    sleep 3
    
    # Iniciar o backend
    echo "🚀 Iniciando aplicação Spring Boot..."
    echo "📦 Modo: desenvolvimento com hot reload"
    echo ""
    
    cd backend
    ./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
else
    echo "❌ Erro ao iniciar banco de dados"
    docker-compose logs database-ceasa-digital
    exit 1
fi
