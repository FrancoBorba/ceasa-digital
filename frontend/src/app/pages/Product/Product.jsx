import React, { useEffect, useState } from 'react';
import { getProducts, deleteProduct } from './services/productService';
import { Link } from 'react-router-dom';

export default function Products() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getProducts().then(data => {
      setProducts(data);
      setLoading(false);
    });
  }, []);

  const handleDelete = async (id) => {
    await deleteProduct(id);
    setProducts(products.filter(p => p.id !== id));
  };

  if (loading) return <p>Carregando produtos...</p>;

  return (
    <section className="p-4">
      <h1 className="text-3xl font-bold mb-6 text-gray-800 text-center">Produtos em Destaque</h1>

      {products.length === 0 ? (
        <p className="text-center text-xl">Nenhum produto encontrado.</p>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
          {products.map((p) => (
            <div 
              key={p.id} 
              className="bg-white shadow-md rounded-2xl p-4 hover:shadow-lg transition-all duration-200"
            >
              {/* Link para pagina do item */}
              <Link to={`/productDetail/${p.id}`} className="block">
                <img 
                  src={`http://localhost:8080${p.fotoUrl}`} 
                  alt={p.nome} 
                  className="w-full h-40 object-cover rounded-xl mb-3"
                />
                <h3 className="text-lg font-semibold text-gray-900">{p.nome}</h3>
                <p className="text-gray-600 text-sm mb-2">{p.descricao}</p>
                <span className="text-green-600 font-bold">
                  R$ {p.preco?.toFixed(2)}
                </span>
              </Link>
            </div>
          ))}
        </div>
      )}
    </section>
  );
}
