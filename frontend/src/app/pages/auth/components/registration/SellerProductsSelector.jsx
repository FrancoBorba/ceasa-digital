import { useState } from "react";

function SellerProductsSelector({ products = [], onSelectionChange }) {
  const [showProductSelector, setShowProductSelector] = useState(false);
  const [productSearch, setProductSearch] = useState("");
  const [selectedProducts, setSelectedProducts] = useState([]);

  const toggleProduct = (product) => {
    setSelectedProducts((prev) => {
      let updated;
      if (prev.includes(product)) {
        updated = prev.filter((p) => p !== product);
      } else {
        updated = [...prev, product];
      }

      // notifica o componente pai
      onSelectionChange?.(updated);
      return updated;
    });
  };

  const filteredProducts = products.filter((p) =>
    p.nome
      ? p.nome.toLowerCase().includes(productSearch.toLowerCase())
      : false
  );

  return (
    <div>
      <div className="flex flex-col">
        <div className="flex flex-row items-center justify-evenly">
          <label className="text-black text-sm font-stretch-expanded font-bold">PRODUTOS QUE PRETENDE VENDER</label>

          <button
            type="button"
            className="text-4xl px-3 h-fit w-fit hover:bg-gray-300 hover:cursor-pointer rounded-lg"
            onClick={() => setShowProductSelector(true)}
          >
            +
          </button>
        </div>

        {selectedProducts.length > 0 ? (
          <div className="flex flex-wrap gap-2 justify-baseline mt-3">
            {selectedProducts.map((product) => (
              <div
                key={product.id || product.nome}
                className="bg-green-100 text-green-800 px-3 py-1 w-[31%] rounded-xl flex items-center gap-1"
              >
                <button
                  type="button"
                  onClick={() => toggleProduct(product)}
                  className="text-green-500 font-bold hover:text-green-300 hover:cursor-pointer"
                >
                  ×
                </button>
                <span className="text-[0.8rem] truncate flex-1">
                  {product.nome}
                </span>
              </div>
            ))}
          </div>
        ) : (
          <p className="col-span-3 p-2 text-center text-green-800 font-bold">
            Escolha pelo menos um produto.
          </p>
        )}
      </div>

      {showProductSelector && (
        <div className="fixed inset-0 bg-black/40 backdrop-blur-sm flex items-center justify-center z-50">
          <div className="flex flex-col gap-10 bg-white rounded-2xl p-10 shadow-xl w-[80%] sm:w-[60%] md:w-[50%] lg:w-[40%] max-w-2xl">
            <div>
              <label className="text-black text-sm font-stretch-expanded font-medium">PESQUISAR PRODUTOS</label>
              <input
                type="text"
                value={productSearch}
                onChange={(e) => setProductSearch(e.target.value)}
                className="w-full border-b-1 border-b-gray-600 focus:outline-none"
              />
            </div>

            <div className="grid sm:grid-cols-2 xl:grid-cols-3 gap-x-1 gap-y-1 overflow-y-auto max-h-[42vh]">
              {filteredProducts.length > 0 ? (
                filteredProducts.map((product) => (
                  <button
                    type="button"
                    key={product.id || product.nome}
                    onClick={() => toggleProduct(product)}
                    className={`relative rounded-2xl py-5 px-2 text-[0.8rem] hover:cursor-pointer max-h-[8vh]
                      flex items-center justify-center text-center break-words whitespace-normal leading-tight
                      ${
                        selectedProducts.includes(product)
                          ? "hidden"
                          : "bg-[#ebffce] border-[#e1ffbb] border-2  transition hover:bg-[#e0ffb8] font-medium text-[#225710]"
                      }`}
                  >
                    {product.nome}
                  </button>
                ))
              ) : (
                <p className="col-span-3 p-2 text-center text-gray-500 font-bold">Nenhum produto encontrado</p>
              )}
              {selectedProducts.length == products.length && (
                <p className="col-span-3 p-2 text-center text-gray-500 font-bold">
                  Todos os produtos foram selecionados
                </p>
              )}
            </div>

            <div className="flex justify-center items-center">
              <button
                className="h-12 w-3/6 sm:w-4/12 bg-[#3AB54A] text-white font-bold font-stretch-expanded rounded-lg hover:bg-[#69ea7a] transition-colors hover:cursor-pointer"
                onClick={() => setShowProductSelector(false)}
              >
                Confirmar
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default SellerProductsSelector;
