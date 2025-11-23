import { useState, useEffect } from "react";
import styles from "./ProducerProductRequestPage.module.css";

function ProducerProductRequestPage() {
  const [showRequested, setShowRequested] = useState(false); // ATIVO vs PENDENTE
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [updatingProducts, setUpdatingProducts] = useState(new Set());

  const [products, setProducts] = useState([]);

  // Fetch dos produtos da API
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        setLoading(true);

        const response = await fetch("/api/v1/ofertas-produtor/me");

        if (!response.ok) throw new Error("Erro ao carregar produtos");

        const data = await response.json();
        setProducts(data);

      } catch (err) {
        setError(err.message);
        console.error("Erro ao buscar produtos:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  // Mapeia APENAS o que o endpoint realmente envia
  const mappedProducts = products.map((product) => ({
    id: product.id,
    nomeProduto: product.nomeProduto,
    quantidadeDisponivel: product.quantidadeDisponivel,
    status: product.status, // exatamente como vem da API
    isAtivo: product.status === "ATIVO",
    originalData: product
  }));

  // Filtrar com base no status
  const filteredProducts = mappedProducts.filter(
    (p) =>
      p.nomeProduto.toLowerCase().includes(searchTerm.toLowerCase()) &&
      (showRequested ? p.status === "ATIVO" : p.status === "PENDENTE")
  );

  // Confirmar produto (POST)
  const handleRequest = async (id) => {
    try {
      setUpdatingProducts((prev) => new Set(prev.add(id)));

      const response = await fetch(`/api/v1/ofertas-produtor/me/confirmar/${id}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" }
      });

      if (!response.ok) throw new Error("Erro ao confirmar produto");

      const updatedProduct = await response.json(); // vem atualizado

      // Atualiza o produto na lista local
      setProducts((prev) =>
        prev.map((p) => (p.id === id ? updatedProduct : p))
      );

    } catch (err) {
      console.error("Erro ao confirmar produto:", err);
      alert("Erro ao confirmar produto. Tente novamente.");
    } finally {
      setUpdatingProducts((prev) => {
        const newSet = new Set(prev);
        newSet.delete(id);
        return newSet;
      });
    }
  };

  if (loading)
    return (
      <div className={styles.container}>
        <div className={styles.loading}>Carregando produtos...</div>
      </div>
    );

  if (error)
    return (
      <div className={styles.container}>
        <div className={styles.error}>Erro: {error}</div>
        <button
          className={styles.retryButton}
          onClick={() => window.location.reload()}
        >
          Tentar Novamente
        </button>
      </div>
    );

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
            {showRequested ? "Confirmados (ATIVO)" : "Disponíveis (PENDENTE)"}
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
        {filteredProducts.map((product) => {
          const isUpdating = updatingProducts.has(product.id);
          const isConfirmed = product.status === "ATIVO";

          return (
            <div key={product.id} className={styles.productCard}>
              <div className={styles.productInfo}>
                <h2>{product.nomeProduto}</h2>
                <p>Peso: {product.quantidadeDisponivel} Kg</p>
                <p>Status: {product.status}</p>
              </div>

              <div className={styles.productAction}>
                <label>Confirmar produto para venda</label>

                {!isConfirmed ? (
                  <div className={styles.actionRow}>
                    <button
                      className={`${styles.button} ${
                        isUpdating ? styles.buttonLoading : ""
                      }`}
                      onClick={() => handleRequest(product.id)}
                      disabled={isUpdating}
                    >
                      {isUpdating ? "Confirmando..." : "Confirmar"}
                    </button>
                  </div>
                ) : (
                  <div className={styles.confirmedBox}>Confirmado</div>
                )}
              </div>
            </div>
          );
        })}

        {filteredProducts.length === 0 && (
          <p className={styles.emptyMessage}>Nenhum produto encontrado.</p>
        )}
      </div>
    </div>
  );
}

export default ProducerProductRequestPage;
