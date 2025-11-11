import { useState } from "react";
import apiRequester from "../services/apiRequester";

function useForgotPassword() {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const [isSuccess, setIsSuccess] = useState(false);

  const tryToSendResetEmail = async (email) => {
    setIsLoading(true);
    setError(null);
    setIsSuccess(false);

    try {
    
      await apiRequester.post("/auth/forgot-password", {
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

  return { tryToSendResetEmail, isLoading, isSuccess, error };
}

export default useForgotPassword;