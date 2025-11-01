import { useState } from "react";
// Importa o seu 'axios' ou 'fetch' configurado
import apiRequester from "../services/apiRequester"; 

function useForgotPassword() {
  const [isLoading, setIsLoading] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);

  const tryToSendResetEmail = async (email) => {
    setIsLoading(true);
    setIsSuccess(false);

    try {
      
      await apiRequester.post("/oauth2-docs/forgot-password", {
        email: email,
      });
      
      setIsSuccess(true);
    } catch (err) {
      console.error("Falha na solicitação de redefinição:", err);
      setIsSuccess(true);
    } finally {
      setIsLoading(false);
    }
  };

  return { tryToSendResetEmail, isLoading, isSuccess };
}

export default useForgotPassword;