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
          :src="form.avatarUrl || defaultAvatar"
          alt="Avatar"
          class="rounded-circle avatar-img border border-white"
          @error="handleImageError"
        />
      </div>
      <h4 class="fw-bold mt-2">{{ form.fullName || 'Chưa có tên' }}</h4>
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
                :src="form.avatarUrl || defaultAvatar"
                class="rounded-circle mb-3"
                style="width: 100px; height: 100px"
                @error="handleImageError"
              />
              <h5 class="fw-bold mb-1">{{ form.fullName || 'Chưa có tên' }}</h5>
              <p class="text-muted mb-1">{{ form.email || 'Chưa có email' }}</p>
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
                      :class="{ 'is-invalid': phoneError }"
                      @input="validatePhoneNumber"
                    />
                    <div v-if="phoneError" class="invalid-feedback">
                      {{ phoneError }}
                    </div>
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
                  <button
                    type="submit"
                    class="btn btn-primary px-4"
                  >
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
import { ref, reactive, onMounted } from "vue";
import userService from "@/services/userService";

const activeTab = ref("profile");
const fileInput = ref(null);
const phoneError = ref("");
const defaultAvatar = new URL("@/assets/images/user-avatar.png", import.meta.url).href;

const form = reactive({
  id: null,
  fullName: "",
  email: "",
  phone: "",
  dob: "",
  avatarUrl: defaultAvatar,
  avatarFile: null,
});

const validatePhoneNumber = () => {
  if (!form.phone) {
    phoneError.value = "";
    return true;
  }
  if (!/^\d{10}$/.test(form.phone)) {
    phoneError.value = "Số điện thoại phải đúng 10 số và chỉ chứa chữ số";
    return false;
  }
  phoneError.value = "";
  return true;
};

const fetchUserProfile = async () => {
  try {
    const authData = localStorage.getItem('auth');
    if (!authData) {
      throw new Error("No auth data found in localStorage");
    }
    const { token } = JSON.parse(authData);
    if (!token) {
      throw new Error("No token found in auth data");
    }
    const response = await userService.getCurrentUser();
    console.log("User profile response:", response.data); // Log chi tiết response
    Object.assign(form, {
      id: response.data.id,
      fullName: response.data.fullName || "",
      email: response.data.email || "",
      phone: response.data.phoneNumber || "",
      dob: response.data.birthDate || "",
      avatarUrl: response.data.avatarUrl || defaultAvatar,
    });
  } catch (error) {
    console.error("Error fetching user profile:", error.response?.data || error.message);
    alert("Không thể tải thông tin tài khoản: " + (error.response?.data?.error || error.message));
  }
};

const handleSubmit = async () => {
  try {
    if (!validatePhoneNumber()) {
      return;
    }

    let avatarUrl = null;

    if (form.avatarFile) {
      const formData = new FormData();
      formData.append('file', form.avatarFile);
      const uploadResponse = await userService.uploadAvatar(formData);
      avatarUrl = uploadResponse.data.url;
      console.log("Avatar uploaded URL:", avatarUrl);
    } else {
      avatarUrl = form.avatarUrl === defaultAvatar ? null : form.avatarUrl;
    }

    const userData = {
      fullName: form.fullName || null,
      email: form.email,
      phoneNumber: form.phone || null,
      birthDate: form.dob || null,
      avatarUrl: avatarUrl,
    };
    console.log("Dữ liệu gửi lên:", JSON.stringify(userData, null, 2));

    const response = await userService.updateCurrentUser(userData);
    console.log("Response from updateCurrentUser:", response.data); // Log chi tiết response

    Object.assign(form, {
      id: response.data.id,
      fullName: response.data.fullName || "",
      email: response.data.email || "",
      phone: response.data.phoneNumber || "",
      dob: response.data.birthDate || "",
      avatarUrl: response.data.avatarUrl || defaultAvatar,
      avatarFile: null,
    });
    console.log("Final avatarUrl in form after update:", form.avatarUrl);

    if (form.avatarUrl && form.avatarUrl !== defaultAvatar) {
      form.avatarUrl = form.avatarUrl + '?t=' + new Date().getTime();
    }

    alert("Đã lưu thông tin!");
  } catch (error) {
    const errorMessage = error.response?.data?.error || error.message || "Lỗi không xác định";
    alert(`Cập nhật thất bại: ${errorMessage}`);
    console.error("Error updating user:", error.response?.data || error);
  }
};

const handleImageError = (event) => {
  console.error("Failed to load image:", form.avatarUrl);
  form.avatarUrl = defaultAvatar;
};

const triggerUpload = () => {
  fileInput.value.click();
};

const handleAvatarChange = (e) => {
  const file = e.target.files[0];
  if (file) {
    form.avatarFile = file;
    const reader = new FileReader();
    reader.onload = () => {
      form.avatarUrl = reader.result;
    };
    reader.readAsDataURL(file);
  }
};

const formatDate = (dateStr) => {
  if (!dateStr) return "";
  const [y, m, d] = dateStr.split("-");
  return `${d}/${m}/${y}`;
};

onMounted(() => {
  fetchUserProfile();
});
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