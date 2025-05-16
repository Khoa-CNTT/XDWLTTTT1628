<template>
  <div class="test-bank container-fluid py-4">
    <h4 class="mb-4 fw-bold">Ngân hàng bài thi</h4>

    <!-- Tìm kiếm + nút thêm -->
    <div class="row mb-4">
      <div class="col-md-8">
        <div class="input-group">
          <input
            type="text"
            class="form-control"
            placeholder="Tìm kiếm bài thi theo tiêu đề hoặc năm..."
            v-model="searchQuery"
          />
          <button class="btn btn-outline-secondary" type="button">
            <i class="fas fa-search"></i>
          </button>
        </div>
      </div>
      <div class="col-md-4 text-end">
        <button
          class="btn btn-primary"
          data-bs-toggle="modal"
          data-bs-target="#addTestModal"
        >
          <i class="fas fa-plus me-2"></i>Thêm danh mục bài thi mới
        </button>
      </div>
    </div>

    <!-- Danh sách bài thi -->
    <div class="table-responsive">
      <table class="table table-bordered table-hover">
        <thead class="table-light">
          <tr>
            <th>#</th>
            <th>Tiêu đề</th>
            <th>Năm</th>
            <th>Số câu hỏi</th>
            <th>Thời gian giới hạn (phút)</th>
            <th>Miễn phí</th>
            <th>Công khai</th>
            <th>Đánh giá đầu vào</th>
            <th>Ngày tạo</th>
            <th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(test, index) in filteredTests" :key="test.id">
            <td>{{ index + 1 }}</td>
            <td>{{ test.title || "Không có tiêu đề" }}</td>
            <td>{{ test.yearId || "N/A" }}</td>
            <td>{{ test.totalQuestions || 0 }}</td>
            <td>{{ test.timeLimit ? test.timeLimit : "Không giới hạn" }}</td>
            <td>
              <span :class="test.isFree ? 'text-success' : 'text-danger'">
                {{ test.isFree ? "Có" : "Không" }}
              </span>
            </td>
            <td>
              <span :class="test.isPublished ? 'text-success' : 'text-danger'">
                {{ test.isPublished ? "Có" : "Không" }}
              </span>
            </td>
            <td>
              <span :class="test.isPlacementTest ? 'text-success' : 'text-danger'">
                {{ test.isPlacementTest ? "Có" : "Không" }}
              </span>
            </td>
            <td>{{ formatDate(test.createdAt) }}</td>
            <td>
              <button
                class="btn btn-sm btn-warning me-2"
                data-bs-toggle="modal"
                data-bs-target="#editTestModal"
                @click="openEditModal(test)"
              >
                <i class="fas fa-edit"></i> Sửa
              </button>
              <button @click="deleteTest(test.id)" class="btn btn-sm btn-danger">
                <i class="fas fa-trash"></i> Xóa
              </button>
            </td>
          </tr>
          <tr v-if="filteredTests.length === 0">
            <td colspan="10" class="text-center">Không tìm thấy bài thi nào.</td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Modal thêm bài thi -->
    <div class="modal fade" id="addTestModal" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Thêm bài thi mới</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="submitNewTest">
              <div class="mb-3">
                <label class="form-label">Tiêu đề:</label>
                <input v-model="newTest.title" class="form-control" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Mô tả:</label>
                <textarea v-model="newTest.description" class="form-control" rows="3"></textarea>
              </div>
              <div class="mb-3">
                <label class="form-label">Năm:</label>
                <input v-model.number="newTest.yearId" class="form-control" type="number" min="2000" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Số câu hỏi:</label>
                <input v-model.number="newTest.totalQuestions" class="form-control" type="number" min="1" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Thời gian giới hạn (phút):</label>
                <input v-model.number="newTest.timeLimit" class="form-control" type="number" min="1" />
              </div>
              <div class="form-check mb-2">
                <input v-model="newTest.isFree" class="form-check-input" type="checkbox" />
                <label class="form-check-label">Miễn phí</label>
              </div>
              <div class="form-check mb-2">
                <input v-model="newTest.isPublished" class="form-check-input" type="checkbox" />
                <label class="form-check-label">Công khai</label>
              </div>
              <div class="form-check mb-3">
                <input v-model="newTest.isPlacementTest" class="form-check-input" type="checkbox" />
                <label class="form-check-label">Đánh giá đầu vào</label>
              </div>
              <button class="btn btn-primary w-100">Thêm bài thi</button>
            </form>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal chỉnh sửa bài thi -->
    <div class="modal fade" id="editTestModal" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Chỉnh sửa bài thi</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="submitEditTest">
              <div class="mb-3">
                <label class="form-label">Tiêu đề:</label>
                <input v-model="editTest.title" class="form-control" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Mô tả:</label>
                <textarea v-model="editTest.description" class="form-control" rows="3"></textarea>
              </div>
              <div class="mb-3">
                <label class="form-label">Năm:</label>
                <input v-model.number="editTest.yearId" class="form-control" type="number" min="2000" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Số câu hỏi:</label>
                <input v-model.number="editTest.totalQuestions" class="form-control" type="number" min="1" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Thời gian giới hạn (phút):</label>
                <input v-model.number="editTest.timeLimit" class="form-control" type="number" min="1" />
              </div>
              <div class="form-check mb-2">
                <input v-model="editTest.isFree" class="form-check-input" type="checkbox" />
                <label class="form-check-label">Miễn phí</label>
              </div>
              <div class="form-check mb-2">
                <input v-model="editTest.isPublished" class="form-check-input" type="checkbox" />
                <label class="form-check-label">Công khai</label>
              </div>
              <div class="form-check mb-3">
                <input v-model="editTest.isPlacementTest" class="form-check-input" type="checkbox" />
                <label class="form-check-label">Đánh giá đầu vào</label>
              </div>
              <button class="btn btn-primary w-100">Lưu thay đổi</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from "vue";
import adminService from "@/services/adminService";

export default {
  name: "TestBank",
  setup() {
    const tests = ref([]);
    const searchQuery = ref("");

    const newTest = ref({
      title: "",
      description: "",
      yearId: null,
      totalQuestions: null,
      timeLimit: null,
      isFree: false,
      isPublished: false,
      isPlacementTest: false,
    });

    const editTest = ref({ ...newTest.value });

    onMounted(async () => {
      try {
        const res = await adminService.getTests();
        tests.value = res.data || [];
      } catch (e) {
        console.error("Lỗi khi tải bài thi:", e);
      }
    });

    const filteredTests = computed(() => {
      const q = searchQuery.value.toLowerCase();
      return tests.value.filter(
        (t) =>
          t.title?.toLowerCase().includes(q) ||
          String(t.yearId || "").includes(q)
      );
    });

    const formatDate = (d) => {
      if (!d) return "N/A";
      return new Date(d).toLocaleDateString("vi-VN");
    };

    const deleteTest = async (id) => {
      if (!confirm("Xoá bài thi?")) return;
      try {
        await adminService.deleteTest(id);
        tests.value = tests.value.filter((t) => t.id !== id);
        alert("Xoá bài thi thành công!");
      } catch (e) {
        alert("Xoá thất bại!");
      }
    };

    const openEditModal = (test) => {
      editTest.value = { ...test };
    };

    const submitNewTest = async () => {
      try {
        await adminService.createTest(newTest.value);
        alert("Thêm bài thi thành công!");
        location.reload();
      } catch (e) {
        alert("Thêm bài thi thất bại!");
        console.error(e);
      }
    };

    const submitEditTest = async () => {
      try {
        await adminService.updateTest(editTest.value.id, editTest.value);
        alert("Cập nhật bài thi thành công!");
        location.reload();
      } catch (e) {
        alert("Cập nhật thất bại!");
        console.error(e);
      }
    };

    return {
      searchQuery,
      filteredTests,
      formatDate,
      newTest,
      editTest,
      openEditModal,
      deleteTest,
      submitNewTest,
      submitEditTest,
    };
  },
};
</script>

<style scoped>
.test-bank {
  background-color: #f8f9fa;
}

.table-responsive {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.table th,
.table td {
  vertical-align: middle;
  text-align: center;
}

.table th {
  font-weight: 600;
}

.text-success {
  color: #ffffff !important;
  font-weight: 500;
  background-color: #2e7d32;
  padding: 4px 10px;
  border-radius: 30px;
  display: inline-block;
}

.text-danger {
  color: #ffffff !important;
  font-weight: 500;
  background-color: #1c1e21a3;
  padding: 4px 10px;
  border-radius: 30px;
  display: inline-block;
}

.test-success {
  color: #ffffff !important;
  font-weight: 500;
  background-color: #3a64cf;
  padding: 4px 10px;
  border-radius: 30px;
  display: inline-block;
}

.test-danger {
  color: #ffffff !important;
  font-weight: 500;
  background-color: #1c1e21a3;
  padding: 4px 10px;
  border-radius: 30px;
  display: inline-block;
}

.btn-sm {
  padding: 0.25rem 0.5rem;
  font-size: 0.875rem;
}

.modal-dialog {
  max-width: 500px;
}

.modal-content {
  border-radius: 8px;
}

.modal-header {
  background-color: #f8f9fa;
  border-bottom: 1px solid #dee2e6;
}

.modal-title {
  font-size: 1.25rem;
  font-weight: 600;
}

.form-check label {
  text-align: left;
  padding-left: 12px;
  display: block;
}

.form-check-input {
  border: 1px solid #d87330;
}

.form-check-input:checked {
  background-color: #1877f2;
  border-color: #1877f2;
}

.form-label {
  font-size: 1rem;
  font-weight: 600;
  text-align: left;
  display: block;
}
</style>