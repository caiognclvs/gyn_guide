# Gyn Guide - Sistema de Reviews de Restaurantes e Bares

Sistema web para agregação de reviews de restaurantes e bares locais, desenvolvido com Spring Boot (backend) e React (frontend).

## Estrutura do Projeto

```
gyn_guide/
├── backend/          # Aplicação Spring Boot
└── frontend/         # Aplicação React
```

## Funcionalidades

- ✅ Cadastro e login de usuários
- ✅ Dois tipos de usuários:
  - **Pessoa Física**: Pode submeter avaliações (texto + nota 1-5)
  - **Pessoa Jurídica**: Pode cadastrar estabelecimentos para serem avaliados

## Tecnologias

### Backend
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database (desenvolvimento local)
- Java 17

### Frontend
- React 18
- React Router
- Vite
- Axios

## Como Executar

### Backend

1. Certifique-se de ter o Java 17 e Maven instalados
2. Navegue até a pasta do backend:
   ```bash
   cd backend
   ```
3. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```
4. O backend estará disponível em `http://localhost:8080`
5. Console H2 disponível em `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:gyn_guide_db`
   - Username: `sa`
   - Password: (vazio)

### Frontend

1. Certifique-se de ter o Node.js instalado
2. Navegue até a pasta do frontend:
   ```bash
   cd frontend
   ```
3. Instale as dependências:
   ```bash
   npm install
   ```
4. Execute a aplicação:
   ```bash
   npm run dev
   ```
5. O frontend estará disponível em `http://localhost:3000`

## Endpoints da API

### Autenticação

- `POST /api/auth/cadastro/pessoa-fisica` - Cadastro de pessoa física
- `POST /api/auth/cadastro/pessoa-juridica` - Cadastro de pessoa jurídica
- `POST /api/auth/login` - Login

## Modelos de Dados

### Pessoa Física
- Email
- Senha
- Nome
- Data de Nascimento

### Pessoa Jurídica
- Email
- Senha
- Nome Fantasia
- Razão Social
- CNPJ
- Endereço

## Próximos Passos

- Implementar sistema de avaliações
- Implementar cadastro de estabelecimentos
- Adicionar autenticação JWT
- Implementar criptografia de senhas (BCrypt)
- Adicionar validações mais robustas

