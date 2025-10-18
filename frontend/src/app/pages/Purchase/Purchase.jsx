import React from "react";
import { useNavigate } from "react-router-dom";

function PurchaseConfirmation() {
  const navigate = useNavigate();

  const handleBackToHome = () => {
    navigate("/"); // volta para a página inicial
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50">
      <div className="bg-white shadow-md rounded-2xl p-8 text-center max-w-md">
        <h2 className="text-2xl font-semibold text-green-600 mb-4">
           Será a tela de confirmação de compra.
        </h2>

        <p className="text-gray-700 mb-6">
          E então podemos por métodos de pagamento, resumo do pedido, etc.
        </p>

        <button
          onClick={handleBackToHome}
          className="bg-green-500 hover:bg-green-600 text-white font-medium py-2 px-6 rounded-lg transition"
        >
          Voltar para o início
        </button>
      </div>
    </div>
  );
}

export default PurchaseConfirmation;
