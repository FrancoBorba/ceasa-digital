import apiRequester from '../../auth/services/apiRequester';
const API_URL = `/api/v1/products`;

export const getProducts = async () => {
  const response = await apiRequester.get(API_URL);
  console.log("Resposta da API (getProducts):", response.data);
  return response.data;
};

export const getProductById = async (id) => {
  const response = await apiRequester.get(`${API_URL}/${id}`);
  console.log(`Produto ID ${id}:`, response.data);
  return response.data;
};

export const createProduct = async (product) => {
  const response = await apiRequester.post(API_URL, product);
  return response.data;
};

export const updateProduct = async (id, product) => {
  const response = await apiRequester.put(`${API_URL}/${id}`, product);
  return response.data;
};

export const deleteProduct = async (id) => {
  await apiRequester.delete(`${API_URL}/${id}`);
};
