# Guia de Atualização do Frontend - Sistema de Pedidos

Este guia detalha as mudanças necessárias no frontend para integrar com o novo sistema de pedidos e as rotas públicas de produtos/categorias.

## 1. Rotas Públicas (Sem Autenticação)

As seguintes rotas agora podem ser acessadas sem o token JWT no cabeçalho `Authorization`:

- `GET /produtos`: Lista todos os produtos (paginado).
- `GET /produtos/{id}`: Detalhes de um produto específico.
- `GET /categorias`: Lista todas as categorias.
- `GET /categorias/{id}`: Detalhes de uma categoria específica.

**Dica:** No Vue/Nuxt, você pode remover a obrigatoriedade de token para as telas de "Cardápio" ou "Home".

## 2. Sistema de Carrinho e Finalização

O carrinho deve ser gerenciado no **Frontend** (ex: Pinia ou LocalStorage). Quando o usuário decidir finalizar a compra, você deve enviar um `POST` para `/pedidos`.

### Fluxo Sugerido:
1. Usuário adiciona itens ao carrinho (armazenado localmente).
2. Na tela de checkout, o usuário preenche o **CEP**, **Complemento** e escolhe o **Método de Pagamento**.
3. O frontend envia o JSON para a API.

### Endpoint: `POST /pedidos`
**Requer Autenticação (Token JWT)**

**Corpo da Requisição (JSON):**
```json
{
  "cep": "12345-678",
  "complemento": "Apt 101, Bloco B",
  "metodoPagamento": "PIX", 
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

**Valores aceitos para `metodoPagamento`:**
- `CARTAO`
- `PIX`
- `DINHEIRO`

### Endpoint: `GET /pedidos`
**Requer Autenticação (Token JWT)**
Retorna a lista de pedidos realizados pelo usuário logado.

---

## 3. Modelos de Dados (TypeScript)

Se estiver usando TypeScript, aqui estão as interfaces sugeridas:

```typescript
export enum MetodoPagamento {
  CARTAO = 'CARTAO',
  PIX = 'PIX',
  DINHEIRO = 'DINHEIRO'
}

export enum StatusPedido {
  PENDENTE = 'PENDENTE',
  PREPARANDO = 'PREPARANDO',
  SAIU_PARA_ENTREGA = 'SAIU_PARA_ENTREGA',
  ENTREGUE = 'ENTREGUE',
  CANCELADO = 'CANCELADO'
}

export interface ItemPedidoRequest {
  produtoId: number;
  quantidade: number;
}

export interface CadastroPedidoRequest {
  cep: string;
  complemento?: string;
  metodoPagamento: MetodoPagamento;
  itens: ItemPedidoRequest[];
}
```

## 4. Notas Importantes
- O preço dos itens é capturado no momento da criação do pedido (preço atual do produto).
- O `valorTotal` é calculado automaticamente pelo backend.
- Certifique-se de tratar erros de "Produto não encontrado" caso o ID enviado seja inválido.
