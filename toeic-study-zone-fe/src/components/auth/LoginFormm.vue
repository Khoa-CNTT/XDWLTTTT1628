<template>
  <div class="login-form-container">
    <form @submit.prevent="submitForm">
      <div class="mb-3">
        <label for="email" class="form-label visually-hidden">Email</label>
        <input
          type="email"
          class="form-control"
          id="email"
          placeholder="Nhập email"
          v-model="email"
          required
        />
      </div>
      <div class="mb-3 position-relative">
        <label for="password" class="form-label visually-hidden"
          >Mật khẩu</label
        >
        <input
          :type="showPassword ? 'text' : 'password'"
          class="form-control"
          id="password"
          placeholder="Nhập mật khẩu"
          v-model="password"
          required
        />
        <span class="eye-icon" @click="togglePasswordVisibility">
          <i :class="showPassword ? 'fas fa-eye' : 'fas fa-eye-slash'"></i>
        </span>
      </div>
      <div v-if="error" class="text-danger text-center mb-3">
        {{ error }}
      </div>
      <button
        type="submit"
        class="btn btn-primary w-100 mb-3"
        :disabled="loading"
      >
        {{ loading ? "Đang đăng nhập..." : "Đăng nhập" }}
      </button>
      <div class="text-center">
        <a href="#" class="forgot-password">Quên mật khẩu?</a>
      </div>
      <hr class="my-4" />
      <div class="text-center">
        <button type="button" class="btn btn-success" @click="goToRegister">
          Tạo tài khoản mới
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "../../store/useAuthStore";

const email = ref("");
const password = ref("");
const showPassword = ref(false);
const error = ref(null);
const loading = ref(false);
const router = useRouter();
const authStore = useAuthStore();

const submitForm = async () => {
  loading.value = true;
  error.value = null;

  const formData = {
    email: email.value,
    password: password.value,
  };
  console.log("Sending login request with:", formData);

  const result = await authStore.login(formData);
  console.log("Login response:", result);
  loading.value = false;

  if (result.success) {
    // Chuyển hướng dựa trên role
    if (authStore.role === "ADMIN") {
      router.push("/admin/dashboard");
    } else {
      router.push("/dashboard");
    }
  } else {
    error.value = result.message;
  }
};

const goToRegister = () => {
  router.push("/register");
};

const togglePasswordVisibility = () => {
  showPassword.value = !showPassword.value;
};
</script>

<style lang="scss" scoped>
.login-form-container {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 100%;

  .form-label {
    display: block;
    height: 0;
    margin: 0;
    padding: 0;
    visibility: hidden;
  }

  .form-control {
    height: 50px;
    font-size: 1rem;
    border-radius: 6px;
    border: 1px solid #dcdcdc;
    padding: 0.75rem 40px 0.75rem 0.75rem;
    line-height: 1.5;
    box-sizing: box-sizing;
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

  .btn-primary {
    background-color: #1877f2;
    border: none;
    font-weight: bold;
    font-size: 1.1rem;
    padding: 10px;
    border-radius: 6px;

    &:hover {
      background-color: #166fe5;
    }

    &:disabled {
      background-color: #cccccc;
      cursor: not-allowed;
    }
  }

  .btn-success {
    background-color: #42b72a;
    border: none;
    font-weight: bold;
    font-size: 1rem;
    padding: 10px 20px;
    border-radius: 6px;

    &:hover {
      background-color: #36a420;
    }
  }

  .forgot-password {
    color: #1877f2;
    font-size: 0.9rem;
    text-decoration: none;

    &:hover {
      text-decoration: underline;
      text-underline-offset: 3px;
    }
  }
}
</style>
