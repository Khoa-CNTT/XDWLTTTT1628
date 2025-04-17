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
            <router-link class="nav-link" to="/">Khóa học</router-link>
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
          <!-- Hiển thị nút Đăng nhập ngay từ đầu -->
          <li class="nav-item login-button">
            <router-link
              class="nav-link btn btn-primary text-white px-3 py-2"
              to="/login"
              v-if="!isLoggedIn"
            >
              Đăng nhập
            </router-link>
          </li>
          <li class="nav-item dropdown" v-if="isLoggedIn">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              id="navbarDropdown"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <img :src="userAvatar" alt="User Avatar" class="user-avatar" />
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
                <router-link class="dropdown-item" to="/logout"
                  >Đăng xuất</router-link
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
import { ref, onMounted } from "vue";

import logo from "@/assets/images/logo-study-zone.png";
import userAvatar from "@/assets/images/user-avatar.png";

// Mô phỏng trạng thái đăng nhập (thay bằng logic thực tế khi có BE)
const isLoggedIn = ref(false);

// Để kiểm tra trạng thái đăng nhập khi component được tạo
onMounted(() => {
  console.log("Trạng thái đăng nhập:", isLoggedIn.value);
  // Đảm bảo Bootstrap JS được khởi tạo
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
  min-height: 65px; /* Thay height bằng min-height */
  width: 100%;
}

.navbar-brand {
  display: flex;
  align-items: center; /* Căn giữa theo chiều dọc */
  height: 100%; /* Chiếm toàn bộ chiều cao */
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
  line-height: 1; /* Đặt line-height chuẩn */
  padding-top: 0.2rem; /* Điều chỉnh vị trí nếu cần */
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
  align-items: center; /* Căn giữa các mục điều hướng theo chiều dọc */
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

@media (max-width: 992px) {
  .navbar-nav {
    padding-top: 1rem;
    align-items: flex-start; /* Trên thiết bị di động, căn chỉnh các mục về phía đầu */
  }

  .nav-item {
    margin-bottom: 0.5rem;
  }

  .login-button {
    margin-top: 0.5rem;
  }

  .logo {
    top: 0; /* Đặt lại vị trí trên thiết bị di động */
    transform: none; /* Xóa transform trên thiết bị di động */
  }

  .navbar {
    height: auto; /* Cho phép thanh điều hướng mở rộng trên thiết bị di động */
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
