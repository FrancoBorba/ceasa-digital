const ACCESS_TOKEN_KEY = "access_token";
const REFRESH_TOKEN_KEY = "";

function setToken(token) {
  localStorage.setItem(ACCESS_TOKEN_KEY, token)
}

function getToken() {
  return localStorage.getItem(ACCESS_TOKEN_KEY);
}

function removeToken() {
  localStorage.removeItem(ACCESS_TOKEN_KEY);
}

export { setToken, getToken, removeToken };

 