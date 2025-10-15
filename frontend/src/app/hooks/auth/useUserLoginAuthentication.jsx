import { useNavigate } from "react-router";
import { setAccessAndRefreshToken } from "../../pages/Home/services/auth/authTokenStorage";
import apiRequester from "../../pages/Home/services/auth/apiRequester";

function useUserLoginAuthentication() {
  const navigate = useNavigate();

  const tryToAuthenticateUser = async ({ username, password }) => {
    try {
      const { data } = await sendLoginRequestAndReturnDataResponse();
      setAccessAndRefreshToken(data.access_token, data.refresh_token);
      navigate("/");
    } catch (err) {
      const status = err?.response?.status;
      if (status == 400) {
        console.log(err);
        alert("Usuario nao encontrado.");
      }
    }

    function sendLoginRequestAndReturnDataResponse() {
      return apiRequester.post("/oauth2-docs/login", null, {
        params: {
          username: username,
          password: password,
          grant_type: "password",
        },
      });
    }
  };

  return { tryToAuthenticateUser };
}

export default useUserLoginAuthentication;
