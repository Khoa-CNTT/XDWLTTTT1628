import api from './api';

const authService = {
  login(credentials) {
    return api.post('/api/auth/login', credentials);
  },
  register(data) {
    return api.post('/api/auth/register', data);
  },
};

export default authService;