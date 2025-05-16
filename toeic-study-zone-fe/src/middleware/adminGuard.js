import { useAuthStore } from '../store/useAuthStore';

export default function adminGuard(to, from, next) {
  const authStore = useAuthStore();
  if (!authStore.isAuthenticated || authStore.role !== 'ADMIN') {
    return next({ name: 'Dashboard' }); // Chuyển về trang User nếu không phải Admin
  }
  next();
}