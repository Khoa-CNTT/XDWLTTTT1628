<template>
  <header class="admin-header">
    <div class="title">TRANG QUẢN TRỊ</div>
    <div class="right">
      <i class="fas fa-bell"></i>
      <div class="user-section" @click="toggleUserDropdown">
        <i class="fas fa-user-circle"></i>
        <i class="fas" :class="isUserOpen ? 'fa-chevron-up' : 'fa-chevron-down'"></i>
        <ul v-show="isUserOpen" class="user-dropdown">
          <li @click="logout">
            <a href="#" style="color: #ff4444;">Đăng xuất</a>
          </li>
        </ul>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "../../store/useAuthStore";

const isUserOpen = ref(false);
const router = useRouter();
const authStore = useAuthStore();

const toggleUserDropdown = () => {
  isUserOpen.value = !isUserOpen.value;
};

const logout = () => {
  authStore.logout(); 
  router.push("/login"); 
  isUserOpen.value = false; 
};

// Đóng dropdown khi click ra ngoài (tùy chọn)
document.addEventListener("click", (event) => {
  const userSection = document.querySelector(".user-section");
  if (userSection && !userSection.contains(event.target)) {
    isUserOpen.value = false;
  }
});
</script>

<style scoped>
.admin-header {
  height: 60px;
  background-color: #ffffff;
  border-bottom: 1px solid #ddd;
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: sticky;
  top: 0;
  z-index: 10;
}

.title {
  font-size: 1.2rem;
  font-weight: 600;
  color: #1f2d3d;
}

.right {
  display: flex;
  align-items: center;
}

.right i {
  font-size: 1.2rem;
  margin-left: 20px;
  cursor: pointer;
  color: #555;
}

.right i:hover {
  color: #000;
}

.user-section {
  position: relative;
  display: flex;
  align-items: center;
  gap: 5px; 
}

.user-section .fa-chevron-down,
.user-section .fa-chevron-up {
  font-size: 0.9rem;
  color: #555;
}

.user-section:hover .fa-chevron-down,
.user-section:hover .fa-chevron-up {
  color: #000;
}

/* Style cho dropdown */
.user-dropdown {
  position: absolute;
  top: 100%;
  right: 0;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  list-style: none;
  padding: 5px 0;
  margin-top: 5px;
  min-width: 120px;
  z-index: 11;
}

.user-dropdown li {
  padding: 5px 12px;
}

.user-dropdown li a {
  text-decoration: none;
  display: block;
  font-size: 0.9rem;
  color: #ff4444; 
}

.user-dropdown li a:hover {
  background-color: #e3f2fd;
}
</style>