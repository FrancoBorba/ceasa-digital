/**
 * EXEMPLO DE COMO USAR OS ENDPOINTS EM QUALQUER COMPONENTE
 * 
 * Endpoints integrados:
 * - GET /api/v1/produtor-produtos/me (listar produtos do produtor)
 * - PUT /api/v1/produtor-produtos/desativar/{id} (desativar produto)
 */

import { getMyProducts, deactivateProduct } from './produtorProdutoService';

// ============================================
// EXEMPLO 1: Buscar e listar produtos
// ============================================
export const exemploListarProdutos = async () => {
  try {
    // Chama o endpoint GET /api/v1/produtor-produtos/me
    const produtos = await getMyProducts();
    console.log('Produtos do produtor:', produtos);
    return produtos;
  } catch (error) {
    console.error('Erro ao buscar produtos:', error);
    throw error;
  }
};

// ============================================
// EXEMPLO 2: Desativar um produto
// ============================================
export const exemploDesativarProduto = async (produtoId) => {
  try {
    // Chama o endpoint PUT /api/v1/produtor-produtos/desativar/{id}
    const resultado = await deactivateProduct(produtoId);
    console.log('Produto desativado:', resultado);
    return resultado;
  } catch (error) {
    console.error('Erro ao desativar produto:', error);
    throw error;
  }
};

// ============================================
// EXEMPLO 3: Uso em um componente React
// ============================================

/*
import React, { useState, useEffect } from 'react';
import { getMyProducts, deactivateProduct } from './services/produtorProdutoService';

const MeuComponente = () => {
  const [produtos, setProdutos] = useState([]);

  // Carregar produtos ao montar
  useEffect(() => {
    const carregarProdutos = async () => {
      const data = await getMyProducts();
      setProdutos(data);
    };
    carregarProdutos();
  }, []);

  // Função para excluir (desativar) produto
  const handleExcluir = async (id) => {
    if (confirm('Deseja realmente excluir?')) {
      await deactivateProduct(id);
      // Atualiza a lista removendo o produto
      setProdutos(produtos.filter(p => p.id !== id));
      alert('Produto desativado!');
    }
  };

  return (
    <div>
      {produtos.map(produto => (
        <div key={produto.id}>
          <h3>{produto.nome}</h3>
          <p>R$ {produto.preco}</p>
          <button onClick={() => handleExcluir(produto.id)}>
            Excluir
          </button>
        </div>
      ))}
    </div>
  );
};
*/
