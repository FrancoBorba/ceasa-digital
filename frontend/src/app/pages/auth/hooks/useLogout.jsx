import { useNavigate } from "react-router-dom";
import { useUser } from "../../../context/UserContext";

export function useLogout() {
  const navigate = useNavigate();
  const { logout } = useUser();

  const handleLogout = () => {
    // 1. Limpa o estado global e o localStorage via Contexto
    logout();

    // 2. Redireciona para o login
    navigate("/login");
  };

  return handleLogout;
}
