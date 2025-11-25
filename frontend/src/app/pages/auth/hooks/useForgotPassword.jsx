import { useState } from 'react';
const API_URL = 'http://localhost:8080';

function useForgotPassword() {
  const [isLoading, setIsLoading] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);
  const [error, setError] = useState(null);

  const tryToSendResetEmail = async (email) => {
    setIsLoading(true);
    setError(null);

    try {
      
      const response = await fetch(`${API_URL}/users/forgot-password`, {
        method: 'POST', 
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email: email }) 
      });

      if (!response.ok) {
        throw new Error(`Erro: ${response.status}`);
      }

      setIsSuccess(true);

    } catch (err) {
      console.error("Erro ao conectar com o endpoint:", err);
      setError(err);
      alert("Ocorreu um erro. Verifique o e-mail e tente novamente.");
    } finally {
      setIsLoading(false);
    }
  };

  return { tryToSendResetEmail, isLoading, isSuccess, error };
}

export default useForgotPassword;