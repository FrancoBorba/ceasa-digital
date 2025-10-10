import { useState } from "react";
import "./Login.css";

const Login = ({ onForgotPassword }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();
    alert("Enviando os dados: " + email + " - " + password);
  };

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100">
      <form
        onSubmit={handleSubmit}
        className="bg-white p-10 rounded-xl shadow-lg w-full max-w-md space-y-6"
      >
        <h1 className="text-10xl font-bold mb-8 text-center text-gray-900">
          Login
        </h1>
        <div>
          <input
            type="email"
            placeholder="E-mail"
            required
            onChange={(e) => setEmail(e.target.value)}
            className="w-full p-4 rounded-lg border-2 border-blue-500 mb-4 text-gray-900 placeholder-gray-500"
          />
        </div>
        <div>
          <input
            type="password"
            placeholder="Senha"
            required
            onChange={(e) => setPassword(e.target.value)}
            className="w-full p-4 rounded-lg border-2 border-blue-500 mb-4 text-gray-900 placeholder-gray-500"
          />
        </div>
        <div className="flex justify-center mb-4">
          <a
            href="#"
            onClick={(e) => {
              e.preventDefault();
              onForgotPassword();
            }}
            className="text-blue-500 hover:underline"
          >
            Esqueceu a senha?
          </a>
        </div>
        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition"
        >
          Entrar
        </button>
        <div className="flex justify-center mb-4 text-blue-500 hover:underline cursor-pointe">
          <p>
            Não tem uma conta?{" "}
            <span>
              Registrar
            </span>
          </p>
        </div>
      </form>
    </div>
  );
};

export default Login;