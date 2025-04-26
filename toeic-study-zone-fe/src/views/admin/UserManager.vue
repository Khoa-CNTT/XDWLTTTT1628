<template>
  <div class="container py-4">
    <h4 class="mb-4 fw-bold">Quản lý người dùng</h4>

    <!-- Tìm kiếm -->
    <div class="mb-3 d-flex align-items-center">
      <input
        type="text"
        class="form-control me-2"
        v-model="searchKeyword"
        placeholder="Tìm kiếm theo tên hoặc email"
      />
      <button class="btn btn-primary search-btn" @click="fetchUsers">
        Tìm kiếm
      </button>
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
          <th>Ngày tạo</th>
          <th>Thời gian đăng nhập lần cuối</th>
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
          <td>{{ formatDateTime(user.created_at) }}</td>
          <td>{{ formatDateTime(user.last_login_at || user.created_at) }}</td>
          <td>
            <button
              class="btn btn-sm btn-warning me-2"
              data-bs-toggle="modal"
              data-bs-target="#editUserModal"
              @click="openEditModal(user)"
            >
              <i class="fas fa-edit"></i> Sửa
            </button>
            <button class="btn btn-sm btn-danger" @click="deleteUser(user.id)">
              <i class="fas fa-trash"></i> Xóa
            </button>
          </td>
        </tr>
        <tr v-if="users.length === 0">
          <td colspan="9" class="text-center">Không tìm thấy người dùng.</td>
        </tr>
      </tbody>
    </table>

    <!-- Modal chỉnh sửa người dùng -->
    <div
      class="modal fade"
      id="editUserModal"
      tabindex="-1"
      aria-labelledby="editUserModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="editUserModalLabel">
              Chỉnh sửa người dùng
            </h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="submitEditUser">
              <div class="mb-3">
                <label for="edit-fullName" class="form-label">Họ tên:</label>
                <input
                  type="text"
                  class="form-control"
                  id="edit-fullName"
                  v-model="editUser.fullName"
                  required
                />
              </div>
              <div class="mb-3">
                <label for="edit-email" class="form-label">Email:</label>
                <input
                  type="text"
                  class="form-control"
                  id="edit-email"
                  v-model="editUser.email"
                  required
                />
              </div>
              <div class="mb-3">
                <label for="edit-phoneNumber" class="form-label">SĐT:</label>
                <input
                  type="text"
                  class="form-control"
                  id="edit-phoneNumber"
                  v-model="editUser.phoneNumber"
                  required
                />
              </div>
              <div class="mb-3">
                <label for="edit-role" class="form-label">Vai trò:</label>
                <select
                  class="form-control"
                  id="edit-role"
                  v-model="editUser.role"
                  required
                >
                  <option value="Admin">Admin</option>
                  <option value="User">User</option>
                </select>
              </div>
              <div class="mb-3">
                <label for="edit-status" class="form-label">Trạng thái:</label>
                <select
                  class="form-control"
                  id="edit-status"
                  v-model="editUser.status"
                  required
                >
                  <option value="Hoạt động">Hoạt động</option>
                  <option value="Khóa">Khóa</option>
                </select>
              </div>
              <button type="submit" class="btn btn-primary w-100">
                Lưu thay đổi
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const users = ref([]);
const searchKeyword = ref("");
const editUser = ref({
  id: null,
  fullName: "",
  email: "",
  phoneNumber: "",
  role: "",
  status: "",
  created_at: "",
  last_login_at: "",
});

const fetchUsers = () => {
  // Gọi API sau này
  users.value = [
    {
      id: 1,
      fullName: "Lê Thị Ánh Ngọc",
      email: "ngoclee22803@gmail.com",
      phoneNumber: "0394446103",
      role: "Admin",
      status: "Hoạt động",
      created_at: "2023-01-01T10:00:00Z",
      last_login_at: "2023-01-02T15:30:00Z",
    },
    {
      id: 2,
      fullName: "Nguyễn Long Vũ",
      email: "nguyenlongvu22122003@gmail.com",
      phoneNumber: "0123456789",
      role: "Admin",
      status: "Khóa",
      created_at: "2023-02-01T09:00:00Z",
      last_login_at: null,
    },
  ];
};

const getStatusClass = (status) => {
  switch (status) {
    case "Hoạt động":
      return "badge bg-success";
    case "Khóa":
      return "badge bg-secondary";
    default:
      return "";
  }
};

// Hàm định dạng ngày giờ
const formatDateTime = (date) => {
  if (!date) return "";
  return new Date(date).toLocaleString("vi-VN", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  });
};

// Hàm mở modal chỉnh sửa
const openEditModal = (user) => {
  editUser.value = { ...user };
};

// Hàm xóa người dùng
const deleteUser = (userId) => {
  if (confirm("Bạn có chắc chắn muốn xóa người dùng này?")) {
    users.value = users.value.filter((user) => user.id !== userId);
    alert("Xóa người dùng thành công!");
  }
};

// Hàm lưu thay đổi sau khi chỉnh sửa
const submitEditUser = () => {
  // Lấy thông tin gốc của user để giữ nguyên created_at và last_login_at
  const originalUser = users.value.find(
    (user) => user.id === editUser.value.id
  );
  const updatedUser = {
    ...editUser.value,
    created_at: originalUser.created_at, // Giữ nguyên created_at
    last_login_at: originalUser.last_login_at, // Giữ nguyên last_login_at
  };

  users.value = users.value.map((user) =>
    user.id === updatedUser.id ? updatedUser : user
  );
  alert("Cập nhật người dùng thành công!");
  const modal = document.getElementById("editUserModal");
  const bootstrapModal = bootstrap.Modal.getInstance(modal);
  bootstrapModal.hide();
};

fetchUsers();
</script>

<style scoped>
.container {
  width: 100%;
  max-width: 1500px;
}

.table th,
.table td {
  vertical-align: middle;
  text-align: center;
}

.badge {
  padding: 6px 10px;
  border-radius: 12px;
  font-size: 0.875rem;
  font-weight: 400;
}

.modal-dialog {
  max-width: 500px;
}

.modal-content {
  border-radius: 8px;
}

.modal-header {
  background-color: #f8f9fa;
  border-bottom: 1px solid #dee2e6;
}

.modal-title {
  font-size: 1.25rem;
  font-weight: 600;
}

.form-label {
  font-size: 1rem;
  font-weight: 600;
  text-align: left;
  display: block;
}

.btn-sm {
  padding: 0.25rem 0.5rem;
  font-size: 0.875rem;
}

.search-btn {
  white-space: nowrap;
  padding: 0.375rem 1rem;
  font-size: 0.875rem;
  line-height: 1.5;
  width: 15%;
}
</style>
