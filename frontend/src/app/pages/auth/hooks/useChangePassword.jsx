import { useState } from "react";

const API_URL = 'http://localhost:8080';

function useChangePassword() {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const tryToChangePassword = async ({ currentPassword, newPassword }) => {
    setIsLoading(true);
    setError(null);
    const token = localStorage.getItem('authToken');

    try {
      const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
      if (!passwordRegex.test(newPassword)) {
         throw new Error("A senha deve ter no mínimo 8 caracteres, maiúscula, minúscula, número e especial.");
      }
      const response = await fetch(`${API_URL}/users/me/password`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
          oldPassword: currentPassword,
          newPassword: newPassword
        })
      });

      if (!response.ok) {
        if (response.status === 400) {
            throw new Error("Senha atual incorreta ou dados inválidos.");
        }
        throw new Error(`Erro ao alterar senha: ${response.status}`);
      }

      return true;

    } catch (err) {
      console.error(err);
      setError(err.message);
      throw err; 
    } finally {
      setIsLoading(false);
    }
  };

  return { tryToChangePassword, isLoading, error };
}

export default useChangePassword;