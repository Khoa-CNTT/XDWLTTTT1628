import { useAuthStore } from '../store/useAuthStore';

export default function guestGuard(to, from, next) {
  const authStore = useAuthStore();
  if (authStore.isAuthenticated) {
    return next({ name: 'Dashboard' });
  }
  next();
}