import { useState } from "react";
import { useNavigate } from "react-router-dom";
import apiRequester from "../services/apiRequester";

function useResetPassword() {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const tryToResetPassword = async ({ token, password }) => {
    setIsLoading(true);
    setError(null);
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    
    if (!passwordRegex.test(password)) {
       setError("A senha deve ter no mínimo 8 caracteres, letras maiúsculas, minúsculas, números e caracteres especiais.");
       setIsLoading(false);
       return;
    }

    try {
      await apiRequester.post("/users/reset-password", {
        token: token,
        password: password,
      });

      alert("Senha redefinida com sucesso!");
      navigate("/login");
      
    } catch (err) {
      const status = err?.response?.status;
      if (status === 400) {
        setError("Link inválido, expirado ou senha não atende aos requisitos.");
      } else if (status === 404) {
        setError("Token não encontrado ou expirado.");
      } else {
        setError("Ocorreu um erro. Tente novamente.");
      }
      console.error("Falha ao redefinir senha:", err);
    } finally {
      setIsLoading(false);
    }
  };

  return { tryToResetPassword, isLoading, error };
}

export default useResetPassword;