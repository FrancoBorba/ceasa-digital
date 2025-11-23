import React, { useEffect, useState } from 'react';
import { getMyProducts, deactivateProduct } from './services/produtorProdutoService';

/**
 * Componente para listar e gerenciar produtos do produtor
 * Integra os endpoints:
 * - GET /api/v1/produtor-produtos/me (listar produtos)
 * - PUT /api/v1/produtor-produtos/desativar/{id} (desativar produto)
 */
const ProdutorProdutos = () => {
  const [produtos, setProdutos] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Carregar produtos ao montar o componente
  useEffect(() => {
    carregarProdutos();
  }, []);

  /**
   * Função para buscar produtos do produtor logado
   * Integra com: GET /api/v1/produtor-produtos/me
   */
  const carregarProdutos = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getMyProducts();
      setProdutos(data);
    } catch (err) {
      setError('Erro ao carregar produtos. Tente novamente.');
      console.error('Erro ao buscar produtos:', err);
    } finally {
      setLoading(false);
    }
  };

  /**
   * Função para desativar/excluir produto
   * Integra com: PUT /api/v1/produtor-produtos/desativar/{id}
   * @param {number} id - ID do produto a ser desativado
   */
  const handleExcluir = async (id) => {
    // Confirmação antes de desativar
    if (!window.confirm('Tem certeza que deseja desativar este produto?')) {
      return;
    }

    try {
      // Chama o endpoint de desativar
      await deactivateProduct(id);
      
      // Remove o produto da lista após desativar
      setProdutos(produtos.filter(p => p.id !== id));
      
      alert('Produto desativado com sucesso!');
    } catch (err) {
      alert('Erro ao desativar produto. Tente novamente.');
      console.error('Erro ao desativar produto:', err);
    }
  };

  // Estado de loading
  if (loading) {
    return (
      <div className="flex justify-center items-center min-h-screen">
        <p className="text-lg">Carregando produtos...</p>
      </div>
    );
  }

  // Estado de erro
  if (error) {
    return (
      <div className="flex justify-center items-center min-h-screen">
        <div className="text-center">
          <p className="text-red-600 mb-4">{error}</p>
          <button
            onClick={carregarProdutos}
            className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
          >
            Tentar Novamente
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold text-gray-800">Meus Produtos</h1>
        <button
          onClick={carregarProdutos}
          className="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
        >
          Atualizar Lista
        </button>
      </div>

      {produtos.length === 0 ? (
        <div className="text-center py-12">
          <p className="text-gray-600 text-lg">Você ainda não tem produtos cadastrados.</p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {produtos.map((produto) => (
            <div
              key={produto.id}
              className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow"
            >
              {/* Imagem do produto */}
              {produto.imagemUrl && (
                <img
                  src={produto.imagemUrl}
                  alt={produto.nome}
                  className="w-full h-48 object-cover rounded-lg mb-4"
                />
              )}

              {/* Nome do produto */}
              <h2 className="text-xl font-semibold text-gray-800 mb-2">
                {produto.nome}
              </h2>
              
              {/* Descrição */}
              {produto.descricao && (
                <p className="text-gray-600 mb-3 line-clamp-2">
                  {produto.descricao}
                </p>
              )}

              {/* Preço e Estoque */}
              <div className="flex justify-between items-center mb-4">
                <span className="text-2xl font-bold text-green-600">
                  R$ {produto.preco ? produto.preco.toFixed(2) : '0.00'}
                </span>
                
                {produto.estoque !== undefined && (
                  <span className="text-sm text-gray-500">
                    Estoque: {produto.estoque}
                  </span>
                )}
              </div>

              {/* Botões de ação */}
              <div className="flex gap-2">
                {/* Botão Excluir - Integrado com endpoint de desativar */}
                <button
                  onClick={() => handleExcluir(produto.id)}
                  className="flex-1 px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700 transition-colors"
                >
                  Excluir
                </button>
                
                {/* Botão Editar (pode ser implementado depois) */}
                <button
                  onClick={() => {
                    console.log('Editar produto:', produto.id);
                    // Implementar navegação para edição
                  }}
                  className="flex-1 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition-colors"
                >
                  Editar
                </button>
              </div>

              {/* Status do produto */}
              {produto.ativo !== undefined && (
                <div className="mt-3 text-center">
                  <span
                    className={`inline-block px-3 py-1 rounded-full text-sm ${
                      produto.ativo
                        ? 'bg-green-100 text-green-800'
                        : 'bg-red-100 text-red-800'
                    }`}
                  >
                    {produto.ativo ? 'Ativo' : 'Inativo'}
                  </span>
                </div>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default ProdutorProdutos;
