# Lista de Endpoints para o Painel Administrativo

Esta lista detalha os endpoints necessarios na API (backend) para suportar as funcionalidades do painel administrativo (Admin) do sistema de restaurante. Todos os endpoints abaixo exigem que o usuario esteja autenticado e possua o tipo `ADMIN`.

## 1. Gestao de Pedidos

A gestao de pedidos e o coracao da operacao do restaurante, permitindo acompanhar o fluxo desde a criacao ate a entrega.

### 1.1. Listar Todos os Pedidos (Visao Geral)
- **Endpoint:** `GET /admin/pedidos`
- **Descricao:** Retorna a lista de todos os pedidos. Idealmente deve suportar paginacao e filtros.
- **Parametros (Query):** 
  - `page` (int): numero da pagina.
  - `size` (int): quantidade por pagina.
  - `status` (Enum opcional): filtra por PENDENTE, PREPARANDO, etc.
- **Resposta (200 OK):** Lista paginada contendo o resumo dos pedidos (id, nome do cliente, valor total, status, data de criacao).

### 1.2. Detalhes de um Pedido Especifico
- **Endpoint:** `GET /admin/pedidos/{id}`
- **Descricao:** Retorna todos os detalhes de um pedido, incluindo os itens comprados, endereco de entrega e metodo de pagamento.
- **Resposta (200 OK):** Objeto do pedido com a lista aninhada de itens (quantidade, produto, valor unitario).

### 1.3. Atualizar Status do Pedido
- **Endpoint:** `PUT /admin/pedidos/{id}/status`
- **Descricao:** Permite que o admin avance ou cancele o fluxo do pedido.
- **Corpo da Requisicao:**
```json
{
  "status": "PREPARANDO" // PENDENTE, PREPARANDO, SAIU_PARA_ENTREGA, ENTREGUE, CANCELADO
}
```
- **Resposta (200 OK):** Objeto do pedido atualizado.


## 2. Gestao de Produtos (Cardapio)

### 2.1. Criar Novo Produto
- **Endpoint:** `POST /produtos`
- **Descricao:** Adiciona um novo item ao cardapio.
- **Corpo da Requisicao:**
```json
{
  "nome": "Hamburguer Duplo",
  "descricao": "Pao, duas carnes, queijo, bacon",
  "preco": 35.90,
  "categoriaId": 1
}
```
- **Resposta (201 Created):** Retorna o produto recem-criado com seu ID.

### 2.2. Atualizar Produto Existente
- **Endpoint:** `PUT /produtos/{id}`
- **Descricao:** Edita as informacoes de um produto (ex: alterar preco, nome ou descricao).
- **Corpo da Requisicao:** Mesma estrutura do POST, porem atualizando os dados antigos.
- **Resposta (200 OK):** Produto atualizado.

### 2.3. Excluir ou Inativar Produto
- **Endpoint:** `DELETE /produtos/{id}`
- **Descricao:** Remove o produto do cardapio.
- **Dica de Arquitetura:** Recomendado implementar exclusao logica (soft delete) adicionando um campo `ativo` (boolean) no banco, para nao quebrar o historico de pedidos antigos que referenciam este produto.
- **Resposta (204 No Content):** Sucesso, sem retorno de corpo.


## 3. Gestao de Categorias

### 3.1. Criar Nova Categoria
- **Endpoint:** `POST /categorias`
- **Descricao:** Cria uma categoria (ex: "Bebidas", "Sobremesas").
- **Corpo da Requisicao:**
```json
{
  "nome": "Sobremesas"
}
```
- **Resposta (201 Created):** Retorna a categoria criada.

### 3.2. Atualizar Categoria
- **Endpoint:** `PUT /categorias/{id}`
- **Descricao:** Altera o nome de uma categoria.
- **Resposta (200 OK):** Categoria atualizada.

### 3.3. Excluir Categoria
- **Endpoint:** `DELETE /categorias/{id}`
- **Descricao:** Exclui uma categoria. A API deve retornar um erro (ex: 400 Bad Request ou 409 Conflict) se o admin tentar excluir uma categoria que ainda possui produtos vinculados a ela.
- **Resposta (204 No Content).**


## 4. (Opcional/Avancado) Dashboard e Metricas

Para enriquecer a tela inicial do admin com indicadores de negocio.

### 4.1. Resumo de Vendas e Metricas
- **Endpoint:** `GET /admin/dashboard/resumo`
- **Descricao:** Traz informacoes consolidadas para exibicao de cards.
- **Resposta (200 OK):**
```json
{
  "faturamentoHoje": 1540.50,
  "pedidosPendentes": 5,
  "pedidosFinalizadosHoje": 32,
  "produtosMaisVendidos": [
    { "produto": "Coca-Cola", "quantidade": 40 }
  ]
}
```


## 5. (Opcional) Gestao de Usuarios/Clientes

### 5.1. Listar Usuarios
- **Endpoint:** `GET /admin/usuarios`
- **Descricao:** Lista as contas cadastradas na plataforma para o admin visualizar.

### 5.2. Alterar Permissoes
- **Endpoint:** `PUT /admin/usuarios/{id}/tipo`
- **Descricao:** Altera o tipo do usuario, util se precisar dar acesso de ADMIN a um novo funcionario.
- **Corpo:** `{ "tipo": "ADMIN" }`
