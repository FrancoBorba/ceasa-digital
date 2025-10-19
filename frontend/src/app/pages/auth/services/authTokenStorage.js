const ACCESS_TOKEN_KEY = "access_token";
const REFRESH_TOKEN_KEY = "refresh_token";

function setAccessAndRefreshToken(accessToken, refreshToken) {
  localStorage.setItem(ACCESS_TOKEN_KEY, accessToken);
  localStorage.setItem(REFRESH_TOKEN_KEY, refreshToken)
}

function getAccessToken() {
  return localStorage.getItem(ACCESS_TOKEN_KEY);
}

function getRefreshToken() {
    return localStorage.getItem(REFRESH_TOKEN_KEY);
}

function removeAccessAndRefreshToken() {
  localStorage.removeItem(ACCESS_TOKEN_KEY, REFRESH_TOKEN_KEY);
}

export {setAccessAndRefreshToken, getAccessToken, getRefreshToken, removeAccessAndRefreshToken};
