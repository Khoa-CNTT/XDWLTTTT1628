import api from './api';

const API_URL = '/api/tests';

export default {
  getTests() {
    return api.get(`${API_URL}`);
  },
  getTestById(id) {
    return api.get(`${API_URL}/${id}`);
  },
  createTest(testData) {
    return api.post(`${API_URL}`, testData);
  },
  updateTest(id, testData) {
    return api.put(`${API_URL}/${id}`, testData);
  },
  deleteTest(id) {
    return api.delete(`${API_URL}/${id}`);
  },
};