import React, { useState } from 'react';
import styles from './AdminProductEvaluationPage.module.css';
import { MagnifyingGlassIcon } from '@heroicons/react/24/solid';
import ProductEvaluationList from '../../auth/components/profile/admin/ProductEvaluationList';
// Mock data (Produtos pendentes de avaliação)

const mockProdutosPendentes = [
  { id: 101, nome: 'Banana', categoria: 'Frutas', 'nome do produtor': '', img: 'https://via.placeholder.com/40x40/FFEB3B/000000?text=B' },
  { id: 102, nome: 'Brócolis', categoria: 'Verduras', 'nome do produtor': '', img: 'https://via.placeholder.com/40x40/4CAF50/FFFFFF?text=B' },
  { id: 103, nome: 'Manga', categoria: 'Frutas', 'nome do produtor': '', img: 'https://via.placeholder.com/40x40/FF9800/FFFFFF?text=M' },
  { id: 104, nome: 'Abacate', categoria: 'Frutas', 'nome do produtor': '', img: 'https://via.placeholder.com/40x40/8BC34A/FFFFFF?text=A' },
  { id: 105, nome: 'Maçã', categoria: 'Frutas', 'nome do produtor': '', img: 'https://via.placeholder.com/40x40/F44336/FFFFFF?text=M' },
  { id: 106, nome: 'Couve', categoria: 'Verdura', 'nome do produtor': '', img: 'https://via.placeholder.com/40x40/2E7D32/FFFFFF?text=C' },
];


const AdminProductEvaluationPage = () => {
  // Estado para a lista de produtos pendentes
  const [produtos, setProdutos] = useState(mockProdutosPendentes);

  /**
   * História de Usuário: "Confirmar cada produto"
   * Esta função remove o produto da lista de pendências (simulando a aprovação)
   */
  const handleApprove = (id) => {
    console.log(`Produto ${id} APROVADO`);
    // Filtra a lista, removendo o item que foi avaliado
    setProdutos(produtosAtuais =>
      produtosAtuais.filter(p => p.id !== id)
    );
    // Próximo passo: chamar a API (ex: POST /api/admin/products/approve/${id})
  };

  /**
   * Esta função também remove o produto da lista (simulando a negação)
   */
  const handleDeny = (id) => {
    console.log(`Produto ${id} NEGADO`);
    // Filtra a lista, removendo o item que foi avaliado
    setProdutos(produtosAtuais =>
      produtosAtuais.filter(p => p.id !== id)
    );
  };


  return (
    <div className={styles.wrapper}>
      <header className={styles.header}>
        <div>
          <h1 className={styles.title}>Lista de produtos</h1>
          <p className={styles.breadcrumb}>Dashboard / Lista de Prod...</p>
        </div>
        <div className={styles.searchBar}>
          <MagnifyingGlassIcon />
          <input type="text" placeholder="Pesquisar..." />
        </div>
      </header>
      
      <ProductEvaluationList 
        produtos={produtos}
        onApprove={handleApprove}
        onDeny={handleDeny}
      />
    </div>
  );
};

export default AdminProductEvaluationPage;