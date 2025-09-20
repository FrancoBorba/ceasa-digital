# CEASA Digital API

API REST para o projeto CEASA Digital - Sistema de gerenciamento para a Central de Abastecimento de Sergipe.

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura de Segurança](#arquitetura-de-segurança)
- [Pré-requisitos](#pré-requisitos)
- [Configuração do Ambiente](#configuração-do-ambiente)
- [Execução Local](#execução-local)
- [Execução com Docker](#execução-com-docker)
- [Documentação da API](#documentação-da-api)
- [Autenticação e Autorização](#autenticação-e-autorização)
- [Desenvolvimento](#desenvolvimento)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Solução de Problemas](#solução-de-problemas)

## 🎯 Sobre o Projeto

O CEASA Digital é uma API REST desenvolvida para modernizar e digitalizar os processos da Central de Abastecimento de Sergipe, facilitando o gerenciamento de usuários, produtos, transações e relatórios.

## 🚀 Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 3.3.4** - Framework principal
- **Spring Security 6** - Segurança e autenticação
- **Spring Authorization Server** - Servidor OAuth2
- **JWT (JSON Web Tokens)** - Tokens de acesso
- **Spring Data JPA** - Persistência de dados
- **PostgreSQL** - Banco de dados principal
- **H2** - Banco de dados para testes
- **Flyway** - Controle de versão do banco de dados
- **Maven** - Gerenciamento de dependências
- **Docker** - Containerização
- **SpringDoc OpenAPI** - Documentação da API

## 🔐 Arquitetura de Segurança

O projeto implementa uma arquitetura robusta de segurança baseada em OAuth2 e JWT, separando responsabilidades entre:

### Authorization Server
- **Endpoint:** `/oauth2/token`
- **Responsabilidade:** Emissão e validação de tokens JWT
- **Grant Type:** Password (customizado)
- **Configuração:** `AuthorizationServerSecurityConfig`

### Resource Server
- **Responsabilidade:** Proteção de recursos da API
- **Validação:** Tokens JWT em todas as requisições autenticadas
- **Configuração:** `ResourceServerSecurityConfig`

### Características:
- ✅ **JWT Self-contained** - Tokens contêm todas as informações necessárias
- ✅ **CORS configurado** - Suporte a requisições cross-origin
- ✅ **Refresh Tokens** - Renovação automática de tokens
- ✅ **Autorização baseada em roles** - ROLE_USER, ROLE_ADMIN
- ✅ **Endpoints públicos** - Acesso sem autenticação quando necessário

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

# Configurações de Segurança OAuth2
SECURITY_CLIENT_ID=ceasa-digital-client
SECURITY_CLIENT_SECRET=ceasa-digital-secret
SECURITY_JWT_DURATION=3600
SECURITY_JWT_REFRESH_DURATION=7200
SECURITY_OAUTH2_JWK_SET_URI=http://localhost:8080/.well-known/jwks.json
SECURITY_OAUTH2_ISSUER_URI=http://localhost:8080

# Configurações CORS
CORS_ORIGINS=http://localhost:3000,http://localhost:5173
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

- `POST /oauth2/token` - Obter token de acesso (Authentication)
- `GET /users/public` - Endpoint público (sem autenticação)
- `GET /users/user` - Endpoint para usuários autenticados
- `GET /users/admin` - Endpoint exclusivo para administradores

## 🔐 Autenticação e Autorização

### Como Obter um Token de Acesso

Para acessar endpoints protegidos, você precisa primeiro obter um token JWT:

```bash
curl -X POST http://localhost:8080/oauth2/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=password&username=seu_usuario&password=sua_senha&client_id=ceasa-digital-client&client_secret=ceasa-digital-secret"
```

**Resposta de sucesso:**
```json
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
  "token_type": "Bearer",
  "expires_in": 3600
}
```

### Como Usar o Token

Inclua o token no cabeçalho `Authorization` das suas requisições:

```bash
curl -X GET http://localhost:8080/users/user \
  -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### Renovar Token (Refresh Token)

Quando o token expira, use o refresh token para obter um novo:

```bash
curl -X POST http://localhost:8080/oauth2/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=refresh_token&refresh_token=SEU_REFRESH_TOKEN&client_id=ceasa-digital-client&client_secret=ceasa-digital-secret"
```

### Roles e Permissões

O sistema possui dois níveis de acesso:

- **ROLE_USER**: Acesso a recursos básicos da aplicação
- **ROLE_ADMIN**: Acesso total, incluindo funcionalidades administrativas

**Exemplos de uso:**
- `GET /users/user` - Requer `ROLE_USER` ou `ROLE_ADMIN`
- `GET /users/admin` - Requer apenas `ROLE_ADMIN`
- `GET /users/public` - Não requer autenticação

## 👨‍💻 Desenvolvimento

### Como Adicionar Novos Endpoints

#### 1. Endpoints Públicos (Sem Autenticação)

Para criar um endpoint que não requer autenticação:

**Passo 1:** Crie o método no controller normalmente:
```java
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    
    @GetMapping("/catalogo")
    public List<Produto> obterCatalogo() {
        // Lógica do endpoint público
        return produtoService.obterCatalogo();
    }
}
```

**Passo 2:** Adicione o endpoint à lista de URLs públicas em `ResourceServerSecurityConfig`:
```java
http.authorizeHttpRequests(authorize -> authorize
    // Adicione sua URL aqui:
    .requestMatchers("/api/produtos/catalogo").permitAll()
    .requestMatchers("/users/public").permitAll() // Existing
    // ... outros endpoints
    .anyRequest().authenticated());
```

#### 2. Endpoints Privados (Com Autenticação)

Para endpoints que requerem autenticação, você tem algumas opções:

##### 2.1. Endpoint que requer apenas autenticação (qualquer role):
```java
@GetMapping("/perfil")
public PerfilDto obterPerfil(Authentication authentication) {
    String username = authentication.getName();
    return usuarioService.obterPerfil(username);
}
```

##### 2.2. Endpoint que requer role específica:
```java
@GetMapping("/admin/relatorios")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public List<Relatorio> obterRelatorios() {
    return relatorioService.obterTodos();
}

@GetMapping("/usuario/historico")
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
public List<Historico> obterHistorico(Authentication auth) {
    return historicoService.obterPorUsuario(auth.getName());
}
```

##### 2.3. Endpoint com validação de propriedade de recurso:
```java
@GetMapping("/pedidos/{id}")
@PreAuthorize("hasRole('ROLE_ADMIN') or @pedidoService.isOwner(#id, authentication.name)")
public Pedido obterPedido(@PathVariable Long id, Authentication auth) {
    return pedidoService.obterPorId(id);
}
```

### Anotações de Segurança Disponíveis

- `@PreAuthorize("hasRole('ROLE_ADMIN')")` - Apenas administradores
- `@PreAuthorize("hasRole('ROLE_USER')")` - Apenas usuários comuns
- `@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")` - Usuários ou admins
- `@PreAuthorize("permitAll()")` - Todos (equivale a endpoint público)
- `@PreAuthorize("denyAll()")` - Ninguém

### Obtendo Informações do Usuário Autenticado

```java
@GetMapping("/meus-dados")
public DadosUsuarioDto obterMeusDados(Authentication authentication) {
    // Obter username
    String username = authentication.getName();
    
    // Obter roles
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    
    // Verificar se tem role específica
    boolean isAdmin = authorities.stream()
        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    
    return usuarioService.obterDados(username);
}
```

### Testando Endpoints com Postman

Importe a collection do Postman localizada em:
```
backend/postman/ceasa_digital.postman_collection.json
```

Esta collection contém:
- ✅ Requisição para obter token
- ✅ Exemplos de endpoints públicos
- ✅ Exemplos de endpoints privados
- ✅ Configuração automática de token nas requisições

## 📁 Estrutura do Projeto

```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/uesb/ceasadigital/api/
│   │   │       ├── CeasaDigitalApiApplication.java
│   │   │       ├── common/              # Classes utilitárias e handlers
│   │   │       │   ├── exceptions/      # Exceções customizadas
│   │   │       │   ├── handlers/        # Global exception handlers
│   │   │       │   └── response/        # Response models
│   │   │       ├── config/              # Configurações
│   │   │       │   └── security/        # Configurações de segurança
│   │   │       │       ├── authorizationserver/  # OAuth2 Authorization Server
│   │   │       │       │   ├── customgrant/      # Custom Password Grant
│   │   │       │       │   ├── controllers/      # Auth error handling
│   │   │       │       │   ├── filters/          # OAuth2 response filters
│   │   │       │       │   └── wrappers/         # Response wrappers
│   │   │       │       └── resourceserver/       # OAuth2 Resource Server
│   │   │       │           ├── controllers/      # Resource error handling
│   │   │       │           └── entrypoint/       # Auth entry points
│   │   │       └── features/            # Funcionalidades por domínio
│   │   │           ├── user/            # Domínio de usuários
│   │   │           │   ├── controller/  # REST Controllers
│   │   │           │   ├── dto/         # Data Transfer Objects
│   │   │           │   ├── exception/   # Exceções específicas
│   │   │           │   ├── model/       # Entidades JPA
│   │   │           │   ├── repository/  # Repositórios de dados
│   │   │           │   │   └── projections/ # Projeções de dados
│   │   │           │   └── service/     # Lógica de negócio
│   │   │           └── role/            # Domínio de papéis/roles
│   │   │               ├── controller/
│   │   │               ├── dto/
│   │   │               ├── exception/
│   │   │               ├── model/
│   │   │               ├── repository/
│   │   │               └── service/
│   │   └── resources/
│   │       ├── application.yml          # Configuração principal
│   │       ├── application-dev.yml      # Configuração desenvolvimento
│   │       ├── application-test.yml     # Configuração testes
│   │       └── db/
│   │           ├── migration/           # Scripts Flyway (versionamento)
│   │           └── test/                # Dados de teste
│   └── test/                            # Testes unitários e integração
├── postman/                             # Collections para testes
│   └── ceasa_digital.postman_collection.json
├── target/                              # Arquivos compilados (Maven)
├── logs/                                # Logs da aplicação
├── Dockerfile                           # Imagem Docker
├── pom.xml                             # Dependências Maven
├── .env                                # Variáveis de ambiente (não versionado)
├── .env.example                        # Exemplo de variáveis
└── BACKEND_README.md                   # Este arquivo
```

## 🛠️ Solução de Problemas

### Problema: Erro ao obter token OAuth2

**Sintomas:** 
- `HTTP 401 Unauthorized` ao chamar `/oauth2/token`
- `invalid_client` ou `invalid_grant`

**Soluções:**
1. Verificar credenciais do client:
   ```bash
   # Confirme que estas variáveis estão corretas no .env:
   SECURITY_CLIENT_ID=ceasa-digital-client
   SECURITY_CLIENT_SECRET=ceasa-digital-secret
   ```
2. Verificar credenciais do usuário (username/password)
3. Verificar se o usuário existe no banco de dados
4. Verificar logs da aplicação para mais detalhes

### Problema: Token expirado

**Sintomas:** `HTTP 401` com mensagem de token expirado

**Soluções:**
1. Use o refresh token para obter um novo token:
   ```bash
   curl -X POST http://localhost:8080/oauth2/token \
     -H "Content-Type: application/x-www-form-urlencoded" \
     -d "grant_type=refresh_token&refresh_token=SEU_REFRESH_TOKEN&client_id=ceasa-digital-client&client_secret=ceasa-digital-secret"
   ```
2. Ou faça um novo login completo

### Problema: Acesso negado (403 Forbidden)

**Sintomas:** Token válido mas `HTTP 403` ao acessar endpoint

**Soluções:**
1. Verificar se o usuário tem a role necessária para o endpoint
2. Verificar se o endpoint está configurado corretamente no `ResourceServerSecurityConfig`
3. Verificar a anotação `@PreAuthorize` no controller

### Problema: CORS Error

**Sintomas:** Erro de CORS no frontend

**Soluções:**
1. Adicionar a URL do frontend nas configurações CORS:
   ```properties
   CORS_ORIGINS=http://localhost:3000,http://localhost:5173,https://seu-frontend.com
   ```
2. Reiniciar a aplicação após alterar as configurações

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
docker-compose -f docker-compose.yml logs -f backend

# Logs específicos do container
docker logs ceasa-digital-backend -f
```

**Verificar saúde dos serviços:**
```bash
# Health check da aplicação
curl http://localhost:8080/actuator/health

# Status dos containers
docker-compose ps
```

**Debugging de autenticação:**
```bash
# Testar endpoint público
curl http://localhost:8080/users/public

# Testar obtenção de token
curl -X POST http://localhost:8080/oauth2/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=password&username=admin&password=123456&client_id=ceasa-digital-client&client_secret=ceasa-digital-secret"

# Testar endpoint privado (substitua TOKEN_AQUI pelo token obtido)
curl -X GET http://localhost:8080/users/admin \
  -H "Authorization: Bearer TOKEN_AQUI"
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
