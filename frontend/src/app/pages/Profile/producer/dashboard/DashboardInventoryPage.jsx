import React, { useState, useEffect } from 'react';
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

  // Estados para gerenciar o ID do produtor e o carregamento
  const [producerId, setProducerId] = useState(null);
  const [isLoadingId, setIsLoadingId] = useState(true);
  const [errorId, setErrorId] = useState(null);

  // Efeito para buscar o ID do produtor
  useEffect(() => {
    const fetchProducerId = async () => {
      setIsLoadingId(true);
      setErrorId(null);
      try {
        // Usamos /produtor/me (Melhor prática para buscar dados do usuário logado)
        const response = await apiRequester.get(`/produtor/me`);
        
        // response.data é o ProdutorResponseDTO, que deve ter o campo 'id'
        setProducerId(response.data.id); 
        
      } catch (error) {
        console.error("Falha ao buscar ID do produtor:", error);
        setErrorId("Falha ao carregar o ID do produtor. Verifique se o perfil de produtor foi criado.");
      } finally {
        setIsLoadingId(false);
      }
    };

    fetchProducerId();
  }, []); // 🛑 CORREÇÃO: Array de dependência VAZIO para rodar APENAS na montagem.

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
    // Validação do ID do produtor antes de prosseguir
      if (isLoadingId) {
          alert("Aguarde o carregamento dos dados do produtor.");
          return;
      }
      if (errorId || !producerId) {
          alert("Erro: Não foi possível identificar o produtor. Verifique sua autenticação.");
          return;
      }
    try {
      // Extrai apenas os IDs dos produtos selecionados
      const produtosIds = selectedProducts.map(p => p.id);

      const response = await apiRequester.post(
        '/api/v1/produtor-produtos/solicitar-venda',
        {
          idProdutor: producerId,
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

    // 🛑 CORREÇÃO: Renderização condicional para carregamento/erro
    if (isLoadingId) {
        return <div className={styles.wrapper}>Carregando perfil do produtor...</div>;
    }

    if (errorId) {
        return <div className={styles.wrapper}><p style={{color: 'red'}}>Erro ao carregar dados: {errorId}</p></div>;
    }
    // Fim da correção

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