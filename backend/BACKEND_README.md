# CEASA Digital API

API REST para o projeto CEASA Digital - Sistema de gerenciamento para a Central de Abastecimento de Sergipe.

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Pré-requisitos](#pré-requisitos)
- [Configuração do Ambiente](#configuração-do-ambiente)
- [Execução Local](#execução-local)
- [Execução com Docker](#execução-com-docker)
- [Documentação da API](#documentação-da-api)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Solução de Problemas](#solução-de-problemas)

## 🎯 Sobre o Projeto

O CEASA Digital é uma API REST desenvolvida para modernizar e digitalizar os processos da Central de Abastecimento de Sergipe, facilitando o gerenciamento de usuários, produtos, transações e relatórios.

## 🚀 Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 3.3.4** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **PostgreSQL** - Banco de dados principal
- **H2** - Banco de dados para testes
- **Flyway** - Controle de versão do banco de dados
- **Maven** - Gerenciamento de dependências
- **Docker** - Containerização
- **SpringDoc OpenAPI** - Documentação da API

## 📋 Pré-requisitos

### Para Execução Local
- **Java 21** ou superior
- **Maven 3.9+**
- **PostgreSQL 15+**

### Para Execução com Docker
- **Docker** 
- **Docker Compose**

## ⚙️ Configuração do Ambiente

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd ceasa-digital/backend
```

### 2. Configure as variáveis de ambiente

Copie o arquivo de exemplo e configure suas variáveis:

```bash
cp .env.example .env
```

Edite o arquivo `.env` com suas configurações:

```properties
# Configurações do Spring
SPRING_PROFILES_ACTIVE=dev

# Configurações do servidor
SERVER_PORT=8080

# Configurações do banco de dados DEV
DEV_DATASOURCE_DB=ceasa_digital
DEV_DATASOURCE_PORT=5432
DEV_DATASOURCE_USERNAME=postgres
DEV_DATASOURCE_PASSWORD=sua_senha_aqui
DEV_DATASOURCE_HOST=localhost
DEV_DATASOURCE_URL=jdbc:postgresql://${DEV_DATASOURCE_HOST}:${DEV_DATASOURCE_PORT}/${DEV_DATASOURCE_DB}

# Configurações para Docker Compose
DOCKER_SPRING_PROFILES_ACTIVE=dev
DOCKER_SERVER_EXTERNAL_PORT=8080
DOCKER_SERVER_INTERNAL_PORT=8080
DOCKER_POSTGRES_DB=ceasa_digital
DOCKER_POSTGRES_USER=postgres
DOCKER_POSTGRES_PASSWORD=sua_senha_aqui
DOCKER_POSTGRES_EXTERNAL_PORT=5433
DOCKER_POSTGRES_INTERNAL_PORT=5432
DOCKER_POSTGRES_HOST=database-ceasa-digital
DOCKER_POSTGRES_URL=jdbc:postgresql://${DOCKER_POSTGRES_HOST}:${DOCKER_POSTGRES_INTERNAL_PORT}/${DOCKER_POSTGRES_DB}
```

## 🖥️ Execução Local

### 1. Configurar o Banco de Dados PostgreSQL

Instale e configure o PostgreSQL, depois crie o banco de dados:

```sql
CREATE DATABASE ceasa_digital;
```

### 2. Executar a aplicação

```bash
# Compilar o projeto
mvn clean compile

# Executar a aplicação
mvn spring-boot:run
```

**Ou usando o wrapper do Maven:**

```bash
# No Linux/Mac
./mvnw spring-boot:run

# No Windows
mvnw.cmd spring-boot:run
```

### 3. Verificar se está funcionando

A aplicação estará disponível em: `http://localhost:8080`

## 🐳 Execução com Docker

### Método 1: Docker Compose (Recomendado)

Execute tudo com um comando:

```bash
# Construir e iniciar todos os serviços
docker-compose -f docker-compose.backend.yml up --build -d
```

### Verificar containers em execução

```bash
docker-compose -f docker-compose.backend.yml ps
```

### Parar os serviços

```bash
docker-compose -f docker-compose.backend.yml down
```

### Conexão de BD Docker com Pg admin:

Registre um Servidor e configure as seguintes informações:

- Host: `localhost`
- Porta: `sua porta externa`
- Usuário: `postgres`
- Senha: `sua_senha_aqui`

## 📚 Documentação da API

### Swagger UI

Após iniciar a aplicação, acesse a documentação interativa:

- **URL:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/v3/api-docs`

### Endpoints principais

- `GET /api/users` - Listar usuários
- `POST /api/users` - Criar usuário
- `GET /api/users/{id}` - Buscar usuário por ID
- `PUT /api/users/{id}` - Atualizar usuário
- `DELETE /api/users/{id}` - Deletar usuário

## 📁 Estrutura do Projeto

```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/uesb/ceasadigital/api/
│   │   │       ├── CeasaDigitalApiApplication.java
│   │   │       ├── common/              # Classes utilitárias
│   │   │       ├── config/              # Configurações
│   │   │       └── features/            # Funcionalidades por domínio
│   │   │           ├── user/            # Domínio de usuários
│   │   │           │   ├── controller/
│   │   │           │   ├── dto/
│   │   │           │   ├── model/
│   │   │           │   ├── repository/
│   │   │           │   └── service/
│   │   │           └── role/            # Domínio de papéis
│   │   └── resources/
│   │       ├── application.yml          # Configuração principal
│   │       ├── application-dev.yml      # Configuração desenvolvimento
│   │       ├── application-test.yml     # Configuração testes
│   │       └── db/migration/            # Scripts Flyway
│   └── test/                            # Testes
├── target/                              # Arquivos compilados
├── docker-compose.backend.yml           # Docker Compose
├── Dockerfile                           # Imagem Docker
├── pom.xml                             # Dependências Maven
├── .env                                # Variáveis de ambiente
├── .env.example                        # Exemplo de variáveis
└── README.md                           # Este arquivo
```

## 🛠️ Solução de Problemas

### Problema: Erro de conexão com banco de dados

**Sintomas:** Erro ao conectar com PostgreSQL

**Soluções:**
1. Verificar se o PostgreSQL está rodando
2. Confirmar as credenciais no arquivo `.env`
3. Verificar se o banco `ceasa_digital` foi criado
4. Testar conexão manual: `psql -h localhost -U postgres -d ceasa_digital`

### Problema: Porta 8080 já está em uso

**Sintomas:** `Port 8080 is already in use`

**Soluções:**
1. Alterar a porta no `.env`: `SERVER_PORT=8081`
2. Ou parar o processo que está usando a porta 8080

### Problema: Erro de memória Java

**Sintomas:** `OutOfMemoryError`

**Soluções:**
1. Aumentar memória JVM: `export JAVA_OPTS="-Xmx2g"`
2. Ou executar com: `mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx2g"`

### Problema: Erro Flyway ao migrar banco

**Sintomas:** Erro nas migrações do banco

**Soluções:**
1. Limpar e recriar o banco:
   ```sql
   DROP DATABASE ceasa_digital;
   CREATE DATABASE ceasa_digital;
   ```
2. Ou resetar as migrações Flyway

### Problema: Docker sem permissão

**Sintomas:** `Permission denied` ao executar Docker

**Soluções:**
1. Adicionar usuário ao grupo docker: `sudo usermod -aG docker $USER`
2. Reiniciar a sessão ou executar: `newgrp docker`

### Logs e Debugging

**Ver logs da aplicação:**
```bash
# Logs local
tail -f logs/app.log

# Logs Docker
docker-compose -f docker-compose.backend.yml logs -f backend-ceasa-digital

# Logs específicos do container
docker logs ceasa-digital-backend -f
```

**Verificar saúde dos serviços:**
```bash
# Health check da aplicação
curl http://localhost:8080/actuator/health

# Status dos containers
docker-compose -f docker-compose.backend.yml ps
```

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença [MIT](../LICENSE).

---

**Desenvolvido para a Universidade Estadual do Sudoeste da Bahia (UESB)**
