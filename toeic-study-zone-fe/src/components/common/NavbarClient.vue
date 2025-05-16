<template>
  <nav
    class="navbar navbar-brand navbar-expand-lg navbar-light bg-white shadow-sm"
  >
    <div class="container">
      <router-link class="navbar-brand" to="/">
        <img :src="logo" alt="Study Zone Logo" class="logo" />
        <span class="brand-text ms-2">Study Zone</span>
      </router-link>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarNav"
        aria-controls="navbarNav"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ms-auto">
          <li class="nav-item">
            <router-link class="nav-link" to="/dashboard">Dashboard</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/test-home">Luyện đề</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/introduction"
              >Giới thiệu</router-link
            >
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/placement-test"
              >Kiểm tra đầu vào</router-link
            >
          </li>
          <!-- Nút Đăng nhập nếu chưa đăng nhập -->
          <li class="nav-item login-button" v-if="!authStore.isAuthenticated">
            <router-link
              class="nav-link btn btn-primary text-white px-3 py-2"
              to="/login"
            >
              Đăng nhập
            </router-link>
          </li>
          <!-- Avatar và dropdown nếu đã đăng nhập -->
          <li class="nav-item dropdown" v-if="authStore.isAuthenticated">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              id="navbarDropdown"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <img
                :src="userAvatarUrl || defaultAvatar"
                alt="User Avatar"
                class="user-avatar"
                @error="onImageError"
              />
            </a>
            <ul
              class="dropdown-menu dropdown-menu-end"
              aria-labelledby="navbarDropdown"
            >
              <li>
                <router-link class="dropdown-item" to="/profile"
                  >Hồ sơ</router-link
                >
              </li>
              <li>
                <a class="dropdown-item" href="#" @click.prevent="logout"
                  >Đăng xuất</a
                >
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "../../store/useAuthStore";
import userService from "@/services/userService"; // Import userService giống Profile.vue

import logo from "@/assets/images/logo-study-zone.png";
import defaultAvatar from "@/assets/images/user-avatar.png"; // Đổi tên để đồng bộ với Profile.vue

// Sử dụng router và authStore
const router = useRouter();
const authStore = useAuthStore();

// State để lưu avatarUrl từ API
const userAvatarUrl = ref(null);

// Hàm lấy thông tin tài khoản và avatar
const fetchUserProfile = async () => {
  try {
    const response = await userService.getCurrentUser();
    console.log("Navbar user profile:", response.data);
    userAvatarUrl.value = response.data.avatarUrl || defaultAvatar;
  } catch (error) {
    console.error("Error fetching user profile in Navbar:", error.response?.data || error.message);
    userAvatarUrl.value = defaultAvatar; // Fallback nếu lỗi
  }
};

// Hàm đăng xuất
const logout = () => {
  authStore.logout();
  router.push("/home");
};

// Xử lý nếu lỗi ảnh
const onImageError = (event) => {
  event.target.src = defaultAvatar;
};

// Gọi API khi component được mounted
onMounted(() => {
  if (authStore.isAuthenticated) {
    fetchUserProfile(); // Chỉ gọi nếu đã đăng nhập
  }

  // Khởi tạo dropdown của Bootstrap
  if (typeof bootstrap !== "undefined") {
    const dropdownElementList = document.querySelectorAll(".dropdown-toggle");
    dropdownElementList.forEach((element) => {
      new bootstrap.Dropdown(element);
    });
  }
});
</script>

<style lang="scss" scoped>
.navbar {
  padding: 1rem 0;
  position: relative;
  z-index: 1000;
  min-height: 65px;
  width: 100%;
}

.navbar-brand {
  display: flex;
  align-items: center;
  height: 100%;
}

.logo {
  height: 55px;
  width: 70px;
  object-fit: contain;
}

.brand-text {
  font-size: 1.8rem;
  font-weight: 700;
  color: #1877f2;
  text-transform: uppercase;
  letter-spacing: 1px;
  line-height: 1;
  padding-top: 0.2rem;
}

.nav-link {
  font-size: 1rem;
  font-weight: 500;
  color: #1c2526;
  margin-left: 1rem;
}

.nav-link:hover {
  color: #1877f2;
}

.navbar-nav {
  display: flex;
  align-items: center;
}

.user-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
}

.login-button {
  display: block !important;
}

.btn-primary {
  background-color: #1877f2;
  border-color: #1877f2;
  display: inline-block !important;
  visibility: visible !important;
  opacity: 1 !important;
}

.dropdown-menu {
  min-width: 150px;
  border: 1px solid #e0e0e0;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.dropdown-item {
  font-size: 0.95rem;
  padding: 0.5rem 1rem;
}

.dropdown-item:hover {
  background-color: #f1f1f1;
}

@media (max-width: 992px) {
  .navbar-nav {
    padding-top: 1rem;
    align-items: flex-start;
  }

  .nav-item {
    margin-bottom: 0.5rem;
  }

  .login-button {
    margin-top: 0.5rem;
  }

  .logo {
    top: 0;
    transform: none;
  }

  .navbar {
    height: auto;
  }

  .navbar-collapse {
    position: absolute;
    top: 100%;
    left: 0;
    right: 0;
    background-color: white;
    padding: 1rem;
    margin: 0 1rem;
    border-radius: 0.5rem;
    box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
    z-index: 1000;

    &.collapsing,
    &.show {
      animation: fadeIn 0.3s ease;
    }
  }

  @keyframes fadeIn {
    from {
      opacity: 0;
      transform: translateY(-10px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }
}
</style>