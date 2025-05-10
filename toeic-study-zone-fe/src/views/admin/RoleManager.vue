<template>
  <div class="row p-4">
    <!-- Danh sách quyền -->
    <div class="col-md-4">
      <div class="card shadow-sm">
        <div
          class="card-header d-flex justify-content-between align-items-center"
        >
          <strong>Danh sách quyền</strong>
          <button class="btn btn-primary btn-sm" @click="addRole">
            + Thêm quyền
          </button>
        </div>
        <div class="card-body">
          <input
            type="text"
            v-model="searchRole"
            class="form-control mb-3"
            placeholder="Tìm kiếm quyền..."
          />
          <table class="table table-bordered table-hover">
            <thead class="table-light">
              <tr>
                <th>#</th>
                <th>Tên quyền</th>
                <th>Cấp quyền</th>
                <th>Thao tác</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(role, index) in filteredRoles" :key="role.id">
                <td>{{ index + 1 }}</td>
                <td>{{ role.name }}</td>
                <td>
                  <button
                    class="btn btn-info btn-sm w-100"
                    @click="selectRole(role)"
                  >
                    Phân quyền
                  </button>
                </td>
                <td>
                  <div class="btn-group-sm d-flex justify-content-center gap-2">
                    <button
                      class="btn btn-outline-primary btn-sm"
                      @click="editRole(role)"
                      title="Sửa quyền"
                    >
                      <i class="bi bi-pencil-square"></i>
                    </button>
                    <button
                      class="btn btn-outline-danger btn-sm"
                      @click="deleteRole(role.id)"
                      title="Xoá quyền"
                    >
                      <i class="bi bi-trash3"></i>
                    </button>
                  </div>
                </td>
              </tr>
              <tr v-if="filteredRoles.length === 0">
                <td colspan="4" class="text-center text-muted">
                  Không có dữ liệu
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <!-- Danh sách chức năng -->
    <div class="col-md-4">
      <div class="card shadow-sm">
        <div
          class="card-header d-flex justify-content-between align-items-center"
        >
          <strong>Danh sách chức năng</strong>
          <button class="btn btn-success btn-sm" @click="addFunction">
            + Thêm chức năng
          </button>
        </div>
        <div class="card-body">
          <table class="table table-bordered table-hover">
            <thead class="table-light">
              <tr>
                <th>#</th>
                <th>Tên chức năng</th>
                <th>Thao tác</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(func, index) in functions" :key="func.id">
                <td>{{ index + 1 }}</td>
                <td>{{ func.name }}</td>
                <td>
                  <button
                    class="btn btn-outline-success btn-sm w-100"
                    :disabled="
                      selectedRole?.functions.find((f) => f.id === func.id)
                    "
                    @click="assignFunction(func)"
                  >
                    {{
                      selectedRole?.functions.find((f) => f.id === func.id)
                        ? "Đã cấp"
                        : "Cấp quyền"
                    }}
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <!-- Quyền đã phân -->
    <div class="col-md-4">
      <div class="card shadow-sm">
        <div class="card-header">
          <strong
            >Đang phân quyền cho:
            {{ selectedRole?.name || "Chưa chọn" }}</strong
          >
        </div>
        <div class="card-body">
          <table class="table table-bordered">
            <thead class="table-light">
              <tr>
                <th>Chức năng</th>
                <th>Thao tác</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(func, index) in selectedRoleFunctions" :key="index">
                <td>{{ func.name }}</td>
                <td>
                  <button
                    class="btn btn-outline-danger btn-sm w-100"
                    @click="removeFunction(index)"
                  >
                    Xoá
                  </button>
                </td>
              </tr>
              <tr v-if="selectedRoleFunctions.length === 0">
                <td colspan="2" class="text-center text-muted">
                  Chưa có chức năng
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";

const searchRole = ref("");
const roles = ref([
  { id: 1, name: "Admin", functions: [] },
  { id: 2, name: "Người dùng", functions: [] },
]);

const functions = ref([
  { id: 1, name: "Quản lý người dùng" },
  { id: 2, name: "Quản lý bài thi" },
  { id: 3, name: "Xem thống kê" },
  { id: 4, name: "Xóa bình luận" },
  { id: 5, name: "Phân quyền" },
  { id: 6, name: "Quản lý lịch học" },
  { id: 7, name: "Quản lý quảng cáo" },
  { id: 8, name: "Xem lịch sử làm bài" },
]);

const selectedRole = ref(null);
const filteredRoles = computed(() =>
  roles.value.filter((r) =>
    r.name.toLowerCase().includes(searchRole.value.toLowerCase())
  )
);
const selectedRoleFunctions = computed(
  () => selectedRole.value?.functions || []
);

function selectRole(role) {
  selectedRole.value = role;
}

function assignFunction(func) {
  if (
    selectedRole.value &&
    !selectedRole.value.functions.find((f) => f.id === func.id)
  ) {
    selectedRole.value.functions.push(func);
  }
}

function removeFunction(index) {
  if (selectedRole.value) {
    selectedRole.value.functions.splice(index, 1);
  }
}

function addRole() {
  const name = prompt("Nhập tên quyền mới:");
  if (name) {
    roles.value.push({ id: Date.now(), name, functions: [] });
  }
}

function editRole(role) {
  const updated = prompt("Sửa tên quyền:", role.name);
  if (updated) {
    role.name = updated;
  }
}

function deleteRole(id) {
  if (confirm("Bạn có chắc chắn xoá quyền này?")) {
    roles.value = roles.value.filter((r) => r.id !== id);
    if (selectedRole.value?.id === id) selectedRole.value = null;
  }
}

function addFunction() {
  const name = prompt("Nhập tên chức năng mới:");
  if (name) {
    functions.value.push({ id: Date.now(), name });
  }
}
</script>

<style scoped>
.card {
  border-radius: 10px;
}
.table th,
.table td {
  vertical-align: middle;
  text-align: center;
}
.btn-group-sm .btn {
  border-radius: 6px !important;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 32px;
  width: 32px;
  padding: 0;
  font-size: 14px;
  transition: all 0.2s ease-in-out;
}
.btn-outline-primary {
  border-color: #0d6efd;
  color: #0d6efd;
}
.btn-outline-primary:hover {
  background-color: #0d6efd;
  color: white;
  border-color: #0d6efd;
}
.btn-outline-danger {
  border-color: #dc3545;
  color: #dc3545;
}
.btn-outline-danger:hover {
  background-color: #dc3545;
  color: white;
  border-color: #dc3545;
}
</style>