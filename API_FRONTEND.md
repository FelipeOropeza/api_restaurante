# 🍽️ Documentação Completa da API - Sistema Restaurante

Esta documentação contém todas as informações necessárias para integrar o front-end com a API do Restaurante, incluindo autenticação, cardápio e o novo sistema de pedidos.

---

## 🚀 Informações Gerais

- **URL Base:** `http://localhost:8080`
- **CORS:** Permitido para `http://localhost:5173` (Vite padrão)
- **Autenticação:** JWT (JSON Web Token) via Header `Authorization`.
  - Exemplo: `Authorization: Bearer <seu_token_jwt>`
- **Formato de Dados:** JSON (UTF-8)

---

## 🔐 Autenticação e Usuários

### Login
Realiza a autenticação do usuário e retorna o token de acesso.
- **Endpoint:** `POST /login`
- **Público:** Sim
- **Corpo:**
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
- **Público:** Sim
- **Corpo:** `{ "nome": "Felipe", "email": "felipe@email.com", "senha": "123" }`
- **Resposta:** `200 OK`

---

## 📂 Cardápio (Categorias e Produtos)

### Listar Categorias
- **Endpoint:** `GET /categorias`
- **Público:** ✅ **Sim** (Não requer token)
- **Parâmetros:** `page`, `size`, `sort`
- **Resposta:** Lista paginada de categorias.

### Listar Produtos
- **Endpoint:** `GET /produtos`
- **Público:** ✅ **Sim** (Não requer token)
- **Parâmetros:** `page`, `size`, `sort`
- **Resposta:** Lista paginada de produtos com nome, preço e categoria.

### Detalhes do Produto
- **Endpoint:** `GET /produtos/{id}`
- **Público:** ✅ **Sim**
- **Resposta:** Objeto completo do produto com descrição.

> **Nota para Admin:** As operações de criação, edição e exclusão (POST, PUT, DELETE) em `/produtos` e `/categorias` requerem o tipo `ADMIN` e token JWT.

---

## 🛒 Sistema de Pedidos

O fluxo de pedidos envolve o gerenciamento do carrinho no frontend e a finalização via API.

### Criar Novo Pedido
Finaliza a compra e registra o pedido no sistema.
- **Endpoint:** `POST /pedidos`
- **Público:** 🔒 **Não** (Requer token JWT)
- **Corpo (JSON):**
```json
{
  "cep": "12345-678",
  "complemento": "Apt 101, Bloco B",
  "metodoPagamento": "PIX", // CARTAO, PIX ou DINHEIRO
  "itens": [
    {
      "produtoId": 1,
      "quantidade": 2
    },
    {
      "produtoId": 5,
      "quantidade": 1
    }
  ]
}
```

### Meus Pedidos
Lista todos os pedidos realizados pelo usuário logado.
- **Endpoint:** `GET /pedidos`
- **Público:** 🔒 **Não** (Requer token JWT)
- **Resposta:** Lista de pedidos com status e valor total.

### Status e Pagamentos (Enums)

| Campo | Valores Possíveis |
| :--- | :--- |
| `metodoPagamento` | `CARTAO`, `PIX`, `DINHEIRO` |
| `status` | `PENDENTE`, `PREPARANDO`, `SAIU_PARA_ENTREGA`, `ENTREGUE`, `CANCELADO` |

---

## ⚠️ Tratamento de Erros

| Código | Descrição | Exemplo de Resposta |
| :--- | :--- | :--- |
| **400** | Erro de Validação (campos faltando ou inválidos) | `[{"campo": "email", "mensagem": "não pode estar vazio"}]` |
| **403** | Token expirado, inválido ou sem permissão (Admin/User) | - |
| **404** | Recurso (ID) não encontrado | - |

---

## 🛠️ Dicas de Integração (Frontend)

1. **Persistência do Carrinho:** Use `localStorage` ou `sessionStorage` para manter o carrinho se o usuário atualizar a página.
2. **Cálculo de Total:** Calcule o total no frontend apenas para exibição, mas saiba que a API recalcula tudo no backend para segurança.
3. **Página Inicial:** Como `GET /produtos` é público, você pode carregar o cardápio assim que o app abrir, sem forçar o login imediato.
4. **Header Global:** Configure uma instância do Axios (ou fetch) para injetar o token automaticamente se ele existir:
   ```javascript
   api.interceptors.request.use(config => {
     const token = localStorage.getItem('token');
     if (token) config.headers.Authorization = `Bearer ${token}`;
     return config;
   });
   ```
