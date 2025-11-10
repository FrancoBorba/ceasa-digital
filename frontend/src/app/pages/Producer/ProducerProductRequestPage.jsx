import { useState } from "react";
import styles from "./ProducerProductRequestPage.module.css";

function ProducerProductRequestPage() {
  const [showRequested, setShowRequested] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");

  const [products, setProducts] = useState([
    { id: 1, name: "Abacaxi", category: "Frutas", image: "/images/abacaxi.jpg", requested: false, weight: "50 Kg" },
    { id: 2, name: "Manga", category: "Frutas", image: "/images/manga.jpg", requested: true, weight: "20 Kg" },
    { id: 3, name: "Banana", category: "Frutas", image: "/images/banana.jpg", requested: false, weight: "30 Kg" },
  ]);

  const filteredProducts = products.filter(
    (p) =>
      p.name.toLowerCase().includes(searchTerm.toLowerCase()) &&
      (showRequested ? p.requested : !p.requested)
  );

  const handleRequest = (id) => {
    setProducts((prev) =>
      prev.map((p) => {
        if (p.id === id) {
          return { ...p, requested: true };
        }
        return p;
      })
    );
  };

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Solicitar Produtos</h1>

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
            {showRequested ? "Confirmados" : "Disponíveis"}
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
              <p className={styles.weight}>Peso: {product.weight}</p>
            </div>

            <div className={styles.productAction}>
              <label>Confirmar produto para venda</label>

              {!product.requested ? (
                <div className={styles.actionRow}>
                  <button
                    className={styles.button}
                    onClick={() => handleRequest(product.id)}
                  >
                    Confirmar
                  </button>
                </div>
              ) : (
                <div className={styles.confirmedBox}>
                  Confirmado
                </div>
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

export default ProducerProductRequestPage;