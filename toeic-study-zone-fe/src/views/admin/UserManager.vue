<template>
  <div class="container py-4">
    <h2 class="mb-4 fw-bold">Quản lý người dùng</h2>

    <!-- Tìm kiếm -->
    <div class="mb-3 d-flex">
      <input
        type="text"
        class="form-control me-2"
        v-model="searchKeyword"
        placeholder="Tìm kiếm theo tên hoặc email"
      />
      <button class="btn btn-primary" @click="fetchUsers">Tìm kiếm</button>
    </div>

    <!-- Bảng danh sách -->
    <table class="table table-bordered table-hover">
      <thead class="table-light">
        <tr>
          <th>#</th>
          <th>Họ tên</th>
          <th>Email</th>
          <th>SĐT</th>
          <th>Vai trò</th>
          <th>Trạng thái</th>
          <th>Hành động</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(user, index) in users" :key="user.id">
          <td>{{ index + 1 }}</td>
          <td>{{ user.fullName }}</td>
          <td>{{ user.email }}</td>
          <td>{{ user.phoneNumber }}</td>
          <td>{{ user.role }}</td>
          <td>
            <span :class="getStatusClass(user.status)">
              {{ user.status }}
            </span>
          </td>
          <td>
            <button class="btn btn-sm btn-outline-primary me-1">Sửa</button>
            <button class="btn btn-sm btn-outline-danger">Xóa</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref } from "vue";

const users = ref([]);
const searchKeyword = ref("");

const fetchUsers = () => {
  // gọi API sau này
  users.value = [
    {
      id: 1,
      fullName: "Lê Thị Ánh Ngọc",
      email: "ngoclee22803@gmail.com",
      phoneNumber: "0394446103",
      role: "Admin",
      status: "ACTIVE",
    },
  ];
};

const getStatusClass = (status) => {
  switch (status) {
    case "ACTIVE":
      return "badge bg-success";
    case "INACTIVE":
      return "badge bg-secondary";
    case "BANNED":
      return "badge bg-danger";
    default:
      return "";
  }
};

fetchUsers();
</script>

<style scoped>
.container {
  max-width: 1000px;
}
</style>
