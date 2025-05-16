import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use(
  (config) => {
    // Bỏ qua việc kiểm tra token cho request login
    if (config.url.includes('/api/auth/login')) {
      return config;
    }

    const authData = localStorage.getItem('auth');
    let token = null;
    if (authData) {
      const parsedData = JSON.parse(authData);
      token = parsedData.token;
    }
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    } else {
      console.warn('No token found in localStorage for URL:', config.url);
    }
    return config;
  },
  (error) => Promise.reject(error)
);

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem('auth');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;