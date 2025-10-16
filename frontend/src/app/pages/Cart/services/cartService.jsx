import apiRequester from '../../auth/services/apiRequester';
const API_URL = `/api/v1/carrinhos`;

export async function getCart() {
  const response = await apiRequester.get(API_URL);
  return response.data;
}

export async function cleanCart() {
  await apiRequester.delete(`${API_URL}/limpar`);
}

export async function removeItemFromCart(idItem) {
  await apiRequester.delete(`${API_URL}/itens/${idItem}`);
}

export async function addItemToCart(itemRequest) {
  const response = await apiRequester.post(`${API_URL}/itens`, itemRequest);
  return response.data;
}

export async function updateItemInCart(idItem, itemRequest) {
  const response = await apiRequester.put(`${API_URL}/itens/${idItem}`, itemRequest);
  return response.data;
}
