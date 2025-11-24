import { useState, useEffect } from "react";
import styles from "./ProducerProductRequestPage.module.css";

function ProducerProductRequestPage() {
  const [showRequested, setShowRequested] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Função para formatar a data
  const formatDate = (dateString) => {
    if (!dateString) return "";
    const date = new Date(dateString);
    return date.toLocaleDateString("pt-BR");
  };

  // Função para buscar as ofertas do produtor
  const fetchProducerOffers = async () => {
    try {
      setLoading(true);
      const token = localStorage.getItem("token");
      
      const response = await fetch("/api/v1/ofertas-produtor/me", {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error(`Erro: ${response.status}`);
      }

      const offers = await response.json();
      
      // Usando apenas os campos que vêm da API, sem lógica de status
      const mappedProducts = offers.map(offer => ({
        id: offer.id,
        metaEstoqueId: offer.metaEstoqueId,
        nomeProduto: offer.nomeProduto,
        produtorId: offer.produtorId,
        quantidadeOfertada: offer.quantidadeOfertada,
        quantidadeDisponivel: offer.quantidadeDisponivel,
        totalVolumeVendido: offer.totalVolumeVendido,
        status: offer.status,
        criadoEm: offer.criadoEm,
      }));

      setProducts(mappedProducts);
    } catch (err) {
      setError(err.message);
      console.error("Erro ao buscar ofertas:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProducerOffers();
  }, []);

  // Filtro baseado apenas no status real do backend
  const filteredProducts = products.filter(
    (p) =>
      p.nomeProduto.toLowerCase().includes(searchTerm.toLowerCase()) &&
      (showRequested ? p.status !== "PENDENTE" : p.status === "PENDENTE")
  );

  const handleRequest = async (id) => {
    try {
      const productToUpdate = products.find(p => p.id === id);
      const token = localStorage.getItem("token");
      
      // Fazendo o POST para confirmar a oferta
      const response = await fetch(`/api/v1/ofertas-produtor/me/confirmar/${productToUpdate.metaEstoqueId}`, {
        method: "POST",
        headers: {
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error(`Erro ao confirmar: ${response.status}`);
      }

      // Recarrega os dados do backend para ter status atualizado
      await fetchProducerOffers();

      console.log(`Produto ${id} confirmado com sucesso!`);

    } catch (err) {
      console.error("Erro ao confirmar produto:", err);
      alert("Erro ao confirmar produto. Tente novamente.");
    }
  };

  if (loading) {
    return <div className={styles.container}>Carregando...</div>;
  }

  if (error) {
    return (
      <div className={styles.container}>
        <p>Erro: {error}</p>
        <button onClick={fetchProducerOffers}>Tentar novamente</button>
      </div>
    );
  }

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
            {/* Imagem padrão já que não vem da API */}
            <img
              src="/images/abacaxi.jpg"
              alt={product.nomeProduto}
              className={styles.productImage}
            />

            <div className={styles.productInfo}>
              <h2>{product.nomeProduto}</h2>
              {/* Apenas quantidade ofertada e data */}
              <p className={styles.quantity}>Quantidade: {product.quantidadeOfertada}</p>
              <p className={styles.date}>Data: {formatDate(product.criadoEm)}</p>
            </div>

            <div className={styles.productAction}>
              <label>Confirmar produto para venda</label>

              {product.status === "PENDENTE" ? (
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