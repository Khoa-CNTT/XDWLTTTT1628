<template>
  <form @submit.prevent="handleSubmit" class="input-all">
    <div class="mb-3">
      <input
        type="text"
        v-model="form.full_name"
        class="form-control"
        placeholder="Họ và tên *"
        required
      />
    </div>

    <div class="mb-3">
      <input
        type="email"
        v-model="form.email"
        class="form-control"
        placeholder="Email *"
        required
      />
    </div>

    <div class="mb-3 position-relative">
      <input
        :type="showPassword ? 'text' : 'password'"
        v-model="form.password"
        class="form-control"
        placeholder="Mật khẩu *"
        required
      />
      <span class="eye-icon" @click="togglePasswordVisibility">
        <i :class="showPassword ? 'fas fa-eye' : 'fas fa-eye-slash'"></i>
      </span>
    </div>

    <div class="mb-3 position-relative">
      <input
        :type="showConfirmPassword ? 'text' : 'password'"
        v-model="form.confirm_password"
        class="form-control"
        placeholder="Nhập lại mật khẩu *"
        required
      />
      <span class="eye-icon" @click="toggleConfirmPasswordVisibility">
        <i :class="showConfirmPassword ? 'fas fa-eye' : 'fas fa-eye-slash'"></i>
      </span>
    </div>

    <div class="mb-3">
      <label class="form-label">
        Ngày sinh *<span class="info-icon">ⓘ</span>
      </label>
      <div class="row">
        <div class="col">
          <select v-model="form.day" class="form-select" required>
            <option value="" disabled>Ngày</option>
            <option v-for="day in 31" :key="day" :value="day">{{ day }}</option>
          </select>
        </div>
        <div class="col">
          <select v-model="form.month" class="form-select" required>
            <option value="" disabled>Tháng</option>
            <option v-for="month in 12" :key="month" :value="month">
              {{ month }}
            </option>
          </select>
        </div>
        <div class="col">
          <select v-model="form.year" class="form-select" required>
            <option value="" disabled>Năm</option>
            <option v-for="year in years" :key="year" :value="year">
              {{ year }}
            </option>
          </select>
        </div>
      </div>
    </div>

    <button type="submit" class="btn btn-success w-100" :disabled="loading">
      {{ loading ? "Đang xử lý..." : "Đăng ký" }}
    </button>
    <div class="text-center mt-2" v-if="errorMessage">
      <p class="text-danger">{{ errorMessage }}</p>
    </div>
    <div class="text-center">
      <p>
        Đã có tài khoản?
        <router-link to="/login" class="text-decoration-none">
          Đăng nhập
        </router-link>
      </p>
    </div>
  </form>
</template>

<script setup>
import { ref, computed } from "vue";
import { useAuthStore } from "@/store/useAuthStore";
import authService from "@/services/authService";

const authStore = useAuthStore();

// Dữ liệu form
const form = ref({
  full_name: "",
  email: "",
  password: "",
  confirm_password: "",
  day: "",
  month: "",
  year: "",
});

const loading = ref(false);
const errorMessage = ref("");

// Trạng thái ẩn/hiện mật khẩu
const showPassword = ref(false);
const showConfirmPassword = ref(false);

const togglePasswordVisibility = () => {
  showPassword.value = !showPassword.value;
};

const toggleConfirmPasswordVisibility = () => {
  showConfirmPassword.value = !showConfirmPassword.value;
};

// Danh sách năm sinh
const currentYear = new Date().getFullYear();
const years = computed(() => {
  const yearsArray = [];
  for (let i = currentYear; i >= currentYear - 100; i--) {
    yearsArray.push(i);
  }
  return yearsArray;
});

// Gửi dữ liệu
const emit = defineEmits(["submit"]);
const handleSubmit = async () => {
  loading.value = true;
  errorMessage.value = "";

  if (form.value.password !== form.value.confirm_password) {
    errorMessage.value = "Mật khẩu nhập lại không khớp!";
    loading.value = false;
    return;
  }

  if (!form.value.day || !form.value.month || !form.value.year) {
    errorMessage.value = "Vui lòng chọn ngày sinh!";
    loading.value = false;
    return;
  }

  const formatNumber = (n) => String(n).padStart(2, "0");
  const birthDate = `${formatNumber(form.value.day)}-${formatNumber(
    form.value.month
  )}-${form.value.year}`;

  const userData = {
    username: form.value.full_name, // Sử dụng full_name làm username
    fullName: form.value.full_name,
    email: form.value.email,
    password: form.value.password,
    birthDate,
  };

  try {
    const response = await authService.register(userData);
    console.log("Đăng ký thành công:", response.data);
    await authStore.register(userData);
    emit("submit", response.data);
  } catch (error) {
    errorMessage.value =
      error.response?.data?.message || "Đăng ký thất bại, vui lòng thử lại!";
    console.error("Lỗi đăng ký:", error);
  } finally {
    loading.value = false;
  }
};
</script>

<style lang="scss" scoped>
.input-all {
  width: 360px;
  margin: 0 auto;
  padding: 0.2rem;
  box-sizing: border-box;
}

.form-control,
.form-select {
  border-radius: 6px;
  border: 1px solid #ccd0d5;
  padding: 0.75rem;
  font-size: 1rem;
  background-color: #f5f6f7;
  font-weight: 500;
  color: #4a4a4a;
  width: 100%;
}

.form-control {
  padding-right: 40px;
}

.form-select {
  background-color: #fff;
}

.position-relative {
  position: relative;
}

.eye-icon {
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
  color: #606770;
  font-size: 1rem;
  display: flex;
  align-items: center;
}

.form-label {
  font-size: 1rem;
  color: #606770;
  margin-bottom: 0.45rem;
  text-align: left;
  display: flex;
  padding-left: 5px;
}

.info-icon {
  color: #606770;
  font-size: 1rem;
  margin-left: 0.25rem;
}

.btn-success {
  background-color: #00a400;
  border: none;
  padding: 0.75rem;
  font-size: 1.0625rem;
  font-weight: 700;
  border-radius: 6px;
  margin-top: 1rem;
  width: 100%;
}

.btn-success:hover {
  background-color: #008c00;
}

.btn-success:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.text-danger {
  color: #dc3545;
  font-weight: 500;
}

.row {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.col {
  width: 100%;
}

@media (min-width: 576px) {
  .row {
    flex-direction: row;
    gap: 1rem;
  }

  .col {
    width: 100%;
  }
}
</style>