import { defineStore } from "pinia";
import authService from "../services/authService";
import { jwtDecode } from "jwt-decode";
import userService from "../services/userService"; // Import để gọi /api/users/me

export const useAuthStore = defineStore("auth", {
  state: () => ({
    token: null,
    isAuthenticated: false,
    userAvatar: null,
    role: null,
    username: null, // Thêm username vào state
  }),
  actions: {
    async login(credentials) {
      try {
        const response = await authService.login(credentials);
        console.log("Auth service response:", response);

        const token = response.data.token;
        this.token = token;
        this.isAuthenticated = true;
        console.log("Token received:", token);

        const decodedToken = jwtDecode(token);
        console.log("Decoded token:", decodedToken);

        const roleId = decodedToken.role_id;
        this.role = Number(roleId) === 2 ? "ADMIN" : "USER";
        console.log("Role ID from token:", roleId);
        console.log("Assigned role:", this.role);

        // Thử trích xuất username từ token (giả định 'sub' hoặc 'username')
        this.username = decodedToken.sub || decodedToken.username || null; // Điều chỉnh dựa trên cấu trúc token
        console.log("Username from token:", this.username);

        // Nếu token không chứa username, gọi API /api/users/me để lấy
        if (!this.username) {
          const userResponse = await userService.getCurrentUser();
          this.username = userResponse.data.username || userResponse.data.email; // Sử dụng username hoặc email
          console.log("Username from API:", this.username);
        }

        this.userAvatar =
          decodedToken.avatar ||
          response.data.avatar ||
          import.meta.env.BASE_URL + "assets/images/user-avatar.png";
        console.log("User avatar set:", this.userAvatar);

        return { success: true };
      } catch (error) {
        console.error("Login error:", error);
        this.logout();
        return {
          success: false,
          message: error.response?.data?.message || "Đăng nhập thất bại",
        };
      }
    },

    async register(userData) {
      try {
        const response = await authService.register(userData);
        console.log("Register response:", response);

        const token = response.data.token;
        this.token = token;
        this.isAuthenticated = true;
        console.log("Token received:", token);

        const decodedToken = jwtDecode(token);
        console.log("Decoded token:", decodedToken);

        const roleId = decodedToken.role_id;
        this.role = Number(roleId) === 2 ? "ADMIN" : "USER";
        console.log("Role ID from token:", roleId);
        console.log("Assigned role:", this.role);

        // Thử trích xuất username từ token
        this.username = decodedToken.sub || decodedToken.username || null;
        console.log("Username from token:", this.username);

        // Nếu token không chứa username, gọi API /api/users/me
        if (!this.username) {
          const userResponse = await userService.getCurrentUser();
          this.username = userResponse.data.username || userResponse.data.email;
          console.log("Username from API:", this.username);
        }

        this.userAvatar =
          decodedToken.avatar ||
          response.data.avatar ||
          import.meta.env.BASE_URL + "assets/images/user-avatar.png";
        console.log("User avatar set:", this.userAvatar);

        return { success: true };
      } catch (error) {
        console.error("Register error:", error);
        return {
          success: false,
          message: error.response?.data?.message || "Đăng ký thất bại",
        };
      }
    },

    logout() {
      this.token = null;
      this.isAuthenticated = false;
      this.userAvatar = null;
      this.role = null;
      this.username = null;
    },
  },
  persist: {
    storage: localStorage,
  },
});