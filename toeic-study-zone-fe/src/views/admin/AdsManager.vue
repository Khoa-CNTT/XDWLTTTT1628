<template>
  <div class="p-4">
    <h3 class="fw-bold mb-4">
      <i class="bi bi-megaphone-fill me-2"></i> Quản lý quảng cáo
    </h3>

    <div class="text-end mb-3">
      <button class="btn btn-primary" @click="showCreateModal = true">
        <i class="bi bi-plus-circle me-1"></i> Tạo quảng cáo
      </button>
    </div>

    <div class="table-responsive">
      <table class="table table-bordered align-middle shadow-sm">
        <thead class="table-light">
          <tr>
            <th>#</th>
            <th>Tiêu đề</th>
            <th>Hình ảnh</th>
            <th>Ngày bắt đầu</th>
            <th>Ngày kết thúc</th>
            <th>Trạng thái</th>
            <th>Thao tác</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(ad, index) in ads" :key="ad.id">
            <td>{{ index + 1 }}</td>
            <td>{{ ad.title }}</td>
            <td><img :src="ad.imageUrl" class="img-thumbnail" width="80" /></td>
            <td>{{ formatDate(ad.startDate) }}</td>
            <td>{{ formatDate(ad.endDate) }}</td>
            <td>
              <span
                :class="ad.isActive ? 'badge bg-success' : 'badge bg-secondary'"
              >
                {{ ad.isActive ? "Đang hoạt động" : "Ngưng hoạt động" }}
              </span>
            </td>
            <td>
              <div class=" gap-2">
                <button
                  class="btn btn-info btn-sm text-white"
                  @click="viewDetail(ad)"
                >
                  Xem
                </button>
                <button class="btn btn-danger btn-sm" @click="deleteAd(ad.id)">
                   Xóa
                </button>
              </div>
            </td>
          </tr>
          <tr v-if="ads.length === 0">
            <td colspan="7" class="text-center text-muted fst-italic">
              Chưa có quảng cáo nào.
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Modal xem chi tiết -->
    <div
      v-if="selectedAd"
      class="modal-backdrop-custom"
      @click.self="selectedAd = null"
    >
      <div class="modal-content-custom p-4">
        <h5 class="fw-bold mb-3">Chi tiết quảng cáo</h5>
        <div class="mb-2"><strong>Tiêu đề:</strong> {{ selectedAd.title }}</div>
        <div class="mb-2">
          <strong>Hình ảnh:</strong><br /><img
            :src="selectedAd.imageUrl"
            class="img-fluid rounded"
          />
        </div>
        <div class="mb-2">
          <strong>Mô tả:</strong><br />{{ selectedAd.description }}
        </div>
        <div class="mb-2">
          <strong>Trạng thái:</strong>
          <span :class="selectedAd.isActive ? 'text-success' : 'text-muted'">
            {{ selectedAd.isActive ? "Đang hoạt động" : "Ngưng hoạt động" }}
          </span>
        </div>
        <div class="text-end">
          <button class="btn btn-secondary mt-3" @click="selectedAd = null">
            Đóng
          </button>
        </div>
      </div>
    </div>

    <!-- Modal tạo quảng cáo -->
    <div
      v-if="showCreateModal"
      class="modal-backdrop-custom"
      @click.self="showCreateModal = false"
    >
      <div class="modal-content-custom p-4">
        <h5 class="fw-bold mb-3">Tạo quảng cáo mới</h5>
        <form @submit.prevent="createAd">
          <input
            v-model="newAd.title"
            class="form-control mb-2"
            placeholder="Tiêu đề"
            required
          />
          <input
            v-model="newAd.imageUrl"
            class="form-control mb-2"
            placeholder="URL hình ảnh"
            required
          />
          <textarea
            v-model="newAd.description"
            class="form-control mb-2"
            placeholder="Mô tả"
          ></textarea>
          <input
            v-model="newAd.linkUrl"
            class="form-control mb-2"
            placeholder="URL đích"
          />

          <label class="form-label text-start d-block">Ngày chiếu</label>
          <input
            v-model="newAd.startDate"
            type="date"
            class="form-control mb-2"
            required
          />

          <label class="form-label text-start d-block">Ngày hết hạn</label>
          <input
            v-model="newAd.endDate"
            type="date"
            class="form-control mb-3"
            required
          />
          <div class="text-end">
            <button
              class="btn btn-secondary me-2"
              @click="showCreateModal = false"
            >
              Hủy
            </button>
            <button type="submit" class="btn btn-primary">Lưu</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useAdvertisementStore } from "@/store/advertisementStore";

const adStore = useAdvertisementStore();
const ads = ref([]);
const showCreateModal = ref(false);
const selectedAd = ref(null);
const newAd = ref({
  title: "",
  imageUrl: "",
  description: "",
  linkUrl: "",
  startDate: "",
  endDate: "",
});

onMounted(async () => {
  await adStore.fetchAll();
  ads.value = adStore.advertisements || [];
});

const createAd = async () => {
  const payload = {
    ...newAd.value,
    redirectUrl: newAd.value.linkUrl,
  };
  await adStore.create(payload);
  showCreateModal.value = false;
  Object.assign(newAd.value, {
    title: "",
    imageUrl: "",
    description: "",
    linkUrl: "",
    startDate: "",
    endDate: "",
  });
  await adStore.fetchAll();
  ads.value = adStore.advertisements;
};

const deleteAd = async (id) => {
  if (confirm("Xoá quảng cáo này?")) {
    await adStore.delete(id);
    await adStore.fetchAll();
    ads.value = adStore.advertisements;
  }
};

const viewDetail = (ad) => {
  selectedAd.value = ad;
};

const formatDate = (dateStr) => {
  return new Date(dateStr).toLocaleDateString("vi-VN");
};
</script>

<style scoped>
.modal-backdrop-custom {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.3);
  z-index: 1050;
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content-custom {
  background: white;
  border-radius: 8px;
  max-width: 500px;
  width: 100%;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.table img {
  object-fit: cover;
}

.btn {
  min-width: 90px;
  margin-right: 6px;
}

.modal-content-custom img {
  max-height: 250px;
  object-fit: contain;
}

.btn-info {
  background-color: #17a2b8;
  border-color: #17a2b8;
}

.btn-info:hover {
  background-color: #138496;
  border-color: #117a8b;
}

.btn-danger {
  background-color: #dc3545;
  border-color: #dc3545;
}

.btn-danger:hover {
  background-color: #bd2130;
  border-color: #b21f2d;
}



.btn-sm {
  padding: 0.25rem 0.5rem;
  font-size: 0.875rem;
  line-height: 1.5;
  border-radius: 0.2rem;
}

.d-flex.gap-2 > .btn {
  min-width: 90px;
}

.form-label{
  margin-left: 20px;
  font-weight: 600;
}
</style>
