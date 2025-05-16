import api from './api';

const userService = {
  getAllUsers(keyword) {
    return api.get('/api/admin/users', { params: { keyword } });
  },
  createUser(data) {
    return api.post('/api/admin/users', data);
  },
  updateUser(id, data) {
    return api.put(`/api/admin/users/${id}`, data);
  },
  deleteUser(id) {
    return api.delete(`/api/admin/users/${id}`);
  },
  getCurrentUser() {
    return api.get('/api/users/me');
  },
  updateCurrentUser(data) {
    return api.put('/api/users/me', data);
  },
  uploadAvatar(formData) {
    return api.post('/api/users/upload-avatar', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },

  getLearningGoals(userId) {
    return api.get(`/api/learning-goals/user/${userId}`);
  },
  createLearningGoal(payload) {
    return api.post(`/api/learning-goals`, payload);
  },
  updateLearningGoal(id, payload) {
    return api.put(`/api/learning-goals/${id}`, payload);
  },
  deleteLearningGoal(id) {
    return api.delete(`/api/learning-goals/${id}`);
  },
};

export default userService;
