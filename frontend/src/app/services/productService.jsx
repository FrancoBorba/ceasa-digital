import axios from 'axios';
// Coloquei a URL inteira aqui pq o .env dentro do front nao deu certo
const API_URL = `http://localhost:8080/api/v1/products`;

export const getProducts = async () => {
  const response = await axios.get(API_URL);
  console.log("Resposta da API (getProducts):", response.data);
  return response.data;
};

export const getProductById = async (id) => {
  const response = await axios.get(`${API_URL}/${id}`);
  console.log(`Produto ID ${id}:`, response.data);
  return response.data;
};

export const createProduct = async (product) => {
  const response = await axios.post(API_URL, product);
  return response.data;
};

export const updateProduct = async (id, product) => {
  const response = await axios.put(`${API_URL}/${id}`, product);
  return response.data;
};

export const deleteProduct = async (id) => {
  await axios.delete(`${API_URL}/${id}`);
};
