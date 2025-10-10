import { useState } from "react";
import "../Login/Login.css"; 

const ForgotPassword = ({ onBack }) => {
  const [email, setEmail] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault(); 
    alert("Enviando link de redefinição para: " + email);
  };

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100">
      <form
        onSubmit={handleSubmit}
        className="bg-white p-10 rounded-xl shadow-lg w-full max-w-md space-y-8"
      >
        <h1 className="text-4xl font-bold mb-4 text-center text-gray-900">
          Redefinir Senha
        </h1>
        <p className="subtitle text-lg text-gray-700 text-center mb-6">
        Enviaremos um link para redefinir a sua senha.
        </p>
        <div>
          <input
            type="email"
            placeholder="Digite seu e-mail"
            required 
            onChange={(e) => setEmail(e.target.value)}
            className="w-full p-4 rounded-lg border-2 border-blue-500 text-gray-900 placeholder-gray-500 mb-6"
          />
        </div>
        <div className="flex flex-col gap-4">
          <button
            type="submit"
            className="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold text-lg hover:bg-blue-700 transition"
          >
            Enviar Link
          </button>
          <button
            type="button"
            onClick={onBack}
            className="w-full bg-gray-300 text-gray-700 py-3 rounded-lg font-semibold text-lg hover:bg-gray-400 transition"
          >
            Voltar
          </button>
        </div>
      </form>
    </div>
  );
};

export default ForgotPassword;