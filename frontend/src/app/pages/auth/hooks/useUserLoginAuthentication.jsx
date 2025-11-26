import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { setAccessAndRefreshToken } from "../services/authTokenStorage";
import apiRequester from "../services/apiRequester";
import { useUser } from "../../../context/UserContext";

function useUserLoginAuthentication() {
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();
  const { handleLogin } = useUser(); 

  const tryToAuthenticateUser = async ({ username, password }) => {
    setIsLoading(true);
    try {
      const params = new URLSearchParams();
      params.append('username', username);
      params.append('password', password);
      params.append('grant_type', 'password');

      const tokenResponse = await apiRequester.post("/oauth2/token", params, {
        headers: {
          'Authorization': 'Basic ' + btoa('myclientid:myclientsecret'),
          'Content-Type': 'application/x-www-form-urlencoded',
        },
      });

      const { access_token, refresh_token } = tokenResponse.data;
      setAccessAndRefreshToken(access_token, refresh_token);

      const userResponse = await apiRequester.get("/users/me");
      const userData = userResponse.data;

      handleLogin({
        name: userData.name,
        email: userData.email,
        role: userData.role,
        avatarUrl: userData.avatarUrl
      });

      if (userData.role === 'ROLE_PRODUTOR') {
        navigate("/producer/dashboard");
      } else if (userData.role === 'ROLE_ADMIN') {
        navigate("/admin/products");
      } else {
        navigate("/"); 
      }

    } catch (err) {
      console.error(err);
      const status = err?.response?.status;
      
      if (status === 400 || status === 401) {
        alert("E-mail ou senha incorretos.");
      } else {
        alert("Erro ao conectar com o servidor.");
      }
    } finally {
      setIsLoading(false);
    }
  };

  return { tryToAuthenticateUser, isLoading };
}

export default useUserLoginAuthentication;