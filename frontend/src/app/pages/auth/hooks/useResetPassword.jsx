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

    try {
      await apiRequester.post("/auth/reset-password", {
        token: token,
        password: password,
      });

      alert("Senha redefinida com sucesso!");
      navigate("/login");
    } catch (err) {
      const status = err?.response?.status;
      if (status === 400) {
        setError("Link de redefinição inválido ou expirado.");
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