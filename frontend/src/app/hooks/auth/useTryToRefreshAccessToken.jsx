import { useNavigate } from "react-router";
import apiRequester from "../../pages/Home/services/auth/apiRequester";
import {
  getRefreshToken,
  removeAccessAndRefreshToken,
  setAccessAndRefreshToken,
} from "../../pages/Home/services/auth/authTokenStorage";
import { useCallback } from "react";

function useTryToRefreshAccessToken() {
  const navigate = useNavigate();

  const tryToRefreshAccessToken = useCallback(async () => {
    try {
      const { data } = await sendRefreshTokenAndReturnDataResponse();
      setAccessAndRefreshToken(data.access_token, data.refresh_token);
      navigate("/");
    } catch {
      removeAccessAndRefreshToken();
      console.log("Tokens expirados!");
    }

    function sendRefreshTokenAndReturnDataResponse() {
      return apiRequester.post("/oauth2-docs/refresh", null, {
        params: {
          refresh_token: getRefreshToken(),
          grant_type: "refresh_token",
        },
      });
    }
  }, [navigate]);

  return tryToRefreshAccessToken ;
}

export default useTryToRefreshAccessToken;
