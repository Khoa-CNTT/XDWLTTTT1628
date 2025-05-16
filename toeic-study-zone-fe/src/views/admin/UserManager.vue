<template>
  <div class="container py-4">
    <h4 class="mb-4 fw-bold">Quản lý người dùng</h4>
    <button class="btn btn-success mb-3" @click="openCreateModal">
      <i class="fas fa-user-plus"></i> Tạo tài khoản
    </button>

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
          <td>{{ formatDateTime(user.createdAt) }}</td>
          <td>{{ formatDateTime(user.updatedAt || user.createdAt) }}</td>
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
                <label for="edit-fullName" class="form-label">Họ tên: *</label>
                <input
                  type="text"
                  class="form-control"
                  id="edit-fullName"
                  v-model="editUser.fullName"
                />
              </div>
              <div class="mb-3">
                <label for="edit-email" class="form-label">Email: *</label>
                <input
                  type="email"
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
                  pattern="[0-9]{10}"
                  title="Số điện thoại phải đúng 10 số"
                  :class="{ 'is-invalid': phoneNumberError }"
                  @input="validatePhoneNumber('edit')"
                />
                <div v-if="phoneNumberError" class="invalid-feedback">
                  {{ phoneNumberError }}
                </div>
              </div>
              <div class="mb-3">
                <label for="edit-role" class="form-label">Vai trò:</label>
                <select
                  class="form-control"
                  id="edit-role"
                  v-model="editUser.role"
                  required
                >
                  <option value="USER">User</option>
                  <option value="ADMIN">Admin</option>
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

    <div
      v-if="showCreateModal"
      class="fixed-top w-100 h-100 bg-dark bg-opacity-50 d-flex justify-content-center align-items-center"
    >
      <div class="bg-white p-4 border border-dark rounded" style="width: 500px">
        <h5 class="mb-3 fw-bold">Tạo tài khoản</h5>
        <form @submit.prevent="submitCreateUser">
          <div v-if="createError" class="alert alert-danger">
            {{ createError }}
          </div>

          <div class="mb-3">
            <label class="form-label">Họ tên *</label>
            <input
              type="text"
              class="form-control border-dark"
              v-model="createForm.fullName"
              required
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Email *</label>
            <input
              type="email"
              class="form-control border-dark"
              v-model="createForm.email"
              required
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Số điện thoại</label>
            <input
              type="text"
              class="form-control border-dark"
              v-model="createForm.phoneNumber"
              pattern="[0-9]{10}"
              title="Số điện thoại phải đúng 10 số"
              :class="{ 'is-invalid': createPhoneNumberError }"
              @input="validatePhoneNumber('create')"
            />
            <div v-if="createPhoneNumberError" class="invalid-feedback">
              {{ createPhoneNumberError }}
            </div>
          </div>
          <div class="mb-3">
            <label class="form-label">Mật khẩu *</label>
            <input
              type="password"
              class="form-control border-dark"
              v-model="createForm.password"
              required
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Vai trò</label>
            <select
              class="form-select border-dark"
              v-model="createForm.role"
              required
            >
              <option value="USER">User</option>
              <option value="ADMIN">Admin</option>
            </select>
          </div>
          <div class="mb-3">
            <label class="form-label">Trạng thái</label>
            <select
              class="form-select border-dark"
              v-model="createForm.status"
              required
            >
              <option value="Hoạt động">Hoạt động</option>
              <option value="Khóa">Khóa</option>
            </select>
          </div>

          <div class="d-flex justify-content-end mt-4">
            <button
              type="button"
              class="btn btn-secondary me-2"
              @click="closeCreateModal"
            >
              Đóng
            </button>
            <button type="submit" class="btn btn-primary">Tạo</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import userService from "@/services/userService";

const users = ref([]);
const searchKeyword = ref("");
const editUser = ref({
  id: null,
  fullName: "",
  email: "",
  phoneNumber: "",
  role: "",
  status: "",
  createdAt: "",
  updatedAt: "",
});
const showCreateModal = ref(false);
const createError = ref("");
const createForm = ref({
  fullName: "",
  email: "",
  phoneNumber: "",
  password: "",
  role: "USER",
  status: "Hoạt động",
});
const phoneNumberError = ref("");
const createPhoneNumberError = ref("");

const openCreateModal = () => {
  createForm.value = {
    fullName: "",
    email: "",
    phoneNumber: "",
    password: "",
    role: "USER",
    status: "Hoạt động",
  };
  createError.value = "";
  createPhoneNumberError.value = "";
  showCreateModal.value = true;
};

const closeCreateModal = () => {
  showCreateModal.value = false;
};

const validatePhoneNumber = (type) => {
  const phone =
    type === "edit" ? editUser.value.phoneNumber : createForm.value.phoneNumber;
  const errorRef = type === "edit" ? phoneNumberError : createPhoneNumberError;

  if (!phone) {
    errorRef.value = "";
    return;
  }

  if (!/^\d{10}$/.test(phone)) {
    errorRef.value = "Số điện thoại phải đúng 10 số và chỉ chứa chữ số";
  } else {
    errorRef.value = "";
  }
};

const submitCreateUser = async () => {
  try {
    validatePhoneNumber("create");
    if (createPhoneNumberError.value) {
      createError.value = createPhoneNumberError.value;
      return;
    }

    const userData = {
      username: createForm.value.email.split("@")[0],
      fullName: createForm.value.fullName || null,
      email: createForm.value.email,
      phoneNumber: createForm.value.phoneNumber || null,
      password: createForm.value.password,
      role: createForm.value.role,
      status: createForm.value.status,
    };
    console.log("Dữ liệu gửi lên khi tạo:", JSON.stringify(userData, null, 2));
    await userService.createUser(userData);
    showCreateModal.value = false;
    alert("Tạo tài khoản thành công!");
    await fetchUsers();
  } catch (error) {
    createError.value =
      error.response?.data?.error || "Tạo tài khoản thất bại!";
    console.error(
      "Error creating user:",
      error.response?.data || error.message
    );
  }
};

const fetchUsers = async () => {
  try {
    const response = await userService.getAllUsers(searchKeyword.value);
    console.log("Dữ liệu từ API:", response.data);
    users.value = response.data.map((user) => ({
      ...user,
      role: user.role || "Chưa có vai trò",
      fullName: user.fullName || "Chưa có họ tên",
      phoneNumber: user.phoneNumber || "Chưa có SĐT",
      status: user.status || "Chưa xác định",
    }));
  } catch (error) {
    console.error(
      "Lỗi khi lấy danh sách người dùng:",
      error.response?.data || error.message
    );
    users.value = [];
  }
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

const openEditModal = (user) => {
  editUser.value = {
    ...user,
    role: user.role === "Chưa có vai trò" ? "USER" : user.role.toUpperCase(),
    status: user.status || "Hoạt động",
  };
  phoneNumberError.value = "";
};

const deleteUser = async (userId) => {
  if (confirm("Bạn có chắc chắn muốn xóa người dùng này?")) {
    try {
      console.log("Xóa user với ID:", userId);
      await userService.deleteUser(userId);
      users.value = users.value.filter((user) => user.id !== userId);
      alert("Xóa người dùng thành công!");
    } catch (error) {
      alert(
        "Xóa người dùng thất bại: " +
          (error.response?.data?.error || "Lỗi không xác định")
      );
      console.error(
        "Error deleting user:",
        error.response?.data || error.message
      );
    }
  }
};

const submitEditUser = async () => {
  try {
    validatePhoneNumber("edit");
    if (phoneNumberError.value) {
      alert(phoneNumberError.value);
      return;
    }

    const userData = {
      fullName: editUser.value.fullName || null,
      email: editUser.value.email,
      phoneNumber: editUser.value.phoneNumber || null,
      role: editUser.value.role,
      status: editUser.value.status,
    };
    console.log("Dữ liệu gửi lên khi sửa:", JSON.stringify(userData, null, 2));
    const response = await userService.updateUser(editUser.value.id, userData);
    console.log("Cập nhật user thành công:", response.data);
    users.value = users.value.map((user) =>
      user.id === editUser.value.id ? { ...user, ...response.data } : user
    );
    alert("Cập nhật người dùng thành công!");
    const modal = document.getElementById("editUserModal");
    modal.classList.remove("show");
    modal.style.display = "none";
    document.body.classList.remove("modal-open");
    const backdrop = document.querySelector(".modal-backdrop");
    if (backdrop) backdrop.remove();
  } catch (error) {
    const errorMessage =
      error.response?.data?.error || error.message || "Lỗi không xác định";
    alert(`Cập nhật thất bại: ${errorMessage}`);
    console.error("Error updating user:", error.response?.data || error);
  }
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
