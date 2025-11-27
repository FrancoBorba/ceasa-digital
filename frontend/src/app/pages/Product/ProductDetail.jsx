import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getProductById } from "./services/productService";
import { addItemToCart } from "../Cart/services/cartService";
import Header from "../../layouts/header/header";
import Footer from "../../layouts/footer/footer";

export default function ProductDetail() {
  const { id } = useParams(); // Captura o ID da URL
  const navigate = useNavigate(); // Navegar entre paginas

  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState("");
  const [quantity, setQuantity] = useState(1);

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
        produtoID: product.id,
        quantidade: quantity,
        };

        await addItemToCart(itemRequest);
        setMessage("Produto adicionado ao carrinho!");
        setTimeout(() => setMessage(""), 1000); 
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
    <div className="min-h-screen bg-gray-50">
      <Header />

      <main className="max-w-6xl mx-auto p-6">
        <div className="bg-white shadow-md rounded-2xl p-6 grid grid-cols-1 md:grid-cols-2 gap-8">
          {/* Imagem do produto */}
          <div>
            <img
              src={`${import.meta.env?.VITE_API_URL || "http://localhost:8080"}${product.fotoUrl}`}
              alt={product.nome}
              className="w-full h-[360px] object-cover rounded-xl"
            />
          </div>

          {/* Informações principais */}
          <div>
            <h1 className="text-xl font-semibold text-gray-900">
              {product.nome}
            </h1>
            {/* Rating mock */}
            <div className="flex items-center gap-2 text-sm text-gray-600">
              <span>★ ★ ★ ★ ★</span>
              <span>| 100 avaliações</span>
            </div>

            {/* Preço */}
            <div className="mt-4">
              <div className="bg-gray-100 rounded-md px-4 py-3 inline-block">
                <span className="text-gray-900 font-semibold">R$ {product.preco?.toFixed(2)}</span>
                <span className="ml-2 text-gray-600">/ Unidade</span>
              </div>
            </div>

            {/* Frete e valor */}
            <div className="mt-4 grid grid-cols-3 gap-4 text-sm text-gray-700">
              <div>
                <div className="text-gray-500">Frete</div>
                <div>Fixo</div>
              </div>
              <div>
                <div className="text-gray-500">Frete para:</div>
                <div>Bairro Brasil</div>
              </div>
              <div>
                <div className="text-gray-500">Valor:</div>
                <div>R$ 5,00</div>
              </div>
            </div>

            {/* Quantidade */}
            <div className="mt-6">
              <div className="text-gray-700 text-sm mb-2">Quantidade</div>
              <div className="flex items-center gap-3">
                <button
                  className="px-3 py-1 border rounded"
                  onClick={() => setQuantity((q) => Math.max(1, q - 1))}
                >
                  -
                </button>
                <span className="px-4 py-1 border rounded bg-white">{quantity}</span>
                <button
                  className="px-3 py-1 border rounded"
                  onClick={() => setQuantity((q) => q + 1)}
                >
                  +
                </button>
              </div>
            </div>

            {/* Ações */}
            <div className="mt-6 flex gap-4">
              <button
                className="bg-green-700 text-white px-5 py-2 rounded-lg hover:bg-green-800"
                onClick={handleAddToCart}
              >
                Colocar no Carrinho
              </button>
              <button
                className="bg-gray-800 text-white px-5 py-2 rounded-lg hover:bg-black"
                onClick={() => navigate("/purchase")}
              >
                Comprar Agora
              </button>
            </div>
            {message && (
              <p className="mt-3 text-green-700 text-sm">{message}</p>
            )}
          </div>
        </div>

        {/* Detalhes do Produto */}
        <section className="mt-8 bg-white rounded-2xl shadow-md">
          <div className="px-6 py-4 border-b">
            <h2 className="text-lg font-semibold text-gray-900">Detalhes do Produto</h2>
          </div>
          <div className="grid grid-cols-2 md:grid-cols-4 gap-6 p-6 text-sm">
            <div>
              <div className="text-gray-500">Categoria</div>
              <div className="text-gray-800">Frutas</div>
            </div>
            <div>
              <div className="text-gray-500">Frutas</div>
              <div className="text-gray-800">Manga Tommy</div>
            </div>
            <div>
              <div className="text-gray-500">Estoque</div>
              <div className="text-gray-800">Disponível</div>
            </div>
            <div>
              <div className="text-gray-500">Tipo</div>
              <div className="text-gray-800">Manga Tommy</div>
            </div>
          </div>
        </section>

        {/* Avaliações */}
        <section className="mt-8 bg-white rounded-2xl shadow-md">
          <div className="px-6 py-4 border-b">
            <h2 className="text-lg font-semibold text-gray-900">Avaliações do Produto</h2>
          </div>
          <div className="p-6 flex flex-col gap-6">
            {[1, 2, 3, 4].map((i) => (
              <div key={i} className="flex items-start gap-4">
                <div className="w-10 h-10 rounded-full bg-gray-300" />
                <div className="flex-1">
                  <div className="flex items-center justify-between">
                    <div>
                      <div className="font-semibold">Nome do Cliente</div>
                      <div className="text-sm text-yellow-600">★★★★★</div>
                    </div>
                    <div className="text-gray-500 text-sm">01/01/2025</div>
                  </div>
                  <p className="text-gray-700 text-sm mt-1">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.
                  </p>
                </div>
              </div>
            ))}
          </div>
        </section>
      </main>

      <Footer />
    </div>
  );
}