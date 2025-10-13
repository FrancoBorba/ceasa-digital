import axios from 'axios';
import {getToken} from './authTokenService';

const apiRequester = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  }
});

apiRequester.interceptors.request.use(requestConfig => {
  const access_token = getToken();
  
  if (access_token) {
    requestConfig.headers.Authorization = `Bearer ${access_token}`;
  }
  return requestConfig;
})

export default apiRequester;
