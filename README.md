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

### Usuários

- `GET /api/usuarios/pessoas-fisicas` - Lista todas as pessoas físicas cadastradas
- `GET /api/usuarios/pessoas-juridicas` - Lista todas as pessoas jurídicas cadastradas

## Visualizando os Dados

Os dados dos usuários cadastrados ficam armazenados no banco de dados H2 (em memória). Existem duas formas de visualizá-los:

### 1. Console H2 (Recomendado)

1. Com o backend rodando, acesse: `http://localhost:8080/h2-console`
2. Preencha os campos:
   - **JDBC URL**: `jdbc:h2:mem:gyn_guide_db`
   - **Username**: `sa`
   - **Password**: (deixe vazio)
3. Clique em "Connect"
4. Você poderá visualizar e consultar as tabelas:
   - `USUARIOS` - Tabela base com email, senha e tipo de usuário
   - `PESSOAS_FISICAS` - Dados específicos de pessoas físicas (nome, data de nascimento)
   - `PESSOAS_JURIDICAS` - Dados específicos de pessoas jurídicas (nome fantasia, razão social, CNPJ, endereço)

**Exemplo de consulta SQL:**
```sql
SELECT * FROM USUARIOS;
SELECT * FROM PESSOAS_FISICAS;
SELECT * FROM PESSOAS_JURIDICAS;
```

### 2. Endpoints da API

Você também pode usar os endpoints da API para listar os usuários:

- **Listar Pessoas Físicas**: `GET http://localhost:8080/api/usuarios/pessoas-fisicas`
- **Listar Pessoas Jurídicas**: `GET http://localhost:8080/api/usuarios/pessoas-juridicas`

**Nota**: Como o banco H2 está configurado em memória (`jdbc:h2:mem:gyn_guide_db`), os dados são perdidos quando a aplicação é encerrada. Para persistência permanente, seria necessário alterar a configuração para usar um arquivo.

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

