import React from 'react';
import styles from './ProducerInventoryPage.module.css';
import InventoryControls from '../../../auth/components/profile/producer/producer-inventory/InventoryControls';
import InventoryTable from '../../../auth/components/profile/producer/producer-inventory/InventoryTable';

const ProducerInventoryPage = () => {
 
  const mockProdutos = [
    { id: 1, nome: 'Tomate', idProduto: '#QWER1235', categoria: 'Frutas', estoque: 120 },
    { id: 2, nome: 'Tomate', idProduto: '#QWER1235', categoria: 'Frutas', estoque: 120 },
    { id: 3, nome: 'Tomate', idProduto: '#QWER1235', categoria: 'Frutas', estoque: 120 },
    { id: 4, nome: 'Tomate', idProduto: '#QWER1235', categoria: 'Frutas', estoque: 120 },
    { id: 5, nome: 'Tomate', idProduto: '#QWER1235', categoria: 'Frutas', estoque: 120 },
  ];

  const handleAddProduct = () => {
    // História de usuário: "adicionar os produtos que vendo"
    console.log("Abrir modal ou navegar para a página de adição de produto.");
  };

  const handleEdit = (id) => {
    // História de usuário: "gerenciar meus produtos"
    console.log(`Editar produto com ID: ${id}`);
  };

  const handleDelete = (id) => {
    // História de usuário: "gerenciar meus produtos"
    console.log(`Excluir produto com ID: ${id}`);
  };

  return (
    <div className={styles.wrapper}>
      <InventoryControls onAddProduct={handleAddProduct} />
      <InventoryTable 
        produtos={mockProdutos} 
        onEdit={handleEdit} 
        onDelete={handleDelete} 
      />
    </div>
  );
};

export default ProducerInventoryPage;