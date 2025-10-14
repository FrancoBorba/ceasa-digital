import React, { useEffect, useState } from 'react';
import { getProducts, deleteProduct } from '../../services/productService';

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
    <div className="p-4">
      <h1 className="text-2xl font-semibold mb-4">Produtos</h1>
      <ul>
        {products.map(p => (
          <li key={p.id} className="flex justify-between border-b py-2">
            <span>{p.name}</span>
            <button
              className="bg-red-500 text-white px-2 py-1 rounded"
              onClick={() => handleDelete(p.id)}
            >
              Excluir
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}
