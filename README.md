# 🍴 API Restaurante

API REST robusta para gerenciamento de um sistema de restaurante, desenvolvida com **Java 21** e **Spring Boot**. A aplicação oferece controle total de produtos e categorias, com um sistema de autenticação seguro e níveis de acesso diferenciados.

## 🚀 Funcionalidades

### 📦 Produtos & Categorias
- **CRUD Completo de Categorias:** Gerenciamento total de categorias de alimentos.
- **CRUD Completo de Produtos:** Cadastro de produtos com suporte a URL de imagens e vinculação a categorias.
- **Paginação Eficiente:** Todas as listagens utilizam paginação para garantir performance e escalabilidade.

### 🔐 Segurança & Acesso
- **Autenticação JWT:** Login seguro via JSON Web Token.
- **Controle de Acesso (RBAC):** 
  - **ADMIN:** Acesso total (Criar, Editar, Deletar).
  - **CLIENTE:** Acesso de leitura (Visualizar cardápio).
- **Filtro de Segurança:** Validação de token em cada requisição protegida.

### 🛠️ Infraestrutura
- **Injeção de Dependências:** Uso rigoroso de injeção por construtor (Clean Code).
- **Tratamento de Erros:** Handler global para respostas padronizadas de erros (400, 404, 500).
- **Database Seeder:** Criação automática de um usuário administrador ao iniciar a aplicação pela primeira vez.

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Framework:** Spring Boot
- **Segurança:** Spring Security + JWT (Auth0)
- **Banco de Dados:** PostgreSQL
- **Persistência:** Spring Data JPA
- **Utilitários:** Lombok, Jakarta Validation

## ⚙️ Como Rodar o Projeto

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/FelipeOropeza/api_restaurante.git
    ```

2.  **Configure as variáveis de ambiente:**
    Crie um arquivo `.env` na raiz do projeto com as seguintes chaves:
    ```env
    DB_URL=jdbc:postgresql://localhost:5432/nome_do_banco
    DB_USER=seu_usuario
    DB_PASSWORD=sua_senha
    JWT_SECRET=sua_chave_secreta
    ```

3.  **Execute a aplicação:**
    ```bash
    ./mvnw spring-boot:run
    ```

4.  **Acesso Inicial:**
    A aplicação criará automaticamente um administrador:
    - **Email:** `admin@restaurante.com`
    - **Senha:** `admin123`

---
Desenvolvido por [Felipe Oropeza](https://github.com/FelipeOropeza)
