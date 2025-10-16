import React, { useEffect, useState } from 'react';
import { getCart, cleanCart, removeItemFromCart } from './services/cartService';

function Cart() {
  const [cart, setCart] = useState({ itens: [] });
  const [loading, setLoading] = useState(true);

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

  // Calcular o total do carrinho
  const calcularTotal = () => {
    return cart.itens.reduce(
      (acc, item) => acc + item.precoUnitario * item.quantidade,
      0
    );
  };

  if (loading) return <p>Carregando carrinho...</p>;

  if (!cart.itens || cart.itens.length === 0) {
    return (
      <div className="p-8">
        <h1 className="text-3xl font-bold mb-4">Carrinho de Compras</h1>
        <p>Seu carrinho está vazio por enquanto...</p>
      </div>
    );
  }

  return (
    <div className="p-8">
      <h1 className="text-3xl font-bold mb-4">Carrinho de Compras</h1>

      <ul className="space-y-4">
        {cart.itens.map((item) => (
          <li
            key={item.id}
            className="border p-4 rounded-lg flex justify-between items-center"
          >
            <div>
              <h2 className="font-semibold">{item.nomeProduto}</h2>
              <p>Quantidade: {item.quantidade}</p>
              <p>Preço unitário: R$ {item.precoUnitario.toFixed(2)}</p>
            </div>
            <button
              onClick={() => handleRemoveItem(item.id)}
              className="bg-red-500 text-white px-3 py-1 rounded-lg hover:bg-red-600"
            >
              Remover
            </button>
          </li>
        ))}
      </ul>

      <div className="mt-6 flex justify-between items-center">
        <h2 className="text-xl font-bold">
          Total: R$ {calcularTotal().toFixed(2)}
        </h2>
        <button
          onClick={handleCleanCart}
          className="bg-gray-700 text-white px-4 py-2 rounded-lg hover:bg-gray-800"
        >
          Limpar Carrinho
        </button>
      </div>
    </div>
  );
}

export default Cart;
