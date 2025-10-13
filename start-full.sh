#!/bin/bash

# ================================================================
# Script start-full.sh - Aplicação completa
# Uso: Para produção ou teste completo da aplicação
# ================================================================

echo "🚀 Iniciando aplicação completa CEASA Digital..."
echo "📋 Script: start-full.sh - Banco + Backend + Frontend (aplicação completa)"
echo ""

# Verificar se o arquivo .env existe
if [ ! -f ".env" ]; then
    echo "❌ Erro: Arquivo .env não encontrado!"
    echo "💡 Copie o arquivo .env.example para .env e configure as variáveis"
    exit 1
fi

# Parar containers existentes
echo "🛑 Parando containers existentes..."
docker-compose down

# Fazer build e iniciar todos os serviços
echo "🔨 Fazendo build das imagens..."
docker-compose build

echo "🚀 Iniciando todos os serviços..."
docker-compose up -d

# Aguardar serviços estarem prontos
echo "⏳ Aguardando serviços ficarem disponíveis..."
sleep 30

# Verificar status de todos os serviços
DB_STATUS=$(docker-compose ps database-ceasa-digital | grep -q "Up" && echo "✅" || echo "❌")
BACKEND_STATUS=$(docker-compose ps backend | grep -q "Up" && echo "✅" || echo "❌")
FRONTEND_STATUS=$(docker-compose ps frontend | grep -q "Up" && echo "✅" || echo "❌")

echo ""
echo "📊 Status dos Serviços:"
echo "   🗄️  Banco de dados: $DB_STATUS"
echo "   🔧 Backend API: $BACKEND_STATUS"
echo "   🌐 Frontend: $FRONTEND_STATUS"
echo ""

if [[ "$DB_STATUS" == "✅" && "$BACKEND_STATUS" == "✅" && "$FRONTEND_STATUS" == "✅" ]]; then
    BACKEND_PORT=$(grep SERVER_EXTERNAL_PORT .env | cut -d'=' -f2)
    DB_PORT=$(grep DATASOURCE_EXTERNAL_PORT .env | cut -d'=' -f2)
    
    echo "🎉 Aplicação completa iniciada com sucesso!"
    echo ""
    echo "🔗 URLs de Acesso:"
    echo "   🌐 Frontend: http://localhost:3000"
    echo "   🔧 Backend API: http://localhost:$BACKEND_PORT"
    echo "   🗄️  PostgreSQL: localhost:$DB_PORT"
    echo ""
    echo "🎯 Comandos úteis:"
    echo "   - Ver logs todos: docker-compose logs -f"
    echo "   - Ver logs frontend: docker-compose logs -f frontend"
    echo "   - Ver logs backend: docker-compose logs -f backend"
    echo "   - Ver logs banco: docker-compose logs -f database-ceasa-digital"
    echo "   - Parar: docker-compose down"
    echo "   - Status: docker-compose ps"
    echo ""
    echo "✨ Sua aplicação está rodando!"
else
    echo "❌ Erro ao iniciar alguns serviços"
    echo "📋 Verificando logs..."
    docker-compose logs
    exit 1
fi
