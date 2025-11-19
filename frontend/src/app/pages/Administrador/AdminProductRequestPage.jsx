import { useState, useEffect } from "react";
import styles from "./AdminProductRequestPage.module.css";
import api from "../auth/services/apiRequester"; // Importando o serviço de API existente

export default function AdminProductRequestPage() {
  const [showRequested, setShowRequested] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [products, setProducts] = useState([]);
  const BACKEND_URL = "http://localhost:8080";

  // Função para carregar os dados
  useEffect(() => {
    const fetchData = async () => {
      try {
        // Busca todos os produtos (catálogo)
        const productsResponse = await api.get("/api/v1/products");
        const allProducts = productsResponse.data.content || [];

        // Busca todas as metas de estoque já definidas
        const metasResponse = await api.get("/api/v1/metas-estoque");
        const metas = metasResponse.data || [];

        // Mapeia os produtos mesclando com as informações das metas
        const mappedProducts = allProducts.map((prod) => {
          // Tenta encontrar uma meta para este produto
          const meta = metas.find((m) => m.produtoId === prod.id);

          return {
            id: prod.id, // ID do produto
            metaId: meta ? meta.id : null, // ID da meta (necessário para o PUT)
            name: prod.nome,
            category:
              prod.categories && prod.categories.length > 0
                ? prod.categories[0].nome
                : "Geral",
            image: BACKEND_URL + prod.fotoUrl || "/images/abacaxi.jpg", // Fallback para manter o design
            requested: !!meta, // Se tem meta, está solicitado
            quantity: meta ? meta.quantidadeMeta : "",
            isEditing: false,
          };
        });

        // Filtra com base no switch (Solicitados vs Disponíveis) e atualiza o estado
        // O filtro de visualização final será feito no filteredProducts,
        // mas aqui garantimos que temos todos os dados carregados.
        setProducts(mappedProducts);
      } catch (error) {
        console.error("Erro ao buscar dados:", error);
      }
    };

    fetchData();
  }, [showRequested]); // Recarrega ao mudar o switch, conforme solicitado ("quando ativar o switch, vai ter outro GET")

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

  const handleRequest = async (id) => {
    const product = products.find((p) => p.id === id);

    // Validação básica
    if (!product.quantity || Number(product.quantity) <= 0) {
      setProducts((prev) =>
        prev.map((p) =>
          p.id === id ? { ...p, requested: false, isEditing: false } : p
        )
      );
      return;
    }

    // Lógica do PUT (Apenas para edição de metas existentes)
    if (product.requested && product.metaId) {
      try {
        const payload = {
          produtoId: product.id,
          quantidadeMeta: Number(product.quantity),
        };

        // PUT /api/v1/metas-estoque/{id}
        await api.put(`/api/v1/metas-estoque/${product.metaId}`, payload);

        // Atualiza estado local após sucesso
        setProducts((prev) =>
          prev.map((p) => (p.id === id ? { ...p, isEditing: false } : p))
        );
      } catch (error) {
        console.error("Erro ao atualizar meta:", error);
        alert("Erro ao atualizar a meta." + error);
      }
    } else {
      setProducts((prev) =>
        prev.map((p) =>
          p.id === id ? { ...p, requested: true, isEditing: false } : p
        )
      );
    }
  };

  const handleEdit = (id) => {
    setProducts((prev) =>
      prev.map((p) => (p.id === id ? { ...p, isEditing: true } : p))
    );
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
            </div>

            <div className={styles.productAction}>
              <label>Solicitar produto para venda</label>
              {!product.requested || product.isEditing ? (
                <div className={styles.actionRow}>
                  <select className={styles.select}>
                    <option>Kg</option>
                    <option>Unidade</option>
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
