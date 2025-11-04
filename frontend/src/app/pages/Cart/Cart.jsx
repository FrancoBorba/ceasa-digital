import React, { useEffect, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { getCart, cleanCart, removeItemFromCart, updateItemInCart } from './services/cartService';
import { getProducts } from '../Product/services/productService';
import Header from '../../layouts/header/header';
import Footer from '../../layouts/footer/footer';

function Cart() {
  const [cart, setCart] = useState({ itens: [] });
  const [loading, setLoading] = useState(true);
  const [recommended, setRecommended] = useState([]);
  const [selectedItems, setSelectedItems] = useState([]);
  const [removeQty, setRemoveQty] = useState({});

  const navigate = useNavigate();

  // Buscar carrinho e recomendações ao montar o componente
  useEffect(() => {
    async function fetchData() {
      try {
        const cartData = await getCart();
        // Buscar produtos para enriquecer itens com imagem/nome
        const productsData = await getProducts();
        const list = Array.isArray(productsData?.content)
          ? productsData.content
          : (Array.isArray(productsData) ? productsData : []);
        const productsMap = new Map(list.map((p) => [p.id, p]));

        const enrichedItens = (cartData?.itens || []).map((item) => {
          const prodId = item.produtoID || item.idProduto || item.produtoId;
          const prod = productsMap.get(prodId);
          return {
            ...item,
            fotoUrl: item.fotoUrl || prod?.fotoUrl || '',
            nomeDoProduto: item.nomeDoProduto || prod?.nome || item.nome || 'Produto',
          };
        });

        setCart({ ...(cartData || { itens: [] }), itens: enrichedItens });
        setRemoveQty(Object.fromEntries(enrichedItens.map((i) => [i.id, 1])));
        const cartIds = new Set((cartData?.itens || []).map(i => i.produtoID || i.idProduto || i.produtoId));
        const filtered = list.filter(p => !cartIds.has(p.id));
        const shuffled = filtered.sort(() => Math.random() - 0.5);
        setRecommended(shuffled.slice(0, 8));
      } catch (error) {
        console.error('Erro ao carregar dados do carrinho/recomendações:', error);
      } finally {
        setLoading(false);
      }
    }

    fetchData();
  }, []);

  // Manter seleção consistente quando itens do carrinho mudam
  useEffect(() => {
    const ids = new Set((cart.itens || []).map((i) => i.id));
    setSelectedItems((prev) => prev.filter((id) => ids.has(id)));
  }, [cart.itens]);

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
        total: prevCart.itens
          .filter((item) => item.id !== idItem)
          .reduce((sum, i) => sum + Number(i.precoUnitario) * Number(i.quantidade), 0)
      }));
      setRemoveQty((prev) => {
        const { [idItem]: _, ...rest } = prev;
        return rest;
      });
    } catch (error) {
      console.error('Erro ao remover o item do carrinho:', error);
    }
  };

  // Remover quantidade específica de um item
  const handleRemoveQuantity = async (idItem) => {
    const item = cart.itens.find((i) => i.id === idItem);
    if (!item) return;
    const removeAmount = Number(removeQty[idItem] || 1);
    if (removeAmount <= 0) return;

    const currentQty = Number(item.quantidade);
    const newQty = currentQty - removeAmount;

    if (newQty <= 0) {
      // Remove o item inteiro se quantidade ficar zero ou negativa
      await handleRemoveItem(idItem);
      return;
    }

    try {
      const produtoID = item.produtoID || item.idProduto || item.produtoId;
      await updateItemInCart(idItem, { produtoID, quantidade: newQty });
      setCart((prevCart) => {
        const newItens = prevCart.itens.map((i) =>
          i.id === idItem ? { ...i, quantidade: newQty } : i
        );
        const newTotal = newItens.reduce((sum, x) => sum + Number(x.precoUnitario) * Number(x.quantidade), 0);
        return { ...prevCart, itens: newItens, total: newTotal };
      });
      // Reseta o controle para 1
      setRemoveQty((prev) => ({ ...prev, [idItem]: 1 }));
    } catch (error) {
      console.error('Erro ao atualizar a quantidade do item:', error);
    }
  };

  // Alterar quantidade com controles +/- (padrão da tela de produto)
  const handleChangeItemQuantity = async (idItem, delta) => {
    const item = cart.itens.find((i) => i.id === idItem);
    if (!item) return;
    const newQty = Number(item.quantidade) + Number(delta);
    if (newQty <= 0) {
      await handleRemoveItem(idItem);
      return;
    }
    try {
      const produtoID = item.produtoID || item.idProduto || item.produtoId;
      await updateItemInCart(idItem, { produtoID, quantidade: newQty });
      setCart((prevCart) => {
        const newItens = prevCart.itens.map((i) =>
          i.id === idItem ? { ...i, quantidade: newQty } : i
        );
        const newTotal = newItens.reduce(
          (sum, x) => sum + Number(x.precoUnitario) * Number(x.quantidade),
          0
        );
        return { ...prevCart, itens: newItens, total: newTotal };
      });
    } catch (error) {
      console.error('Erro ao alterar quantidade do item:', error);
    }
  };

  // Seleção de itens
  const toggleItemSelection = (id) => {
    setSelectedItems((prev) =>
      prev.includes(id) ? prev.filter((x) => x !== id) : [...prev, id]
    );
  };

  const allSelected = cart.itens.length > 0 && selectedItems.length === cart.itens.length;

  const toggleSelectAll = () => {
    if (allSelected) {
      setSelectedItems([]);
    } else {
      setSelectedItems(cart.itens.map((i) => i.id));
    }
  };

  const handleDeleteSelected = async () => {
    if (selectedItems.length === 0) return;
    try {
      await Promise.all(selectedItems.map((id) => removeItemFromCart(id)));
      setCart((prevCart) => {
        const newItens = prevCart.itens.filter((item) => !selectedItems.includes(item.id));
        const newTotal = newItens.reduce((sum, i) => sum + Number(i.precoUnitario) * Number(i.quantidade), 0);
        return { ...prevCart, itens: newItens, total: newTotal };
      });
      setSelectedItems([]);
    } catch (error) {
      console.error('Erro ao excluir itens selecionados:', error);
    }
  };

  if (loading) return <p>Carregando carrinho...</p>;
  const baseUrl = import.meta.env?.VITE_API_URL || "http://localhost:8080";

  return (
    <div className="min-h-screen flex flex-col bg-gray-50">
      <Header />
      <main className="flex-1 p-8">
        <h1 className="text-3xl text-center font-bold mb-6">Carrinho</h1>

      {/* Grade tipo tabela do carrinho */}
      <div className="bg-white rounded-2xl shadow-md overflow-hidden">
        {/* Cabeçalho da tabela */}
        <div className="grid grid-cols-12 items-center px-6 py-4 bg-gray-100 text-sm font-semibold text-gray-700">
          <div className="col-span-5 flex items-center gap-2">
            <input
              type="checkbox"
              className="h-4 w-4"
              checked={allSelected}
              onChange={toggleSelectAll}
            />
            Produtos
          </div>
          <div className="col-span-2 text-center">Preço Unitário</div>
          <div className="col-span-2 text-center">Quantidade</div>
          <div className="col-span-2 text-center">Preço Total</div>
          <div className="col-span-1 text-center">Ações</div>
        </div>

        {/* Linhas de itens */}
        {(!cart.itens || cart.itens.length === 0) ? (
          <div className="px-6 py-12 text-center">
            <p className="text-gray-600">Seu carrinho está vazio por enquanto...</p>
            <button
              onClick={() => navigate("/")}
              className="mt-4 bg-gray-700 text-white px-4 py-2 rounded-lg hover:bg-gray-800 transition"
            >
              Ir às compras
            </button>
          </div>
        ) : (
        cart.itens.map((item) => (
          <div key={item.id} className="grid grid-cols-12 items-center px-6 py-4 border-t">
            <div className="col-span-5 flex items-center gap-4">
              <input
                type="checkbox"
                className="h-4 w-4"
                checked={selectedItems.includes(item.id)}
                onChange={() => toggleItemSelection(item.id)}
              />
              <img
                src={`${baseUrl}${item.fotoUrl}`}
                alt={item.nomeDoProduto}
                className="w-24 h-24 object-cover rounded-lg"
              />
              <div>
                <h2 className="text-sm font-semibold text-gray-900 leading-tight">
                  {item.nomeDoProduto}
                </h2>
                <p className="text-xs text-gray-500">Unidade Aproximada</p>
              </div>
            </div>

            <div className="col-span-2 text-center text-gray-800">
              R$ {Number(item.precoUnitario).toFixed(2)}
            </div>

            <div className="col-span-2 text-center text-gray-800">
              <div className="flex items-center justify-center gap-3">
                <button
                  className="px-3 py-1 border rounded"
                  onClick={() => handleChangeItemQuantity(item.id, -1)}
                >
                  -
                </button>
                <span className="px-4 py-1 border rounded bg-white">
                  {item.quantidade}
                </span>
                <button
                  className="px-3 py-1 border rounded"
                  onClick={() => handleChangeItemQuantity(item.id, 1)}
                >
                  +
                </button>
              </div>
            </div>

            <div className="col-span-2 text-center font-semibold text-gray-900">
              R$ {(Number(item.precoUnitario) * Number(item.quantidade)).toFixed(2)}
            </div>

            <div className="col-span-1 text-center">
              <button
                onClick={() => handleRemoveItem(item.id)}
                className="text-red-600 hover:text-red-700 underline"
              >
                Excluir
              </button>
            </div>
          </div>
        )))}

        {/* Barra inferior de ações */}
        <div className="flex items-center justify-between px-6 py-4 bg-gray-50 border-t">
          <div className="flex items-center gap-6 text-sm">
            <div className="flex items-center gap-2 text-gray-700">
              <input
                type="checkbox"
                className="h-4 w-4"
                checked={allSelected}
                onChange={toggleSelectAll}
              />
              Selecionar tudo ({selectedItems.length})
            </div>
            <button
              onClick={handleDeleteSelected}
              className={`underline ${selectedItems.length === 0 ? 'text-gray-400 cursor-not-allowed' : 'text-red-600 hover:text-red-700'}`}
              disabled={selectedItems.length === 0}
            >
              Excluir tudo({selectedItems.length})
            </button>
          </div>
          <button
            onClick={handleConfirmPurchase}
            className="bg-green-600 text-white px-6 py-2 rounded-lg hover:bg-green-700"
          >
            Continuar
          </button>
        </div>
      </div>

      {/* Você Também Pode Gostar */}
      <div className="mt-10">
        <h2 className="text-lg font-semibold mb-4">Você Também Pode Gostar</h2>
        {recommended.length === 0 ? (
          <p className="text-sm text-gray-600">Nenhuma recomendação disponível.</p>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
            {recommended.map((p) => (
              <Link
                to={`/productDetail/${p.id}`}
                key={p.id}
                className="bg-white rounded-xl shadow-sm overflow-hidden hover:shadow-md transition block"
              >
                <img
                  src={`${baseUrl}${p.fotoUrl}`}
                  alt={p.nome}
                  className="w-full h-40 object-cover"
                />
                <div className="p-4">
                  <h3 className="text-sm font-semibold text-gray-900 leading-tight">{p.nome}</h3>
                  <div className="flex items-center gap-1 text-xs text-yellow-600 mt-1">
                    <span>★★★★★</span>
                    <span className="text-gray-400">150 vendidas</span>
                  </div>
                  <div className="mt-2 font-bold text-gray-900">R$ {Number(p.preco).toFixed(2)}</div>
                </div>
              </Link>
            ))}
          </div>
        )}
      </div>
      </main>
      <Footer />
    </div>
  );
}

export default Cart;
