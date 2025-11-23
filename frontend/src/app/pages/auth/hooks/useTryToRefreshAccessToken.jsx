import { useNavigate } from "react-router";
import apiRequester from "../services/apiRequester";
import {
  getRefreshToken,
  removeAccessAndRefreshToken,
  setAccessAndRefreshToken,
} from "../services/authTokenStorage";
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
      const params = new URLSearchParams();
      params.append('refresh_token', getRefreshToken());
      params.append('grant_type', 'refresh_token');

      return apiRequester.post("/oauth2/token", params, {
        headers: {
          'Authorization': 'Basic ' + btoa('ceasa-digital-client:ceasa-digital-secret'),
          'Content-Type': 'application/x-www-form-urlencoded',
        },
      });
    }
  }, [navigate]);

  return tryToRefreshAccessToken ;
}

export default useTryToRefreshAccessToken;
