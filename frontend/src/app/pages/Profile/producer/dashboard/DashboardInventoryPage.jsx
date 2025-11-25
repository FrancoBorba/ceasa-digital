import React, { useState, useEffect } from 'react';
import styles from './DashboardInventoryPage.module.css';
import InventoryControls from '../../../auth/components/profile/producer/producer-inventory/InventoryControls';
import InventoryTable from '../../../auth/components/profile/producer/producer-inventory/InventoryTable';
import { getMyProducts, deactivateProduct } from '../../../Home/services/produtorProdutoService';

/**
 * Página de inventário do produtor - integrada com endpoints da API
 * Carrega produtos do produtor logado e permite desativação
 */
const DashboardInventoryPage = () => {
  const [produtos, setProdutos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  /**
   * Carrega os produtos do produtor ao montar o componente
   * Integração: GET /api/v1/produtor-produtos/me
   */
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        setLoading(true);
        setError(null);
        const data = await getMyProducts();
        const produtosFormatados = data.map(item => ({
          id: item.id,
          nome: item.produtoNome || 'N/A',
          idProduto: `#${item.produtoId || item.id}`,
          categoria: item.categoria || 'Geral'
        }));
        setProdutos(produtosFormatados);
      } catch (err) {
        console.error('Erro ao carregar produtos:', err);
        setError('Erro ao carregar produtos. Tente novamente.');
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  /**
   * Desativa um produto (botão excluir na tabela)
   * Integração: PUT /api/v1/produtor-produtos/desativar/{id}
   */
  const handleDelete = async (id) => {
    if (window.confirm("Tem certeza que deseja desativar este produto?")) {
      try {
        await deactivateProduct(id);
        setProdutos(produtosAtuais => 
          produtosAtuais.filter(produto => produto.id !== id)
        );
        alert('Produto desativado com sucesso!');
      } catch (err) {
        console.error('Erro ao desativar produto:', err);
        alert('Erro ao desativar produto. Tente novamente.');
      }
    }
  };

  return (
    <div className={styles.wrapper}>
      <InventoryControls />
      
      {loading && <p>Carregando produtos...</p>}
      {error && <p style={{color: 'red'}}>{error}</p>}
      
      {!loading && !error && (
        <InventoryTable 
          produtos={produtos}
          onDelete={handleDelete}
        />
      )}
    </div>
  );
};

export default DashboardInventoryPage;