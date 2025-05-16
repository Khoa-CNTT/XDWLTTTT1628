<template>
  <div class="sidebar">
    <div class="logo">
      <i class="fas fa-graduation-cap"></i>
      <span class="brand-text">STUDY ZONE</span>
    </div>

    <ul class="menu">
      <!-- Dashboard -->
      <li>
        <router-link to="/admin/dashboard">
          <i class="fas fa-tachometer-alt"></i>
          <span>Dashboard</span>
        </router-link>
      </li>

      <!-- Dropdown Quản lý -->
      <li @click="toggleDropdown">
        <div class="dropdown-header">
          <div class="left-group">
            <i class="fas fa-box"></i>
            <span>Quản lý</span>
          </div>
          <i
            class="fas"
            :class="isOpen ? 'fa-chevron-up' : 'fa-chevron-down'"
          ></i>
        </div>
      </li>
      <ul v-show="isOpen" class="submenu">
        <li>
          <router-link to="/admin/users-manager"
            ><i class="fas fa-user"></i> Tài khoản</router-link
          >
        </li>
        <li>
          <router-link to="/admin/comments"
            ><i class="fas fa-comments"></i> Bình luận</router-link
          >
        </li>
        <li>
          <router-link to="/admin/ads"
            ><i class="fas fa-bullhorn"></i> Quảng cáo</router-link
          >
        </li>
        <li>
          <router-link to="/admin/test-bank"
            ><i class="fas fa-database"></i> Ngân hàng bài thi</router-link
          >
        </li>
        <li>
          <router-link to="/admin/test-manager"
            ><i class="fas fa-file-alt"></i> Bài thi</router-link
          >
        </li>
        <li>
          <router-link to="/admin/test-history"
            ><i class="fas fa-history"></i> Lịch sử bài thi</router-link
          >
        </li>
        <li>
          <router-link to="/admin/schedule-manager"
            ><i class="fas fa-calendar-alt"></i> Lịch học</router-link
          >
        </li>
      </ul>

      <!-- Các mục còn lại -->
      <li>
        <router-link to="/admin/statistics">
          <i class="fas fa-chart-bar"></i>
          <span>Thống kê</span>
        </router-link>
      </li>
      <li>
        <router-link to="">
          <i class="fas fa-robot"></i>
          <span>Trợ lý AI</span>
        </router-link>
      </li>
      <li>
        <router-link to="/admin/role-manager">
          <i class="fas fa-users-cog"></i>
          <span>Phân quyền</span>
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref } from "vue";
const isOpen = ref(false);
const toggleDropdown = () => {
  isOpen.value = !isOpen.value;
};
</script>

<style scoped>
.sidebar {
  width: 250px;
  height: 100vh;
  background-color: #1f2d3d;
  color: #fff;
  position: fixed;
  display: flex;
  flex-direction: column;
  font-size: 14px;
  padding: 4px;
}

.logo {
  font-size: 1.1rem;
  font-weight: bold;
  padding: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
}

.brand-text {
  text-transform: uppercase;
  font-weight: bold;
  color: #fff;
  font-size: 1rem;
}

.menu {
  list-style: none;
  padding: 0;
  margin: 1rem 0 0;
}

.menu > li {
  padding: 0.8rem 1.5rem;
  font-size: 0.88rem;
}

.menu > li > .dropdown-header,
.menu > li > a {
  display: flex;
  align-items: center;
  gap: 10px;
  color: white;
  text-decoration: none;
  transition: background-color 0.2s ease;
}

.menu > li > a:hover,
.menu > li:hover > .dropdown-header {
  background-color: #32475b;
  border-radius: 6px;
}

.dropdown-header {
  justify-content: space-between;
  cursor: pointer;
}

.left-group {
  display: flex;
  align-items: center;
  gap: 10px;
}

.submenu {
  list-style: none;
  background-color: #2c3e50;
  padding-left: 2rem;
}

.submenu li {
  padding: 0.6rem 0.5rem;
  font-size: 0.85rem;
}

.submenu li a {
  color: white;
  text-decoration: none;
  display: flex;
  align-items: center;
  gap: 10px;
}

.submenu li a:hover {
  background-color: #3b5166;
  border-radius: 4px;
}
</style>
