import { useState } from "react";
import styles from "./AdminProductRequestPage.module.css";

export default function AdminProductRequestPage() {
  const [showRequested, setShowRequested] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");

  const [products, setProducts] = useState([
    { 
      id: 1, 
      name: "Abacaxi", 
      category: "Frutas", 
      image: "/images/abacaxi.jpg", 
      requested: false, 
      quantity: "", 
      unit: "Kg",
      requestDate: "",
      isEditing: false 
    },
    { 
      id: 2, 
      name: "Manga", 
      category: "Frutas", 
      image: "/images/manga.jpg", 
      requested: true, 
      quantity: "20", 
      unit: "Kg",
      requestDate: "2024-01-15",
      isEditing: false 
    },
    { 
      id: 3, 
      name: "Banana", 
      category: "Frutas", 
      image: "/images/banana.jpg", 
      requested: false, 
      quantity: "", 
      unit: "Kg",
      requestDate: "",
      isEditing: false 
    },
  ]);

  const filteredProducts = products.filter(
    (p) =>
      p.name.toLowerCase().includes(searchTerm.toLowerCase()) &&
      (showRequested ? p.requested : !p.requested)
  );

  const handleQuantityChange = (id, value) => {
    setProducts((prev) =>
      prev.map((p) => (p.id === id ? { ...p, quantity: value } : p))
    );
  };

  const handleUnitChange = (id, value) => {
    setProducts((prev) =>
      prev.map((p) => (p.id === id ? { ...p, unit: value } : p))
    );
  };

  const handleRequest = (id) => {
    setProducts((prev) =>
      prev.map((p) => {
        if (p.id === id && (!p.quantity || Number(p.quantity) <= 0)) {
          return { ...p, requested: false, isEditing: false };
        }
        return p.id === id
          ? { 
              ...p, 
              requested: true, 
              isEditing: false,
              requestDate: new Date().toISOString().split('T')[0] // Data atual no formato YYYY-MM-DD
            }
          : p;
      })
    );
  };

  const handleEdit = (id) => {
    setProducts((prev) =>
      prev.map((p) =>
        p.id === id ? { ...p, isEditing: true } : p
      )
    );
  };

  // Função para formatar a data para exibição
  const formatDate = (dateString) => {
    if (!dateString) return "";
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR');
  };

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Solicitar Produtos</h1>

      {/* Barra superior com switch e pesquisa */}
      <div className={styles.topBar}>
        <div className={styles.switchContainer}>
          <label className={styles.switch}>
            <input
              type="checkbox"
              checked={showRequested}
              onChange={() => setShowRequested(!showRequested)}
            />
            <span className={styles.slider}></span>
          </label>
          <span className={styles.switchLabel}>
            {showRequested ? "Solicitados" : "Disponíveis"}
          </span>
        </div>

        <input
          type="text"
          placeholder="Pesquisar produto..."
          className={styles.searchInput}
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      {/* Lista de produtos */}
      <div className={styles.productList}>
        {filteredProducts.map((product) => (
          <div key={product.id} className={styles.productCard}>
            <img
              src={product.image}
              alt={product.name}
              className={styles.productImage}
            />

            <div className={styles.productInfo}>
              <h2>{product.name}</h2>
              <p>{product.category}</p>
              
              {/* Exibir informações de solicitação quando o produto estiver solicitado */}
              {product.requested && !product.isEditing && (
                <div className={styles.requestInfo}>
                  <p><strong>Quantidade:</strong> {product.quantity} {product.unit}</p>
                  <p><strong>Data de Solicitação:</strong> {formatDate(product.requestDate)}</p>
                </div>
              )}
            </div>

            <div className={styles.productAction}>
              <label>Solicitar produto para venda</label>
              {!product.requested || product.isEditing ? (
                <div className={styles.actionRow}>
                  <select 
                    className={styles.select}
                    value={product.unit}
                    onChange={(e) => handleUnitChange(product.id, e.target.value)}
                  >
                    <option value="Kg">Kg</option>
                    <option value="Unidade">Unidade</option>
                    <option value="Caixa">Caixa</option>
                    <option value="Pacote">Pacote</option>
                  </select>
                  <input
                    type="number"
                    placeholder="Digite a quantidade"
                    value={product.quantity}
                    onChange={(e) =>
                      handleQuantityChange(product.id, e.target.value)
                    }
                    className={styles.quantityInput}
                  />
                  <button
                    className={styles.button}
                    onClick={() => handleRequest(product.id)}
                  >
                    {product.isEditing ? "Salvar" : "Solicitar"}
                  </button>
                </div>
              ) : (
                <button
                  className={`${styles.button} ${styles.editButton}`}
                  onClick={() => handleEdit(product.id)}
                >
                  Editar
                </button>
              )}
            </div>
          </div>
        ))}

        {filteredProducts.length === 0 && (
          <p className={styles.emptyMessage}>Nenhum produto encontrado.</p>
        )}
      </div>
    </div>
  );
}