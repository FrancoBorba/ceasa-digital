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
        
        const token = localStorage.getItem("access_token");
        
        // 1. 🛑 VERIFICAÇÃO DE TOKEN: Impede a requisição se o token não existir.
        if (!token) {
            console.error("Erro: Token de autenticação não encontrado no localStorage.");
            // Define uma mensagem de erro clara para o usuário
            throw new Error("Sessão expirada ou não autenticada. Por favor, faça login novamente.");
        }

        const response = await fetch("http://localhost:8080/api/v1/ofertas-produtor/me", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json",
            },
        });

        if (!response.ok) {
            // 2. ❌ TRATAMENTO DE ERRO ROBUSTO: Tenta ler a resposta como texto (para HTML/erro do servidor).
            // Apenas se a resposta não for 401 (Unauthorized), que pode ser tratada de forma diferente.
            let errorDetails = response.statusText;

            // Tenta obter uma mensagem de erro mais detalhada do corpo da resposta
            try {
                // Tenta ler o corpo como JSON primeiro (se a API retornar um erro JSON)
                const errorJson = await response.json();
                errorDetails = errorJson.message || JSON.stringify(errorJson);
            } catch {
                // Se falhar (corpo não é JSON, ex: é HTML), tenta ler como texto
                const errorText = await response.text();
                // Usa o texto, mas limita o tamanho para não poluir o console com HTML gigante
                errorDetails = errorText.substring(0, 200) + '...'; 
            }
            
            // Lança o erro com o status HTTP e a mensagem detalhada
            throw new Error(`Falha na busca de ofertas: Status ${response.status}. Detalhes: ${errorDetails}`);
        }

        // Se a resposta for OK (200-299), ela DEVE ser JSON.
        const offers = await response.json();
        
        // Mapeamento dos produtos (mantido como estava)
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
        // O `catch` agora recebe o erro mais específico que lançamos acima
        setError(err.message);
        console.error("Erro ao buscar ofertas:", err.message);
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
      const token = localStorage.getItem("access_token");
      
      // Fazendo o POST para confirmar a oferta
      const response = await fetch(`http://localhost:8080/api/v1/ofertas-produtor/me/confirmar/${productToUpdate.metaEstoqueId}`, {
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