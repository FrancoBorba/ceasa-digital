import React, { useState } from 'react';
import styles from './DashboardInventoryPage.module.css';
import InventoryControls from '../../../auth/components/profile/producer/producer-inventory/InventoryControls';
import InventoryTable from '../../../auth/components/profile/producer/producer-inventory/InventoryTable';
import AddProductModal from '../../../auth/components/profile/producer/producer-inventory/AddProductModal';
import apiRequester from '../../../auth/services/apiRequester';

// Lista de produtos inicial (mock)
const mockProdutosIniciais = [
  { id: 1, nome: 'Tomate', idProduto: '#QWER1235', categoria: 'Frutas', estoque: 120 },
  { id: 2, nome: 'Cebola', idProduto: '#QWER1236', categoria: 'Vegetais', estoque: 100 },
  { id: 3, nome: 'Banana da terra', idProduto: '#QWER1237', categoria: 'Frutas', estoque: 15 },
];

const DashboardInventoryPage = () => {
  const [produtos, setProdutos] = useState(mockProdutosIniciais);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [produtoParaEditar, setProdutoParaEditar] = useState(null);


  const handleEdit = (id) => {
    const produto = produtos.find(p => p.id === id);
    if (produto) {
      setProdutoParaEditar(produto);
      setIsModalOpen(true);
    }
  };

  const handleDelete = (id) => {
    if (window.confirm("Tem certeza que deseja excluir este produto?")) {
      setProdutos(produtosAtuais => 
        produtosAtuais.filter(produto => produto.id !== id)
      );
    }
  };

  const handleSaveProduct = async (selectedProducts) => {
    try {
      // Extrai apenas os IDs dos produtos selecionados
      const produtosIds = selectedProducts.map(p => p.id);

      // Todo: Change mock value to request to some endpoint that returns producer id.
      const MOCK_PRODUTOR_ID = 102;

      const response = await apiRequester.post(
        '/api/v1/produtor-produtos/solicitar-venda',
        {
          idProdutor: MOCK_PRODUTOR_ID,
          produtosIds: produtosIds
        }
      );

      console.log('Produtos vinculados:', response.data);

      const newProducts = selectedProducts.map(product => ({
        id: product.id,
        nome: product.nome,
        idProduto: `#PROD${product.id}`,
        categoria: 'Categoria',
        estoque: 0
      }));

      // Filtra produtos que já existem
      const uniqueNewProducts = newProducts.filter(
        np => !produtos.some(p => p.id === np.id)
      );

      setProdutos(produtosAtuais => [...produtosAtuais, ...uniqueNewProducts]);
      
      alert(`${response.data.totalSolicitado} produto(s) adicionado(s) com sucesso!`);
      handleCloseModal();
    } catch (error) {
      console.error('Erro ao adicionar produtos:', error);
      alert('Erro ao adicionar produtos. Tente novamente.');
    }
  };

  const handleAddProduct = () => {
    setProdutoParaEditar(null);
    setIsModalOpen(true);
  };
  
  const handleCloseModal = () => {
    setIsModalOpen(false);
    setProdutoParaEditar(null);
  };

  return (
    <div className={styles.wrapper}>
      <InventoryControls onAddProduct={handleAddProduct} />
      
      <InventoryTable 
        produtos={produtos}
        onEdit={handleEdit}
        onDelete={handleDelete}
      />

      {isModalOpen && (
        <AddProductModal 
          onClose={handleCloseModal}
          onSave={handleSaveProduct}
          produtoInicial={produtoParaEditar}
        />
      )}
    </div>
  );
};

export default DashboardInventoryPage;
