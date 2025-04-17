<template>
  <div class="p-4">
    <h3 class="fw-bold mb-4">
      <i class="bi bi-megaphone-fill me-2"></i> Quản lý quảng cáo
    </h3>

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
              <span :class="statusClass(ad.status)">{{ ad.status }}</span>
            </td>
            <td>
              <div class="btn-group btn-group-sm">
                <button class="btn btn-info text-white" @click="viewDetail(ad)">
                  Xem chi tiết
                </button>
                <button
                  class="btn btn-success"
                  @click="approveAd(ad)"
                  v-if="ad.status === 'Chờ duyệt'"
                >
                  Duyệt
                </button>
                <button
                  class="btn btn-warning text-white"
                  @click="rejectAd(ad)"
                  v-if="ad.status === 'Chờ duyệt'"
                >
                  Từ chối
                </button>
                <button class="btn btn-danger" @click="deleteAd(ad.id)">
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
          <strong>Liên kết đích:</strong><br />{{ selectedAd.linkUrl }}
        </div>
        <div class="text-end">
          <button class="btn btn-secondary mt-3" @click="selectedAd = null">
            Đóng
          </button>
        </div>
      </div>
    </div>

    <!-- Modal nhập lý do từ chối -->
    <div
      v-if="rejectionTarget"
      class="modal-backdrop-custom"
      @click.self="rejectionTarget = null"
    >
      <div class="modal-content-custom p-4">
        <h5 class="fw-bold mb-3">Lý do từ chối quảng cáo</h5>
        <textarea
          v-model="rejectionReason"
          rows="4"
          class="form-control mb-3"
          placeholder="Nhập lý do từ chối..."
        ></textarea>
        <div class="text-end">
          <button
            class="btn btn-secondary me-2"
            @click="rejectionTarget = null"
          >
            Hủy
          </button>
          <button class="btn btn-danger" @click="confirmRejection">Gửi</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const ads = ref([
  {
    id: 1,
    title: "Khóa học TOEIC 50% ưu đãi",
    imageUrl: "https://via.placeholder.com/100x60.png?text=Ad",
    description: "Tham gia ngay khóa học TOEIC chất lượng với giá siêu ưu đãi!",
    linkUrl: "https://toeic.example.com",
    startDate: "2025-04-15",
    endDate: "2025-05-15",
    status: "Chờ duyệt",
  },
]);

const selectedAd = ref(null);
const rejectionTarget = ref(null);
const rejectionReason = ref("");

const viewDetail = (ad) => {
  selectedAd.value = ad;
};

const approveAd = (ad) => {
  if (confirm("Bạn có chắc chắn muốn duyệt quảng cáo này?")) {
    ad.status = "Đã duyệt";
    alert("Quảng cáo đã được duyệt thành công!");
  }
};

const rejectAd = (ad) => {
  rejectionTarget.value = ad;
  rejectionReason.value = "";
};

const confirmRejection = () => {
  if (rejectionReason.value.trim() === "") {
    alert("Vui lòng nhập lý do từ chối!");
    return;
  }
  rejectionTarget.value.status = "Bị từ chối";
  alert("Quảng cáo đã bị từ chối!");
  rejectionTarget.value = null;
};

const deleteAd = (id) => {
  if (confirm("Bạn có chắc chắn muốn gỡ bỏ quảng cáo này?")) {
    ads.value = ads.value.filter((ad) => ad.id !== id);
    alert("Quảng cáo đã bị gỡ bỏ!");
  }
};

const formatDate = (dateStr) => {
  const d = new Date(dateStr);
  return d.toLocaleDateString("vi-VN");
};

const statusClass = (status) => {
  if (status === "Chờ duyệt") return "badge bg-secondary";
  if (status === "Đã duyệt") return "badge bg-success";
  if (status === "Bị từ chối") return "badge bg-danger";
  return "";
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
</style>
