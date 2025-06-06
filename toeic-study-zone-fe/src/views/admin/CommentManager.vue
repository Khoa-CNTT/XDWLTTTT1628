<template>
  <div class="p-4">
    <h4 class="fw-bold mb-4">
      <i class="bi bi-chat-dots-fill me-2"></i> Quản lý bình luận
    </h4>

    <!-- Thông báo thành công -->
    <div v-if="successMessage" class="toast-message">
      {{ successMessage }}
    </div>

    <!-- Tìm kiếm -->
    <div class="mb-3 d-flex">
      <input
        type="text"
        class="form-control me-2"
        v-model="searchKeyword"
        placeholder="Tìm kiếm theo bình luận hoặc tên người dùng"
      />
      <button class="btn btn-primary search-btn" @click="fetchComments">
        Tìm kiếm
      </button>
    </div>

    <!-- Bảng danh sách bình luận -->
    <div class="table-responsive">
      <table class="table table-bordered align-middle shadow-sm">
        <thead class="table-light">
          <tr>
            <th>STT</th>
            <th>Người dùng</th>
            <th>Bài thi</th>
            <th>Nội dung</th>
            <th>Thời gian bình luận</th>
            <th>Thao tác</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(comment, index) in comments" :key="comment.id">
            <td>{{ index + 1 }}</td>
            <td class="fw-semibold">{{ comment.userName }}</td>
            <td>{{ comment.testTitle }}</td>
            <td>
              <button
                class="btn btn-sm btn-info"
                data-bs-toggle="modal"
                data-bs-target="#commentDetailModal"
                @click="showCommentDetail(comment.commentText)"
              >
                Chi tiết
              </button>
            </td>
            <td>{{ formatDateTime(comment.createdAt) }}</td>
            <td>
              <button
                class="btn btn-sm btn-danger"
                @click="deleteComment(comment.id)"
              >
                <i class="fas fa-trash me-1"></i> Xóa
              </button>
            </td>
          </tr>
          <tr v-if="comments.length === 0">
            <td colspan="6" class="text-center text-muted fst-italic">
              Không có bình luận nào.
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Modal chi tiết nội dung bình luận -->
    <div
      class="modal fade"
      id="commentDetailModal"
      tabindex="-1"
      aria-labelledby="commentDetailModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="commentDetailModalLabel">
              Nội dung bình luận
            </h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <p>{{ selectedCommentText }}</p>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Đóng
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import userService from "@/services/userService";

const comments = ref([]);
const searchKeyword = ref("");
const successMessage = ref("");
const selectedCommentText = ref("");

// Format thời gian hiển thị
function formatDateTime(isoString) {
  const date = new Date(isoString);
  const day = String(date.getDate()).padStart(2, "0");
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const year = date.getFullYear();
  const hours = String(date.getHours()).padStart(2, "0");
  const minutes = String(date.getMinutes()).padStart(2, "0");
  return `${hours}:${minutes} ${day}/${month}/${year}`;
}

// Hiển thị nội dung chi tiết comment
function showCommentDetail(text) {
  selectedCommentText.value = text;
}

// Xóa bình luận
async function deleteComment(id) {
  const confirmDelete = confirm("Bạn có chắc chắn muốn xóa bình luận này?");
  if (!confirmDelete) return;

  try {
    await userService.deleteComment(id);
    comments.value = comments.value.filter((c) => c.id !== id);
    successMessage.value = " Bình luận đã bị xóa!";
    setTimeout(() => (successMessage.value = ""), 3000);
  } catch (error) {
    console.error("Lỗi khi xóa bình luận:", error);
    alert(" Xóa thất bại. Vui lòng thử lại.");
  }
}

// Tìm kiếm / load bình luận
async function fetchComments() {
  try {
    const res = await userService.getAllComments(searchKeyword.value || "");
    comments.value = res.data;
  } catch (error) {
    console.error("Lỗi khi tải danh sách bình luận:", error);
    comments.value = [];
  }
}

onMounted(() => {
  fetchComments();
});
</script>

<style scoped>
table td,
table th {
  vertical-align: middle;
}

.search-btn {
  white-space: nowrap;
  padding: 0.375rem 1rem;
  font-size: 0.875rem;
  line-height: 1.5;
  width: 15%;
}

.btn-sm {
  padding: 0.25rem 0.5rem;
  font-size: 0.875rem;
}

.toast-message {
  position: fixed;
  top: 20px;
  right: 20px;
  background-color: #28a745;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  z-index: 1000;
  animation: fadeInOut 3s ease-in-out;
}

@keyframes fadeInOut {
  0% {
    opacity: 0;
    transform: translateY(-20px);
  }
  10% {
    opacity: 1;
    transform: translateY(0);
  }
  90% {
    opacity: 1;
    transform: translateY(0);
  }
  100% {
    opacity: 0;
    transform: translateY(-20px);
  }
}
</style>
