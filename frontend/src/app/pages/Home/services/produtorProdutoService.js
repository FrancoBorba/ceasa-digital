import apiRequester from '../../auth/services/apiRequester';

const API_URL = `/api/v1/produtor-produtos`;

/**
 * GET /api/v1/produtor-produtos/me
 * Lista todos os produtos do produtor autenticado
 * @returns {Promise} Lista de produtos do produtor
 */
export const getMyProducts = async () => {
  const response = await apiRequester.get(`${API_URL}/me`);
  console.log("Resposta da API (getMyProducts):", response.data);
  return response.data;
};

/**
 * PUT /api/v1/produtor-produtos/desativar/{id}
 * Desativa um produto específico do produtor
 * @param {number} id - ID do produto a ser desativado
 * @returns {Promise} Resposta da API
 */
export const deactivateProduct = async (id) => {
  const response = await apiRequester.put(`${API_URL}/desativar/${id}`);
  console.log(`Produto ${id} desativado:`, response.data);
  return response.data;
};
