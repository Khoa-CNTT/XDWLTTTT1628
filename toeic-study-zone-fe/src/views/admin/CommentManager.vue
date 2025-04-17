<template>
  <div class="p-4">
    <h3 class="fw-bold mb-4">
      <i class="bi bi-chat-dots-fill me-2"></i> Quản lý bình luận
    </h3>

    <!-- Tìm kiếm -->
    <div class="mb-3 d-flex">
      <input
        type="text"
        class="form-control me-2"
        v-model="searchKeyword"
        placeholder="Tìm kiếm theo bình luận hoặc tên người dùng"
      />
      <button class="btn btn-primary" @click="fetchUsers">Tìm kiếm</button>
    </div>

    <div class="table-responsive">
      <table class="table table-bordered align-middle shadow-sm">
        <thead class="table-light">
          <tr>
            <th>STT</th>
            <th>Người dùng</th>
            <th>Bài thi</th>
            <th>Nội dung</th>
            <th>Ngày bình luận</th>
            <th>Trạng thái</th>
            <th>Thao tác</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(comment, index) in comments" :key="comment.id">
            <td>{{ index + 1 }}</td>
            <td class="fw-semibold">{{ comment.userName }}</td>
            <td>{{ comment.testTitle }}</td>
            <td>{{ comment.text }}</td>
            <td>{{ formatDate(comment.createdAt) }}</td>
            <td>
              <span :class="statusClass(comment.status)">{{
                comment.status
              }}</span>
            </td>
            <td>
              <button
                class="btn btn-sm btn-danger"
                @click="deleteComment(comment.id)"
              >
                Xóa
              </button>
            </td>
          </tr>
          <tr v-if="comments.length === 0">
            <td colspan="7" class="text-center text-muted fst-italic">
              Chưa có bình luận nào.
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const comments = ref([
  {
    id: 1,
    userName: "Lê Thị Ánh Ngọc",
    testTitle: "New Economy TOEIC Test 1",
    text: "Bài rất hay",
    createdAt: "2025-04-22T10:00:00Z",
    status: "INACTIVE",
  },
]);

function deleteComment(id) {
  if (confirm("Bạn có chắc chắn muốn xóa bình luận này không?")) {
    comments.value = comments.value.filter((c) => c.id !== id);
    alert("Bình luận đã bị xóa!");
  }
}

function approveComment(id) {
  const comment = comments.value.find((c) => c.id === id);
  if (comment && confirm("Bạn có chắc chắn muốn duyệt bình luận này?")) {
    comment.status = "Đã duyệt";
    alert("Bình luận đã được duyệt thành công!");
  }
}

function statusClass(status) {
  switch (status) {
    case "INACTIVE":
      return "badge bg-warning text-dark";
    case "ACTIVE":
      return "badge bg-success";
    case "Bị từ chối":
      return "badge bg-danger";
    default:
      return "badge bg-secondary";
  }
}

function formatDate(isoString) {
  const date = new Date(isoString);
  return date.toLocaleDateString("vi-VN"); // định dạng kiểu Việt Nam
}
</script>

<style scoped>
table td,
table th {
  vertical-align: middle;
}
</style>
