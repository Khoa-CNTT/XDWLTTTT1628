// src/services/advertisementService.js
import axios from './Axios';
const API = 'api/advertisements';

export default {
  getAll() {
    return axios.get(`${API}`);
  },
  getActive() {
    return axios.get(`${API}/active`);
  },
  create(advertisement) {
    return axios.post(`${API}`, advertisement); 
  },
  delete(id) {
    return axios.delete(`${API}/${id}`);
  },
};