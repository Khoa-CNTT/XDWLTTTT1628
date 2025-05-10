<template>
  <div class="profile-page mb-5">
    <!-- Banner -->
    <div class="cover d-flex align-items-center justify-content-center">
      <img
        src="./../../assets/images/banner-feature.png"
        alt="Cover"
        class="cover-img"
      />
    </div>

    <!-- Avatar + Tên -->
    <div class="container text-center mt-n5">
      <div class="avatar-wrapper position-relative d-inline-block">
        <img
          :src="form.avatarUrl"
          alt="Avatar"
          class="rounded-circle avatar-img border border-white"
        />
      </div>
      <h4 class="fw-bold mt-2">{{ form.fullName }}</h4>
      <span class="badge bg-light text-dark fw-medium">Trang công khai</span>
    </div>

    <!-- Tabs -->
    <div class="container mt-5">
      <ul class="nav nav-tabs justify-content-center mb-3">
        <li class="nav-item">
          <a
            class="nav-link fw-bold"
            :class="{ active: activeTab === 'profile' }"
            href="#"
            @click.prevent="activeTab = 'profile'"
          >
            Thông tin cá nhân
          </a>
        </li>
      </ul>

      <!-- Tab: Thông tin cá nhân -->
      <div v-if="activeTab === 'profile'" class="row justify-content-center">
        <div class="col-lg-10">
          <div class="d-flex bg-white rounded shadow p-4 gap-4">
            <!-- Bên trái -->
            <div class="flex-shrink-0 text-center w-25 border-end pe-4">
              <img
                :src="form.avatarUrl"
                class="rounded-circle mb-3"
                style="width: 100px; height: 100px"
              />
              <h5 class="fw-bold mb-1">{{ form.fullName }}</h5>
              <p class="text-muted mb-1">{{ form.email }}</p>
              <small class="text-muted">{{ formatDate(form.dob) }}</small>
              <div class="mt-2">
                <button
                  class="btn btn-outline-primary btn-sm"
                  @click="triggerUpload"
                >
                  Cập nhật ảnh đại diện
                </button>
                <input
                  ref="fileInput"
                  type="file"
                  accept="image/*"
                  class="d-none"
                  @change="handleAvatarChange"
                />
              </div>
            </div>

            <!-- Bên phải -->
            <div class="flex-grow-1">
              <form @submit.prevent="handleSubmit">
                <div class="mb-3 row">
                  <label class="col-sm-3 col-form-label">Họ và Tên</label>
                  <div class="col-sm-9">
                    <input
                      v-model="form.fullName"
                      type="text"
                      class="form-control"
                    />
                  </div>
                </div>
                <div class="mb-3 row">
                  <label class="col-sm-3 col-form-label">Email</label>
                  <div class="col-sm-9">
                    <input
                      v-model="form.email"
                      type="email"
                      class="form-control"
                      readonly
                    />
                  </div>
                </div>
                <div class="mb-3 row">
                  <label class="col-sm-3 col-form-label">Số Điện Thoại</label>
                  <div class="col-sm-9">
                    <input
                      v-model="form.phone"
                      type="text"
                      class="form-control"
                    />
                  </div>
                </div>
                <div class="mb-4 row">
                  <label class="col-sm-3 col-form-label">Ngày Sinh</label>
                  <div class="col-sm-9">
                    <input
                      v-model="form.dob"
                      type="date"
                      class="form-control"
                    />
                  </div>
                </div>
                <div class="text-end">
                  <button type="submit" class="btn btn-primary px-4">
                    Lưu
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from "vue";

const activeTab = ref("profile");
const fileInput = ref(null);

const form = reactive({
  fullName: "Lê Thị Ánh Ngọc",
  email: "ngoclee22803@gmail.com",
  phone: "0394446103",
  dob: "2003-08-22",
  avatarUrl: new URL("@/assets/images/user-avatar.png", import.meta.url).href,
});

function formatDate(dateStr) {
  const [y, m, d] = dateStr.split("-");
  return `${d}/${m}/${y}`;
}

function handleSubmit() {
  alert("Đã lưu thông tin!");
}

function triggerUpload() {
  fileInput.value.click();
}

function handleAvatarChange(e) {
  const file = e.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = () => {
      form.avatarUrl = reader.result;
    };
    reader.readAsDataURL(file);
  }
}
</script>

<style scoped lang="scss">
.profile-page {
  background-color: #f5f7fa;

  .cover {
    height: 200px;
    background-color: #e8f4ff;
    position: relative;

    .cover-img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      object-position: center;
    }
  }

  .avatar-wrapper {
    .avatar-img {
      width: 100px;
      height: 100px;
      object-fit: cover;
      margin-top: -50px;
    }
  }

  .nav-tabs .nav-link {
    font-size: 1rem;
    padding: 0.75rem 1.5rem;
  }

  .nav-tabs .nav-link.active {
    border-bottom: 2px solid #1877f2;
    color: #1877f2;
  }
}
</style>
