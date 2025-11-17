import React, { useState } from 'react';
import styles from './DashboardInventoryPage.module.css';
import InventoryControls from '../../../auth/components/profile/producer/producer-inventory/InventoryControls';
import InventoryTable from '../../../auth/components/profile/producer/producer-inventory/InventoryTable';
import AddProductModal from '../../../auth/components/profile/producer/producer-inventory/AddProductModal'

// Lista de produtos inicial 
const mockProdutosIniciais = [
  { id: 1, nome: 'Tomate', idProduto: '#QWER1235', categoria: 'Frutas', estoque: 120 },
  { id: 2, nome: 'Cebola', idProduto: '#QWER1236', categoria: 'Vegetais', estoque: 100 },
  { id: 3, nome: 'Banana da terra', idProduto: '#QWER1237', categoria: 'Frutas', estoque: 15 },
];

/**
 * Esta é a página principal que gerencia o estado do inventário.
 * Ela controla a lista de produtos e a abertura/fechamento do modal.
 */
const DashboardInventoryPage = () => {
  // Estado para a lista de produtos que é exibida na tabela
  const [produtos, setProdutos] = useState(mockProdutosIniciais);
  
  // Estado para controlar se o modal (pop-up) está aberto ou fechado
  const [isModalOpen, setIsModalOpen] = useState(false);
  
  // Estado para guardar o produto que está sendo editado.
  const [produtoParaEditar, setProdutoParaEditar] = useState(null);


  /**
   * Chamada quando o usuário clica no botão "Editar" na tabela.
   */
  const handleEdit = (id) => {
    //  Acha o produto na lista pelo ID
    const produto = produtos.find(p => p.id === id);
    if (produto) {
      //  Coloca ele no estado de "edição"
      setProdutoParaEditar(produto);
      //  Abre o modal
      setIsModalOpen(true);
    }
  };

  /**
   * Chamada quando o usuário clica no botão "Excluir" (lixeira) na tabela.
   */
  const handleDelete = (id) => {
    // Pede confirmação
    if (window.confirm("Tem certeza que deseja excluir este produto?")) {
      // Filtra a lista, removendo o produto com o ID clicado
      setProdutos(produtosAtuais => 
        produtosAtuais.filter(produto => produto.id !== id)
      );
    }
  };

  /**
   * Chamada quando o usuário clica no botão "Salvar" dentro do modal.
   * Esta função lida tanto com Adicionar quanto com Editar.
   */
  const handleSaveProduct = (produtoSalvo) => {
    
    // Verifica se o ID do produto salvo já existe na lista
    const index = produtos.findIndex(p => p.id === produtoSalvo.id);

    if (index !== -1) {
      // Cria uma nova lista, substituindo o produto antigo pelo novo
      setProdutos(produtosAtuais => 
        produtosAtuais.map(p => 
          p.id === produtoSalvo.id ? produtoSalvo : p
        )
      );
    } else {
      // Adiciona o novo produto ao final da lista
      setProdutos(produtosAtuais => 
        [...produtosAtuais, produtoSalvo]
      );
    }
    
    handleCloseModal(); // Fecha o modal e limpa o estado
  };

  /**
   * Chamada quando o usuário clica no botão "Adicionar Novo Produto".
   */
  const handleAddProduct = () => {
    // Garante que não estamos em modo de edição
    setProdutoParaEditar(null); 
    //  Abre o modal
    setIsModalOpen(true);
  };
  
  /**
   * Função auxiliar para fechar o modal e limpar o estado de edição.
   */
  const handleCloseModal = () => {
    setIsModalOpen(false);
    setProdutoParaEditar(null);
  };

  return (
    <div className={styles.wrapper}>
      {/* Componente de Controles (Título, Botão Adicionar, Busca) */}
      <InventoryControls 
        onAddProduct={handleAddProduct}
      />
      
      {/* Componente da Tabela (Lista os produtos) */}
      <InventoryTable 
        produtos={produtos}         // Passa a lista de produtos do estado
        onEdit={handleEdit}         // Passa a função de editar
        onDelete={handleDelete}     // Passa a função de excluir
      />

      {/* Renderização Condicional do Modal:
        O modal só é renderizado (e exibido) se 'isModalOpen' for true.
      */}
      {isModalOpen && (
        <AddProductModal 
          onClose={handleCloseModal}
          onSave={handleSaveProduct}
          // Passa o produto para o modal.
          produtoInicial={produtoParaEditar} 
        />
      )}
    </div>
  );
};

export default DashboardInventoryPage;