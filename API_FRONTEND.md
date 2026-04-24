# Documentação da API para Front-end

Esta documentação contém todas as informações necessárias para integrar o front-end com a API do Restaurante.

## Informações Gerais

- **URL Base:** `http://localhost:8080`
- **CORS:** Permitido para `http://localhost:5173` (Vite padrão)
- **Autenticação:** JWT (JSON Web Token) via Header `Authorization`.
  - Exemplo: `Authorization: Bearer <seu_token_jwt>`
- **Formato de Dados:** JSON

---

## 🔐 Autenticação e Usuários

### Login
Realiza a autenticação do usuário e retorna o token de acesso.

- **Endpoint:** `POST /login`
- **Corpo da Requisição:**
```json
{
  "email": "usuario@email.com",
  "senha": "123"
}
```
- **Resposta (200 OK):**
```json
{
  "token": "eyJhbG...",
  "nome": "Nome do Usuário",
  "email": "usuario@email.com",
  "tipo": "ADMIN" // ou "CLIENTE"
}
```

### Cadastro de Usuário
Cria uma nova conta de usuário (padrão CLIENTE).

- **Endpoint:** `POST /usuarios`
- **Corpo da Requisição:**
```json
{
  "nome": "Felipe",
  "email": "felipe@email.com",
  "senha": "123"
}
```
- **Resposta:** `200 OK` (sem corpo)

---

## 📂 Categorias

### Listar Categorias (Paginado)
Retorna uma lista de categorias com suporte a paginação.

- **Endpoint:** `GET /categorias`
- **Parâmetros de Query (Opcionais):**
  - `page`: Número da página (começa em 0)
  - `size`: Quantidade de itens por página
  - `sort`: Campo para ordenação (ex: `nome,asc`)
- **Resposta (200 OK):**
```json
{
  "content": [
    { "id": 1, "nome": "Bebidas" },
    { "id": 2, "nome": "Lanches" }
  ],
  "pageable": { ... },
  "totalElements": 2,
  "totalPages": 1,
  "size": 10,
  "number": 0,
  ...
}
```

### Detalhar Categoria
- **Endpoint:** `GET /categorias/{id}`
- **Resposta (200 OK):** `{ "id": 1, "nome": "Bebidas" }`

### Cadastrar Categoria (🔒 ADMIN)
- **Endpoint:** `POST /categorias`
- **Corpo da Requisição:** `{ "nome": "Nova Categoria" }`
- **Resposta (201 Created):** `{ "id": 3, "nome": "Nova Categoria" }`

### Atualizar Categoria (🔒 ADMIN)
- **Endpoint:** `PUT /categorias`
- **Corpo da Requisição:** `{ "id": 3, "nome": "Nome Atualizado" }`
- **Resposta (200 OK):** `{ "id": 3, "nome": "Nome Atualizado" }`

### Excluir Categoria (🔒 ADMIN)
- **Endpoint:** `DELETE /categorias/{id}`
- **Resposta:** `204 No Content`

---

## 🍔 Produtos

### Listar Produtos (Paginado)
- **Endpoint:** `GET /produtos`
- **Resposta (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "nome": "Hambúrguer",
      "preco": 25.50,
      "categoria": "Lanches"
    }
  ],
  ...
}
```

### Detalhar Produto
- **Endpoint:** `GET /produtos/{id}`
- **Resposta (200 OK):**
```json
{
  "id": 1,
  "nome": "Hambúrguer",
  "descricao": "Carne bovina, queijo, alface e tomate",
  "preco": 25.50,
  "categoria": "Lanches"
}
```

### Cadastrar Produto (🔒 ADMIN)
- **Endpoint:** `POST /produtos`
- **Corpo da Requisição:**
```json
{
  "nome": "Coca-Cola",
  "descricao": "Lata 350ml",
  "preco": 6.50,
  "idCategoria": 1
}
```
- **Resposta (201 Created):** Objeto detalhado do produto criado.

### Atualizar Produto (🔒 ADMIN)
- **Endpoint:** `PUT /produtos`
- **Corpo da Requisição:**
```json
{
  "id": 1,
  "nome": "Hambúrguer Gourmet",
  "descricao": "Carne Angus, queijo brie e cebola caramelizada",
  "preco": 35.00,
  "idCategoria": 2
}
```
*Obs: Todos os campos exceto `id` são opcionais na atualização.*

### Excluir Produto (🔒 ADMIN)
- **Endpoint:** `DELETE /produtos/{id}`
- **Resposta:** `204 No Content`

---

## ⚠️ Tratamento de Erros

A API retorna os seguintes padrões de erro:

### 400 Bad Request (Erro de Validação)
Ocorre quando campos obrigatórios não são enviados ou possuem formato inválido.
```json
[
  {
    "campo": "email",
    "mensagem": "Email é obrigatório"
  },
  {
    "campo": "senha",
    "mensagem": "Senha é obrigatória"
  }
]
```

### 403 Forbidden
Ocorre quando o token é inválido, expirou ou o usuário não tem permissão para a rota (ex: cliente tentando acessar rota de admin).

### 404 Not Found
Ocorre quando um recurso (ID) não existe no banco de dados.
