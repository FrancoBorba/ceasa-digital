import apiRequester from "../services/apiRequester";
import { removeAccessAndRefreshToken } from "../services/authTokenStorage";
import { useNavigate } from "react-router";

function useChangePassword() {
  const navigate = useNavigate();

  const tryToChangePassword = async ({ oldPassword, newPassword }) => {
    try {
      await apiRequester.post("/oauth2-docs/change-password", null, {
        params: {
          old_password: oldPassword,
          new_password: newPassword,
        },
      });

      alert("Senha alterada com sucesso! Faça login novamente.");
      removeAccessAndRefreshToken();
      navigate("/login");
    } catch (err) {
      console.error(err);
      alert("Erro ao alterar senha. Verifique seus dados.");
    }
  };

  return { tryToChangePassword };
}

export default useChangePassword;
