// src/services/statisticsService.js
import api from './Axios';

export default {
  getSummary() {
    return api.get('/api/statistics/summary');
  },
  getTopTests() {
    return api.get('/api/statistics/top-tests');
  },
  getGoalCompletion() {
    return api.get('/api/statistics/goal-completion');
  },
};
