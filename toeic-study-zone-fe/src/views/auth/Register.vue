<template>
  <div class="register-container">
    <h1 class="study-zone-logo">Study Zone</h1>
    <div class="register-form">
      <h2 class="register-title">Tạo tài khoản mới</h2>
      <p class="register-subtitle">Nhanh chóng và dễ dàng.</p>
      <hr class="divider" />

      <register-form @submit="handleRegister" />
      <div class="text-center mt-2" v-if="successMessage">
        <p class="text-success">{{ successMessage }}</p>
      </div>
      <div class="text-center mt-2" v-if="errorMessage">
        <p class="text-danger">{{ errorMessage }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/store/useAuthStore";
import RegisterForm from "@/components/auth/RegisterForm.vue";

const router = useRouter();
const authStore = useAuthStore();
const successMessage = ref("");
const errorMessage = ref("");

const handleRegister = async (responseData) => {
  try {
    if (responseData.success) {
      successMessage.value = "Đăng ký thành công! Đang chuyển hướng...";
      setTimeout(() => {
        router.push("/dashboard"); // Chuyển hướng sau khi đăng ký
      }, 2000);
    } else {
      errorMessage.value = responseData.message || "Đăng ký thất bại!";
    }
  } catch (error) {
    errorMessage.value = "Có lỗi xảy ra, vui lòng thử lại!";
    console.error("Lỗi xử lý đăng ký:", error);
  }
};
</script>

<style lang="scss" scoped>
.register-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f0f2f5;
  padding: 1rem;
}

.study-zone-logo {
  font-size: 3rem;
  font-weight: 700;
  color: #1877f2;
  text-align: center;
  margin-bottom: 1rem;
}

.register-form {
  background-color: #fff;
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1), 0 8px 16px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 432px;
  box-sizing: border-box;
}

.register-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: #1c1e21;
  text-align: center;
  margin-bottom: 0.25rem;
}

.register-subtitle {
  font-size: 1rem;
  color: #606770;
  text-align: center;
  margin-bottom: 1rem;
}

.divider {
  border: 0;
  border-top: 1px solid #dadde1;
  margin: 0.5rem 0 1rem 0;
}

.text-success {
  color: #00a400;
  font-weight: 500;
}

.text-danger {
  color: #dc3545;
  font-weight: 500;
}

/* Responsive */
@media (max-width: 768px) {
  .study-zone-logo {
    font-size: 2.5rem;
  }

  .register-form {
    padding: 1rem;
  }

  .register-title {
    font-size: 1.25rem;
  }

  .register-subtitle {
    font-size: 0.9375rem;
  }
}

@media (max-width: 480px) {
  .register-form {
    padding: 0.75rem;
    border-radius: 6px;
  }

  .study-zone-logo {
    font-size: 2rem;
  }

  .register-title {
    font-size: 1.125rem;
  }

  .register-subtitle {
    font-size: 0.875rem;
  }
}
</style>