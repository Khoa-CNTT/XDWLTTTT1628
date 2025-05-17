// src/services/examService.js
import api from './api';

const examService = {
  getLatestTests() {
    return api.get('/api/tests/latest');
  },
  getTestById(testId) {
    return api.get(`/api/tests/${testId}`);
  },
  getAllTests() {
    return api.get('/api/tests');
  },
};

export default examService;
