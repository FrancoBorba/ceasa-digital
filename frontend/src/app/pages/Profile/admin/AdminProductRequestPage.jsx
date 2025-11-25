import { useState, useEffect } from "react";
import styles from "./AdminProductRequestPage.module.css";
import api from "../../auth/services/apiRequester";

export default function AdminProductRequestPage() {
  const [showRequested, setShowRequested] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [products, setProducts] = useState([]);
  const BACKEND_URL = "http://localhost:8080";

  // Função para formatar data e hora
  const formatDate = (dateString) => {
    if (!dateString) return "";
    const date = new Date(dateString);
    return date.toLocaleString("pt-BR", {
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    });
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const productsResponse = await api.get("/api/v1/products");
        const allProducts = productsResponse.data.content || [];

        if (showRequested) {
          // --- LÓGICA DA ABA "SOLICITADOS" ---
          // Busca as metas e mostra CADA META como um item na lista.
          // Isso permite que um mesmo produto apareça múltiplas vezes se tiver N metas.
          const metasResponse = await api.get("/api/v1/metas-estoque");
          const metas = metasResponse.data || [];

          const mappedMetas = metas
            .map((meta) => {
              const prod = allProducts.find((p) => p.id === meta.produtoId);

              // Se por acaso a meta aponta para um produto que não veio na lista, ignoramos (segurança)
              if (!prod) return null;

              return {
                uniqueKey: `meta-${meta.id}`, // Chave única para o React (baseada na Meta)
                id: prod.id, // ID do Produto (para referências)
                metaId: meta.id, // ID da Meta (fundamental para edição)
                name: prod.nome,
                category:
                  prod.categories && prod.categories.length > 0
                    ? prod.categories[0].name // Pega a primeira categoria
                    : "Geral",
                image: BACKEND_URL + prod.fotoUrl || "/images/abacaxi.jpg",
                // Estado visual
                requested: true, // Na aba solicitados, o item já é uma solicitação
                quantity: meta.quantidadeMeta,
                requestDate: meta.criadoEm,
                unit: "Kg",
                isEditing: false,
              };
            })
            .filter(Boolean); // Remove nulos

          setProducts(mappedMetas);
        } else {
          // --- LÓGICA DA ABA "TODOS" ---
          // Mostra apenas o catálogo de produtos para CRIAR NOVA solicitação.
          // Ignora se já existe meta ou não. O objetivo é criar uma nova (POST).
          const mappedCatalog = allProducts.map((prod) => {
            return {
              uniqueKey: `prod-${prod.id}`, // Chave única baseada no Produto
              id: prod.id,
              metaId: null, // Não tem meta associada, é uma criação nova
              name: prod.nome,
              category:
                prod.categories && prod.categories.length > 0
                  ? prod.categories[0].name // Pega a primeira categoria
                  : "Geral",
              image: BACKEND_URL + prod.fotoUrl || "/images/abacaxi.jpg",

              // Estado visual
              requested: false, // Força modo de "criação" (aparece o input)
              quantity: "", // Começa vazio para o usuário digitar
              requestDate: null,
              unit: "Kg",
              isEditing: false,
            };
          });

          setProducts(mappedCatalog);
        }
      } catch (error) {
        console.error("Erro ao buscar dados:", error);
      }
    };

    fetchData();
  }, [showRequested]); // Recarrega tudo quando muda a aba

  // Filtro de pesquisa visual (apenas busca pelo nome no array carregado)
  const filteredProducts = products.filter((p) =>
    p.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handleQuantityChange = (uniqueKey, value) => {
    setProducts((prev) =>
      prev.map((p) =>
        p.uniqueKey === uniqueKey ? { ...p, quantity: value } : p
      )
    );
  };

  const handleUnitChange = (uniqueKey, value) => {
    setProducts((prev) =>
      prev.map((p) => (p.uniqueKey === uniqueKey ? { ...p, unit: value } : p))
    );
  };

  // Alterado para receber o uniqueKey para identificar o item correto na lista
  const handleRequest = async (uniqueKey) => {
    const product = products.find((p) => p.uniqueKey === uniqueKey);

    if (!product.quantity || Number(product.quantity) <= 0) {
      alert("Por favor, insira uma quantidade válida.");
      return;
    }

    const payload = {
      produtoId: product.id,
      quantidadeMeta: Number(product.quantity),
    };

    try {
      if (showRequested && product.metaId) {
        // --- PUT: Atualizar meta existente (Aba Solicitados) ---
        await api.put(`/api/v1/metas-estoque/${product.metaId}`, payload);

        setProducts((prev) =>
          prev.map((p) =>
            p.uniqueKey === uniqueKey ? { ...p, isEditing: false } : p
          )
        );
        alert("Meta atualizada com sucesso!");
      } else {
        // --- POST: Criar nova meta (Aba Todos) ---
        await api.post("/api/v1/metas-estoque", payload);

        // Na aba "Todos", após criar, nós limpamos o campo para permitir nova criação
        // Não mudamos para 'requested: true' porque aqui é o catálogo de criação.
        setProducts((prev) =>
          prev.map((p) =>
            p.uniqueKey === uniqueKey
              ? { ...p, quantity: "" } // Limpa o input
              : p
          )
        );
        alert("Solicitação criada com sucesso!");
      }
    } catch (error) {
      console.error("Erro ao salvar meta:", error);
      alert(
        "Erro ao salvar a solicitação. " +
          (error.response?.data?.message || error.message)
      );
    }
  };

  const handleEdit = (uniqueKey) => {
    setProducts((prev) =>
      prev.map((p) =>
        p.uniqueKey === uniqueKey ? { ...p, isEditing: true } : p
      )
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
            {showRequested ? "Solicitados (Metas Ativas)" : "Todos (Catálogo)"}
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
          <div key={product.uniqueKey} className={styles.productCard}>
            <img
              src={product.image}
              alt={product.name}
              className={styles.productImage}
            />

            <div className={styles.productInfo}>
              <h2>{product.name}</h2>
              <p>{product.category}</p>

              {/* Exibe info apenas se for um item JÁ solicitado (Aba Solicitados) e não estiver editando */}
              {product.requested && !product.isEditing && (
                <div className={styles.requestInfo}>
                  <p>
                    <strong>Quantidade:</strong> {product.quantity}{" "}
                    {product.unit}
                  </p>
                  <p>
                    <strong>Criado em:</strong>{" "}
                    {formatDate(product.requestDate)}
                  </p>
                </div>
              )}
            </div>

            <div className={styles.productAction}>
              {/* Se estiver na aba TODOS (!requested) OU estiver EDITANDO na aba solicitados */}
              {!product.requested || product.isEditing ? (
                <>
                  <label>
                    {product.isEditing
                      ? "Editar Quantidade"
                      : "Criar nova meta"}
                  </label>
                  <div className={styles.actionRow}>
                    <select
                      className={styles.select}
                      value={product.unit}
                      onChange={(e) =>
                        handleUnitChange(product.uniqueKey, e.target.value)
                      }
                    >
                      <option value="Kg">Kg</option>
                      <option value="Unidade">Unidade</option>
                      <option value="Caixa">Caixa</option>
                      <option value="Pacote">Pacote</option>
                    </select>
                    <input
                      type="number"
                      placeholder="Qtd"
                      value={product.quantity}
                      onChange={(e) =>
                        handleQuantityChange(product.uniqueKey, e.target.value)
                      }
                      className={styles.quantityInput}
                    />
                    <button
                      className={styles.button}
                      onClick={() => handleRequest(product.uniqueKey)}
                    >
                      {product.isEditing ? "Salvar" : "Solicitar"}
                    </button>
                  </div>
                </>
              ) : (
                // Botão Editar apenas na aba Solicitados
                <div
                  className={styles.actionRow}
                  style={{ justifyContent: "flex-end" }}
                >
                  <button
                    className={`${styles.button} ${styles.editButton}`}
                    onClick={() => handleEdit(product.uniqueKey)}
                  >
                    Editar Meta
                  </button>
                </div>
              )}
            </div>
          </div>
        ))}

        {filteredProducts.length === 0 && (
          <p className={styles.emptyMessage}>Nenhum item encontrado.</p>
        )}
      </div>
    </div>
  );
}
