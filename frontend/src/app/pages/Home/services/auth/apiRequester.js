import axios from 'axios';
import {getAccessToken} from './authTokenStorage';

const apiRequester = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  }
});

apiRequester.interceptors.request.use(requestConfig => {
  const access_token = getAccessToken();
  if (access_token) {
    requestConfig.headers.Authorization = `Bearer ${access_token}`;
  }
  return requestConfig;
})

export default apiRequester;
