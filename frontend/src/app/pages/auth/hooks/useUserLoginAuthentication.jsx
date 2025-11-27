import { useNavigate } from "react-router-dom"; // Nota: react-router-dom é o pacote padrão para web
import { setAccessAndRefreshToken } from "../services/authTokenStorage";
import apiRequester from "../services/apiRequester";
import { useUser } from "../../../context/UserContext"; // Importe o Contexto

function useUserLoginAuthentication() {
  const navigate = useNavigate();
  const { login } = useUser(); // Pegamos a função de login do contexto

  const tryToAuthenticateUser = async ({ username, password }) => {
    try {
      const { data } = await sendLoginRequestAndReturnDataResponse();

      console.log("🔑 Tokens recebidos:", {
        access_token: data.access_token,
        refresh_token: data.refresh_token,
      });

      // 1. Salva no localStorage (persistência)
      setAccessAndRefreshToken(data.access_token, data.refresh_token);
      console.log("💾 Tokens salvos no localStorage");

      // 2. Decodifica o token para pegar infos do usuário (Roles, Nome, etc)
      const decodedToken = parseJwt(data.access_token);
      console.log("📜 Token decodificado:", decodedToken);

      // Extrai as roles. O padrão Spring Security pode colocar em 'authorities', 'scope' ou 'realm_access'
      // Ajuste conforme o seu backend retorna. Geralmente é 'scope' ou 'authorities'.
      const roles = decodedToken.authorities || decodedToken.scope || [];

      // Ajuste: O 'scope' do Spring às vezes vem como string "ROLE_ADMIN ROLE_USER", precisamos transformar em array
      const rolesArray = Array.isArray(roles) ? roles : roles.split(" ");

      // 3. Atualiza o Contexto Global (Memória da App)
      login({
        username: decodedToken.sub || username, // 'sub' costuma ser o username/email no JWT
        roles: rolesArray,
        token: data.access_token,
      });

      // 4. Redireciona
      navigate("/");
    } catch (err) {
      console.error(err);
      const status = err?.response?.status;
      if (status === 400 || status === 401) {
        alert("Usuário ou senha incorretos.");
      } else {
        alert("Erro ao tentar fazer login. Tente novamente mais tarde.");
      }
    }

    function sendLoginRequestAndReturnDataResponse() {
      const params = new URLSearchParams();
      params.append("username", username);
      params.append("password", password);
      params.append("grant_type", "password");

      // Nota: Verifique se as credenciais 'myclientid:myclientsecret' estão certas com seu backend
      return apiRequester.post("/oauth2/token", params, {
        headers: {
          Authorization: "Basic " + btoa("myclientid:myclientsecret"),
          "Content-Type": "application/x-www-form-urlencoded",
        },
      });
    }
  };

  return { tryToAuthenticateUser };
}

// Função auxiliar para ler o JWT sem precisar de biblioteca externa
function parseJwt(token) {
  try {
    var base64Url = token.split(".")[1];
    var base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    var jsonPayload = decodeURIComponent(
      window
        .atob(base64)
        .split("")
        .map(function (c) {
          return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
        })
        .join("")
    );

    return JSON.parse(jsonPayload);
  } catch (e) {
    return {};
  }
}

export default useUserLoginAuthentication;
