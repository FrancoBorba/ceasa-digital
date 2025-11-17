#!/bin/bash

# ================================================================
# Script frontend-dev.sh - Banco de dados + Backend
# Uso: Para desenvolvimento do frontend com hot reload
# ================================================================

echo "🗄️🔧 Iniciando banco de dados + backend..."
echo "📋 Script: frontend-dev.sh - Para desenvolvimento do frontend local com hot reload"
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

# Remover imagens antigas dos serviços
echo "🧹 Removendo imagens antigas..."

docker rmi ceasa-backend-container 2>/dev/null || true

# Iniciar banco de dados e backend
echo "🚀 Iniciando banco de dados..."
docker-compose up -d database-ceasa-digital

echo "⏳ Aguardando banco de dados ficar disponível..."
sleep 10

echo "🔨 Rebuilding imagem do backend (sem cache)..."
docker-compose build --no-cache backend

echo "🚀 Iniciando backend..."
docker-compose up -d backend

# Aguardar serviços estarem prontos
echo "⏳ Aguardando serviços ficarem disponíveis..."
sleep 15

# Verificar status
DB_STATUS=$(docker-compose ps database-ceasa-digital | grep -q "Up" && echo "✅" || echo "❌")
BACKEND_STATUS=$(docker-compose ps backend | grep -q "Up" && echo "✅" || echo "❌")

echo ""
echo "📊 Status dos Serviços:"
echo "   Banco de dados: $DB_STATUS"
echo "   Backend API: $BACKEND_STATUS"
echo ""

if [[ "$DB_STATUS" == "✅" && "$BACKEND_STATUS" == "✅" ]]; then
    BACKEND_PORT=$(grep SERVER_EXTERNAL_PORT .env | cut -d'=' -f2)
    DB_PORT=$(grep DATASOURCE_EXTERNAL_PORT .env | cut -d'=' -f2)
    
    echo "🎉 Serviços iniciados com sucesso!"
    echo "🔗 Backend API: http://localhost:$BACKEND_PORT"
    echo "🗄️  PostgreSQL: localhost:$DB_PORT"
    echo "🔧 Para desenvolvimento: Execute sua aplicação frontend localmente"
    echo ""
    echo "🎯 Comandos úteis:"
    echo "   - Ver logs backend: docker-compose logs -f backend"
    echo "   - Ver logs banco: docker-compose logs -f database-ceasa-digital"
    echo "   - Ver logs todos: docker-compose logs -f"
    echo "   - Parar: docker-compose down"
    echo "   - Status: docker-compose ps"
    echo ""
    echo "🚀 Iniciando frontend..."
    echo "🧹 Limpando node_modules antigo..."
    rm -rf frontend/src/node_modules
    echo "📦 Instalando dependências do frontend..."
    cd frontend/src && npm install && npm run dev
else
    echo "❌ Erro ao iniciar serviços"
    echo "📋 Verificando logs..."
    docker-compose logs
    exit 1
fi
