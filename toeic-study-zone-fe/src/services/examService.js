// src/services/examService.js
import api from './api';

const examService = {
  getLatestTests(limit) {
    return api.get(`/api/tests/latest?limit=${limit}`);
  },
  getTestById(testId) {
    return api.get(`/api/tests/${testId}`);
  },
};

export default examService;