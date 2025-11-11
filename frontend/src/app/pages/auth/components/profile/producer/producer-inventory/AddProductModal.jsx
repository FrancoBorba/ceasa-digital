import React, { useState, useEffect } from 'react';
import styles from './AddProductModal.module.css';
import { XMarkIcon } from '@heroicons/react/24/solid';
const AddProductModal = ({ onSave, onClose, produtoInicial }) => {

  const isEditMode = Boolean(produtoInicial);
  const modalTitle = isEditMode ? "Editar Produto" : "Adicionar Novo Produto";
  const saveButtonText = isEditMode ? "Salvar Alterações" : "Salvar Produto";

  const [nome, setNome] = useState('');
  const [idProduto, setIdProduto] = useState('');
  const [categoria, setCategoria] = useState('');
  const [estoque, setEstoque] = useState(0);

    useEffect(() => {
    if (isEditMode) {
      setNome(produtoInicial.nome);
      setIdProduto(produtoInicial.idProduto);
      setCategoria(produtoInicial.categoria);
      setEstoque(produtoInicial.estoque);
    }
    }, [produtoInicial, isEditMode]);

  const handleSubmit = (e) => {
    e.preventDefault(); 
    
    if (!nome || !idProduto || !categoria || estoque <= 0) {
      alert("Por favor, preencha todos os campos.");
      return;
    }

    const produtoSalvo = {

      id: isEditMode ? produtoInicial.id : Date.now(), 
      nome,
      idProduto,
      categoria,
      estoque: parseInt(estoque, 10),
    };
    onSave(produtoSalvo);
    
  };

  

  return (
    <div className={styles.overlay} onClick={onClose}>
      <div className={styles.modalContent} onClick={(e) => e.stopPropagation()}>
        <header className={styles.header}>
          <h2>Adicionar Novo Produto</h2>
          <button onClick={onClose} className={styles.closeButton}>
            <XMarkIcon />
          </button>
        </header>
        
        <form onSubmit={handleSubmit} className={styles.form}>
          <div className={styles.formGroup}>
            <label htmlFor="nome">Nome do Produto</label>
            <input 
              id="nome" 
              type="text" 
              value={nome}
              onChange={(e) => setNome(e.target.value)}
            />
          </div>

          <div className={styles.formGroup}>
            <label htmlFor="idProduto">ID do Produto (SKU)</label>
            <input 
              id="idProduto" 
              type="text" 
              value={idProduto}
              onChange={(e) => setIdProduto(e.target.value)}
            />
          </div>

          <div className={styles.formRow}>
            <div className={styles.formGroup}>
              <label htmlFor="categoria">Categoria</label>
              <input 
                id="categoria" 
                type="text" 
                value={categoria}
                onChange={(e) => setCategoria(e.target.value)}
              />
            </div>
            <div className={styles.formGroup}>
              <label htmlFor="estoque">Estoque (Qtd)</label>
              <input 
                id="estoque" 
                type="number" 
                value={estoque}
                onChange={(e) => setEstoque(e.target.value)}
              />
            </div>
          </div>

          <footer className={styles.footer}>
            <button type="button" className={styles.btnCancel} onClick={onClose}>
              Cancelar
            </button>
            <button type="submit" className={styles.btnSave}>
              Salvar Produto
            </button>
          </footer>
        </form>
      </div>
    </div>
  );
};

export default AddProductModal;