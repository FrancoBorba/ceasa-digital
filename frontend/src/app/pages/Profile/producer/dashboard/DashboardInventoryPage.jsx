import React, { useState, useEffect } from 'react';
import styles from './DashboardInventoryPage.module.css';
import InventoryControls from '../../../auth/components/profile/producer/producer-inventory/InventoryControls';
import InventoryTable from '../../../auth/components/profile/producer/producer-inventory/InventoryTable';
import AddProductModal from '../../../auth/components/profile/producer/producer-inventory/AddProductModal';
import apiRequester from '../../../auth/services/apiRequester';

// Lista de produtos inicial (mock)
const DashboardInventoryPage = () => {
  const [produtos, setProdutos] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  const API_URL = 'http://localhost:8080';
  const ENDPOINT = '/api/v1/produtor-produtos';

  const fetchInventario = async () => {
    const token = localStorage.getItem('authToken');
    setIsLoading(true);

    try {
      const response = await fetch(`${API_URL}${ENDPOINT}/me`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });

      if (response.ok) {
        const data = await response.json();
        
        const produtosFormatados = data.map(item => ({
          id: item.id, 
          nome: item.nomeProduto || item.produto?.nome || 'Produto sem nome',
          idProduto: item.produtoId || `#${item.id}`,
          categoria: item.categoria || item.produto?.categoria || 'Geral',
          estoque: item.quantidade, 
          status: item.status
        }));

        setProdutos(produtosFormatados);
      } else {
        console.error("Erro ao buscar inventário:", response.status);
      }
    } catch (error) {
      console.error("Erro de conexão:", error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchInventario();
  }, []);
  const handleDelete = async (id) => {
    if (!window.confirm("Tem certeza que deseja remover este item do seu inventário?")) return;

    const token = localStorage.getItem('authToken');
    try {
      const response = await fetch(`${API_URL}${ENDPOINT}/desativar/${id}`, {
        method: 'PUT',
        headers: { 
          'Authorization': `Bearer ${token}` 
        }
      });

      if (response.ok) {
        setProdutos(listaAtual => listaAtual.filter(p => p.id !== id));
        alert("Item removido com sucesso!");
      } else {
        alert("Erro ao remover o item.");
      }
    } catch (error) {
      console.error(error);
      alert("Erro de conexão ao tentar remover.");
    }
  };

  const handleAddProduct = () => {
    setIsModalOpen(true);
  };

  const handleSaveProduct = (novoProduto) => {
    console.log("Salvar:", novoProduto);
    setIsModalOpen(false);
    fetchInventario(); 
  };

  const handleEdit = (id) => {
    console.log("Editar id:", id);
  };

  return (
    <div className={styles.wrapper}>
      <InventoryControls onAddProduct={handleAddProduct} />
      
      {isLoading ? (
        <p style={{padding: '2rem', textAlign: 'center'}}>Carregando seu estoque...</p>
      ) : (
        <InventoryTable 
          produtos={produtos} 
          onEdit={handleEdit} 
          onDelete={handleDelete} 
        />
      )}

      {isModalOpen && (
        <AddProductModal 
          onClose={() => setIsModalOpen(false)}
          onSave={handleSaveProduct}
        />
      )}
    </div>
  );
};

export default DashboardInventoryPage;