import { useState, useEffect } from "react";
import apiRequester from "../services/apiRequester";

export const useProducts = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchProducts = async () => {
      setLoading(true);
      setError(null);

      try {
        const productsToSale = await apiRequester.get("/api/v1/products", {
          params: { page: 0, size: 100, sortBy: "nome", direction: "asc" },
        });

        const alreadySolicitedToSaleProducts = await apiRequester.get("/api/v1/produtor-produtos/me", {
          params: {
            status: "",
          },
        });

        const alreadyRequestedIds = alreadySolicitedToSaleProducts.data.map((item) => item.produtoId);
        const filteredProducts = productsToSale.data.content.filter(
          (product) => !alreadyRequestedIds.includes(product.id)
        );
        setProducts(filteredProducts);
      } catch (err) {
        setError("Erro ao buscar produtos");
        console.error("Erro ao buscar produtos:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  return { products, loading, error };
};
