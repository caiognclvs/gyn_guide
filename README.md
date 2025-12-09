# 🍽️ Gyn Guide

Sistema web para avaliação e descoberta de estabelecimentos (restaurantes, bares, cafeterias) com foco na experiência do usuário e gestão de perfis de estabelecimentos.

## 📋 Sobre o Projeto

Gyn Guide é uma plataforma que conecta pessoas físicas (clientes) e pessoas jurídicas (proprietários de estabelecimentos), permitindo:

- **Para Pessoas Físicas**: Descobrir estabelecimentos, criar avaliações com notas e comentários, gerenciar histórico de avaliações
- **Para Pessoas Jurídicas**: Cadastrar e gerenciar estabelecimentos com informações detalhadas e imagens
- **Sistema de Avaliações**: Notas de 1 a 5 estrelas com comentários detalhados

## 🏗️ Arquitetura

### Backend
- **Framework**: Spring Boot 3.2.0
- **Linguagem**: Java 17
- **Banco de Dados**: PostgreSQL (JPA/Hibernate)
- **Arquitetura**: Camadas (Controller → Service → Repository)
- **Padrões**: DTOs, Custom Exceptions, CORS configurado

### Frontend
- **Framework**: React 18.2
- **Build Tool**: Vite 5.0
- **Roteamento**: React Router 6.20
- **HTTP Client**: Axios 1.6.2
- **UI**: CSS customizado

## 🚀 Tecnologias

### Backend
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- PostgreSQL Driver
- Lombok (opcional)

### Frontend
- React
- React Router DOM
- Axios
- Vite

## 📦 Estrutura do Projeto

```
gyn_guide/
├── backend/
│   ├── src/main/java/com/gynguide/
│   │   ├── config/          # Configurações (CORS, FileStorage)
│   │   ├── controller/      # Endpoints REST
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── exception/       # Exceções customizadas
│   │   ├── model/           # Entidades JPA
│   │   ├── repository/      # Acesso a dados
│   │   ├── service/         # Lógica de negócio
│   │   └── GynGuideApplication.java
│   └── pom.xml
│
└── frontend/
    ├── src/
    │   ├── pages/           # Páginas da aplicação
    │   ├── services/        # API client (Axios)
    │   ├── App.jsx
    │   └── main.jsx
    └── package.json
```

## 🗄️ Modelo de Dados

### Entidades Principais

**Usuario** (Abstrato)
- Herança: `JOINED` strategy
- Subclasses: PessoaFisica, PessoaJuridica

**PessoaFisica**
- Atributos: nome, dataNascimento
- Relacionamentos: 1:N com Avaliacao

**PessoaJuridica**
- Atributos: nomeFantasia, razaoSocial, cnpj, endereco
- Relacionamentos: 1:N com Estabelecimento

**Estabelecimento**
- Atributos: nome, endereco, descricao, imagemUrl
- Relacionamentos: N:1 com PessoaJuridica, 1:N com Avaliacao

**Avaliacao**
- Atributos: texto, nota (1-5), dataAvaliacao
- Relacionamentos: N:1 com PessoaFisica (autor), N:1 com Estabelecimento

## ⚙️ Configuração e Instalação

### Pré-requisitos

- Java 17+
- Maven 3.8+
- Node.js 18+
- PostgreSQL 14+

### Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL:
```sql
CREATE DATABASE gynguide;
```

2. Configure as credenciais em `backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gynguide
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

### Backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

O servidor estará disponível em: `http://localhost:8080`

### Frontend

```bash
cd frontend
npm install
npm run dev
```

O aplicativo estará disponível em: `http://localhost:5173`

## 🔌 API Endpoints

### Autenticação
- `POST /api/auth/login` - Login de usuário
- `POST /api/auth/cadastro/pessoa-fisica` - Cadastro de pessoa física
- `POST /api/auth/cadastro/pessoa-juridica` - Cadastro de pessoa jurídica

### Perfil
- `GET /api/perfil/pessoa-fisica/{id}` - Buscar perfil de pessoa física
- `GET /api/perfil/pessoa-juridica/{id}` - Buscar perfil de pessoa jurídica
- `PUT /api/perfil/pessoa-fisica/{id}` - Atualizar perfil de pessoa física
- `PUT /api/perfil/pessoa-juridica/{id}` - Atualizar perfil de pessoa jurídica

### Estabelecimentos
- `GET /api/estabelecimentos` - Listar todos estabelecimentos
- `GET /api/estabelecimentos/{id}` - Buscar estabelecimento por ID
- `POST /api/estabelecimentos` - Criar estabelecimento (multipart/form-data)
- `GET /api/estabelecimentos/proprietario/{id}` - Buscar estabelecimento do proprietário

### Avaliações
- `POST /api/avaliacoes` - Criar avaliação
- `GET /api/avaliacoes/estabelecimento/{id}` - Listar avaliações de um estabelecimento
- `GET /api/avaliacoes/minhas/{autorId}` - Listar avaliações do usuário

### Usuários
- `GET /api/usuarios` - Listar todos usuários
- `GET /api/usuarios/{id}` - Buscar usuário por ID

## 🎨 Funcionalidades

### Para Pessoas Físicas
- ✅ Cadastro e login
- ✅ Visualização e edição de perfil
- ✅ Listagem de estabelecimentos
- ✅ Visualização detalhada de estabelecimentos
- ✅ Criação de avaliações (nota + comentário)
- ✅ Visualização do histórico de avaliações

### Para Pessoas Jurídicas
- ✅ Cadastro e login
- ✅ Visualização e edição de perfil
- ✅ Cadastro de estabelecimento com upload de imagem
- ✅ Gerenciamento do estabelecimento
- ✅ Visualização de avaliações recebidas

## 🛡️ Tratamento de Erros

Sistema de exceções customizadas com GlobalExceptionHandler:
- `EmailJaCadastradoException` - Email já existe no sistema
- `CnpjJaCadastradoException` - CNPJ já cadastrado
- `CredenciaisInvalidasException` - Login inválido
- `UsuarioNaoEncontradoException` - Usuário não encontrado
- `EstabelecimentoNaoEncontradoException` - Estabelecimento não encontrado
- `ArquivoException` - Erro em upload de arquivos

Todas as exceções retornam JSON padronizado:
```json
{
  "timestamp": "2025-12-09T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Mensagem de erro específica",
  "path": "/api/endpoint"
}
```

## 📝 Conceitos de POO Aplicados

- **Encapsulamento**: Atributos privados com getters/setters
- **Herança**: Usuario → PessoaFisica/PessoaJuridica (JOINED)
- **Polimorfismo**: Métodos abstratos e sobrescrita
- **Abstração**: Classe Usuario abstrata, interfaces Repository
- **Composição**: Estabelecimento possui PessoaJuridica, Avaliacao possui PessoaFisica e Estabelecimento

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT.

## 👥 Autores

Desenvolvido como projeto acadêmico.

---

**Status do Projeto**: ✅ Em desenvolvimento ativo
