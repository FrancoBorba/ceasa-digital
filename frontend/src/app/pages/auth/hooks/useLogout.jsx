import { useNavigate } from "react-router-dom";
import { removeAccessAndRefreshToken } from "../services/authTokenStorage";
import { useUser } from "../../../context/UserContext";

export function useLogout() {
  const navigate = useNavigate();
  const { setUserName, setAvatar } = useUser();

  const logout = () => {
    // 1. Remove os tokens do LocalStorage
    removeAccessAndRefreshToken();

    // 2. Reseta o estado do usuário no Contexto 
    setUserName("Ceasa Digital"); 
    setAvatar("https://i.pravatar.cc/150");

    // 3. Redireciona para a tela de Login
    navigate("/login");
  };

  return logout;
}