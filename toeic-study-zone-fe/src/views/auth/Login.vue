<template>
  <div class="auth-container">
    <div class="container-fluid h-100 px-0">
      <div class="row h-100 align-items-center mx-0">
        <!-- Phần bên trái: Logo và mô tả -->
        <div class="col-md-6 d-flex justify-content-start">
          <div class="content-wrapper">
            <h1 class="study-zone-title">Study Zone</h1>
            <p class="study-zone-subtitle">
              Nền tảng luyện thi <span class="toeic-highlight">TOEIC</span> trực
              tuyến giúp bạn <br />
              chinh phục mục tiêu.
            </p>
            <img :src="logo" alt="Study Zone Illustration" class="logo-image" />
          </div>
        </div>

        <!-- Phần bên phải: Form đăng nhập -->
        <div
          class="col-md-6 d-flex justify-content-center justify-content-md-end"
        >
          <div class="form-wrapper">
            <h2 class="form-title">Đăng nhập</h2>
            <login-form />
            <p class="text-center mt-4">
              Hãy đăng nhập để được luyện thi TOEIC thoải mái.
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import LoginForm from "@/components/auth/LoginFormm.vue";
import logo from "@/assets/images/logo-study-zone.png";
</script>

<style lang="scss" scoped>
.logo-image {
  max-width: 200px;
  height: auto;
  mix-blend-mode: multiply;
  filter: contrast(1.1);
  margin: 0 0 0 20%;
}

.auth-container {
  margin: 0;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  max-width: 1300px;
}

.container-fluid {
  height: 100%;
  width: 100%;
}

.row {
  height: 100%;
  width: 100%;
}

.content-wrapper {
  max-width: 600px;
  width: 100%;
  text-align: left;
  padding-left: 2rem;
}

.form-wrapper {
  max-width: 450px;
  width: 100%;
  margin-right: 9rem;
}

.study-zone-title {
  font-size: 4rem;
  font-weight: bold;
  color: #1877f2;
}

.toeic-highlight {
  color: #ff5722;
  font-weight: bold;
}

.study-zone-subtitle {
  font-size: 1.5rem;
  color: #333;
  line-height: 1.6;
}

.form-title {
  font-size: 2rem;
  font-weight: bold;
  color: #333;
  text-align: center;
  margin-bottom: 1rem;
}

@media (max-width: 767px) {
  .auth-container {
    flex-direction: column;
    justify-content: center;
    padding: 1rem;
    max-width: 100%;
  }

  .content-wrapper,
  .form-wrapper {
    max-width: 100%;
    padding: 0 15px;
  }

  .content-wrapper {
    padding-left: 15px;
    margin-bottom: 2rem;
  }

  .form-wrapper {
    padding-right: 15px;
  }

  .study-zone-title {
    font-size: 2rem;
  }

  .study-zone-subtitle {
    font-size: 1rem;
  }

  .logo-image {
    max-width: 80%;
  }
}
</style>