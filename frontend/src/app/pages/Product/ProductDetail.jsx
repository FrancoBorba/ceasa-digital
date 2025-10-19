import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getProductById } from "./services/productService";
import { addItemToCart } from "../Cart/services/cartService";

export default function ProductDetail() {
  const { id } = useParams(); // Captura o ID da URL
  const navigate = useNavigate(); // Navegar entre paginas

  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState("");

  useEffect(() => {
    async function fetchProduct() {
      try {
        const data = await getProductById(id);
        setProduct(data);
      } catch (error) {
        console.error("Erro ao buscar produto:", error);
      } finally {
        setLoading(false);
      }
    }
    fetchProduct();
  }, [id]);

  const handleAddToCart = async () => {
    try {
        const itemRequest = {
        produtoID: product.id, // ou 'id' dependendo do backend
        quantidade: 1,
        };

        await addItemToCart(itemRequest);
        setMessage("Produto adicionado ao carrinho!");
        setTimeout(() => setMessage(""), 2000); 
    } catch (error) {
        console.error("Erro ao adicionar produto ao carrinho:", error);
        setMessage("Produto adicionado ao carrinho!");
        setTimeout(() => setMessage(""), 2000); 
    }
};


  if (loading) {
    return <p className="text-center text-xl mt-8">Carregando produto...</p>;
  }

  if (!product) {
    return <p className="text-center text-xl mt-8 text-red-600">Produto não encontrado.</p>;
  }

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex flex-col items-center">
      {/* Botao de voltar */}
      <button
        onClick={() => navigate("/")}
        className="self-start mb-6 px-4 py-2 bg-gray-200 hover:bg-gray-300 rounded-lg text-gray-800 transition-all"
      >
        Voltar
      </button>

      {/* Card do produto */}
      <div className="bg-white shadow-lg rounded-2xl p-6 max-w-lg w-full text-center">
        <img
          src={`http://localhost:8080${product.fotoUrl}`}
          alt={product.nome}
          className="w-full h-64 object-cover rounded-xl mb-4"
        />

        <h1 className="text-2xl font-bold text-gray-900 mb-2">{product.nome}</h1>
        <p className="text-gray-700 mb-4">{product.descricao}</p>

        <span className="text-green-600 text-2xl font-semibold block mb-6">
          R$ {product.preco?.toFixed(2)}
        </span>

        <div className="flex justify-between mb-4">
            <button
            className="bg-green-600 text-white px-6 py-2 rounded-lg hover:bg-green-700 transition-colors"
            onClick={() => handleAddToCart()}
            >
            Adicionar ao Carrinho
            </button>
            <button
            className="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-800 transition-colors"
            onClick={() => navigate("/cart")}
            >
            Ir para o Carrinho
            </button>
        </div>
        {message}{/* Permissao da mensagem aparecer na tela */}
      </div>
    </div>
  );
}