import { useNavigate } from "react-router";
import { setAccessAndRefreshToken } from "../services/authTokenStorage";
import apiRequester from "../services/apiRequester";

function useUserLoginAuthentication() {
  const navigate = useNavigate();

  const tryToAuthenticateUser = async ({ username, password }) => {
    try {
      const { data } = await sendLoginRequestAndReturnDataResponse();
      console.log('🔑 Tokens recebidos:', {
        access_token: data.access_token,
        refresh_token: data.refresh_token
      });
      setAccessAndRefreshToken(data.access_token, data.refresh_token);
      console.log('💾 Tokens salvos no localStorage');
      navigate("/");
    } catch (err) {
      const status = err?.response?.status;
      if (status == 400) {
        console.log(err);
        alert("Usuario nao encontrado.");
      }
    }

    function sendLoginRequestAndReturnDataResponse() {
      const params = new URLSearchParams();
      params.append('username', username);
      params.append('password', password);
      params.append('grant_type', 'password');

      return apiRequester.post("/oauth2/token", params, {
        headers: {
          'Authorization': 'Basic ' + btoa('myclientid:myclientsecret'),
          'Content-Type': 'application/x-www-form-urlencoded',
        },
      });
    }
  };

  return { tryToAuthenticateUser };
}

export default useUserLoginAuthentication;
