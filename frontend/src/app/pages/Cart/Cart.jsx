import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getCart, cleanCart, removeItemFromCart } from './services/cartService';

function Cart() {
  const [cart, setCart] = useState({ itens: [] });
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();

  // Buscar carrinho ao montar o componente
  useEffect(() => {
    async function fetchCarrinho() {
      try {
        const data = await getCart();
        console.log('Carrinho retornado do backend:', data);
        setCart(data || { itens: [] });
      } catch (error) {
        console.error('Erro ao carregar o carrinho:', error);
      } finally {
        setLoading(false);
      }
    }

    fetchCarrinho();
  }, []);

  // Limpar todo o carrinho
  const handleCleanCart = async () => {
    try {
      await cleanCart();
      setCart({ itens: [] });
    } catch (error) {
      console.error('Erro ao limpar o carrinho:', error);
    }
  };

  // Ir para tela de compra
  const handleConfirmPurchase = () => {
    navigate('/purchase');
  }

  // Remover um item do carrinho
  const handleRemoveItem = async (idItem) => {
    try {
      await removeItemFromCart(idItem);
      setCart((prevCart) => ({
        ...prevCart,
        itens: prevCart.itens.filter((item) => item.id !== idItem),
      }));
    } catch (error) {
      console.error('Erro ao remover o item do carrinho:', error);
    }
  };

  if (loading) return <p>Carregando carrinho...</p>;

  if (!cart.itens || cart.itens.length === 0) {
    return (
      <div className="p-8 flex flex-col items-center">
        <h1 className="text-3xl font-bold mb-4">Seu Carrinho de Compras</h1>
        <p>Seu carrinho está vazio por enquanto...</p>
        <button
          onClick={() => navigate("/")}
          className="mb-6 mt-6 bg-gray-700 text-white px-4 py-2 rounded-lg hover:bg-gray-800 transition"
        >
          Ir às compras
        </button>
      </div>
    );
  }

  return (
    <div className="p-8">
      <h1 className="text-3xl text-center font-bold mb-4">Seu Carrinho de Compras</h1>
      <button
        onClick={() => navigate("/")}
        className="mb-6 bg-gray-700 text-white px-4 py-2 rounded-lg hover:bg-gray-800 transition"
      >
        Voltar
      </button>
      <ul className="space-y-4">
        {cart.itens.map((item) => (
          <li
            key={item.id}
            className="border p-4 rounded-xl flex flex-col sm:flex-row sm:items-center gap-4 bg-white shadow-sm hover:shadow-md transition-all"
          >

            <img className="w-full sm:w-32 h-32 object-cover rounded-lg" src={`http://localhost:8080${item.fotoUrl}`} />
            <div className="flex-1">
              <h2 className="font-semibold text-lg text-gray-900 mb-1">
                {item.nomeDoProduto}
              </h2>
              <p className="text-gray-600 text-sm mb-1">
                Quantidade: <span className="font-medium">{item.quantidade}</span>
              </p>
              <p className="text-gray-600 text-sm mb-1">
                Preço unitário:{" "}
                <span className="font-medium">
                  R$ {item.precoUnitario.toFixed(2)}
                </span>
              </p>
              <p className="text-gray-800 font-semibold mt-1">
                Subtotal: R$ {(item.quantidade * item.precoUnitario).toFixed(2)}
              </p>
            </div>

            <button
              onClick={() => handleRemoveItem(item.id)}
              className="bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600 self-end sm:self-auto"
            >
              Remover
            </button>
          </li>
        ))}
      </ul>

      <div className="mt-8 bg-gray-100 p-4 rounded-xl flex flex-col sm:flex-row justify-between items-center gap-3">
        <h2 className="text-xl font-bold text-gray-900">
          Total: R$ {cart.total.toFixed(2)}
        </h2>
        <div className="flex gap-3">
          <button
            onClick={handleCleanCart}
            className="bg-gray-700 text-white px-4 py-2 rounded-lg hover:bg-gray-800 transition"
          >
            Limpar Carrinho
          </button>
          <button
            onClick={handleConfirmPurchase}
            className="bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600 transition"
          >
            Confirmar Compra
          </button>
        </div>
      </div>
    </div>
  );
}

export default Cart;
