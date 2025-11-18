import React, { useState } from "react";
import styles from "./AddProductModal.module.css";
import { XMarkIcon } from "@heroicons/react/24/solid";
import { useProducts } from "../../../../hooks/useProducts";

const AddProductModal = ({ onSave, onClose }) => {
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedProducts, setSelectedProducts] = useState([]);
  const { products, loading, error } = useProducts();

  const handleToggleProduct = (product) => {
    setSelectedProducts((prev) => {
      const isSelected = prev.some((p) => p.id === product.id);
      if (isSelected) {
        return prev.filter((p) => p.id !== product.id);
      } else {
        return [...prev, product];
      }
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (selectedProducts.length === 0) {
      alert("Por favor, selecione pelo menos um produto.");
      return;
    }

    onSave(selectedProducts.map((p) => ({ id: p.id, nome: p.nome })));
  };

  const filteredProducts = products.filter((p) => p.nome.toLowerCase().includes(searchTerm.toLowerCase()));

  return (
    <div className={styles.overlay} onClick={onClose}>
      <div className={styles.modalContent} onClick={(e) => e.stopPropagation()}>
        <header className={styles.header}>
          <h2>Adicionar Produtos ao Inventário</h2>
          <button onClick={onClose} className={styles.closeButton}>
            <XMarkIcon />
          </button>
        </header>

        <form onSubmit={handleSubmit} className={styles.form}>
          
          <div className={styles.formGroup}>
            <label htmlFor="search">Buscar Produtos</label>
            <input
              id="search"
              type="text"
              placeholder="Digite o nome do produto..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>

          {selectedProducts.length > 0 && (
            <div className={styles.formGroup}>
              <label>Produtos Selecionados ({selectedProducts.length})</label>
              <div className={styles.selectedProductsContainer}>
                {selectedProducts.map((product) => (
                  <span key={product.id} className={styles.productTag}>
                    {product.nome}
                    <button
                      type="button"
                      onClick={() => handleToggleProduct(product)}
                      className={styles.removeTagButton}
                    >
                      ×
                    </button>
                  </span>
                ))}
              </div>
            </div>
          )}

          <div className={styles.formGroup}>
            <label>Produtos Disponíveis</label>
            <div className={styles.productListContainer}>
              {loading && <p className={styles.loadingText}>Carregando produtos...</p>}
              {error && <p className={styles.errorText}>{error}</p>}
              {!loading && !error && filteredProducts.length === 0 && (
                <p className={styles.emptyText}>Nenhum produto encontrado</p>
              )}
              {!loading &&
                !error &&
                filteredProducts.map((product) => {
                  const isSelected = selectedProducts.some((p) => p.id === product.id);
                  return (
                    <div
                      key={product.id}
                      onClick={() => handleToggleProduct(product)}
                      className={`${styles.productItem} ${isSelected ? styles.productItemSelected : ""}`}
                    >
                      <span className={styles.productName}>{product.nome}</span>
                      {product.categories && product.categories.length > 0 && (
                        <span className={styles.productCategory}>({product.categories[0].name})</span>
                      )}
                    </div>
                  );
                })}
            </div>
          </div>

          <footer className={styles.footer}>
            <button type="button" className={styles.btnCancel} onClick={onClose}>
              Cancelar
            </button>
            <button type="submit" className={styles.btnSave}>
              Adicionar Produtos ({selectedProducts.length})
            </button>
          </footer>
        </form>
      </div>
    </div>
  );
};

export default AddProductModal;
