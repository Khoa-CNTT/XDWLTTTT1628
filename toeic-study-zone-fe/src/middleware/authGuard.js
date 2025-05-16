import { useAuthStore } from '../store/useAuthStore';

export default function authGuard(to, from, next) {
  const authStore = useAuthStore();
  if (!authStore.isAuthenticated) {
    return next({ name: 'Login', query: { redirect: to.fullPath } });
  }
  next();
}