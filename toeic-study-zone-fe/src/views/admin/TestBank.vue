<template>
  <div class="test-bank container-fluid py-4">
    <h4 class="mb-4 fw-bold">Ngân hàng bài thi</h4>

    <!-- Thanh tìm kiếm và nút thêm bài thi -->
    <div class="row mb-4">
      <div class="col-md-8">
        <div class="input-group">
          <input
            type="text"
            class="form-control"
            placeholder="Tìm kiếm bài thi theo tiêu đề hoặc năm..."
            v-model="searchQuery"
            @input="filterTests"
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

    <!-- Bảng danh sách bài thi -->
    <div class="table-responsive">
      <table class="table table-bordered table-hover">
        <thead class="table-light">
          <tr>
            <th>ID</th>
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
          <tr v-for="test in filteredTests" :key="test.id">
            <td>{{ test.id }}</td>
            <td>{{ test.title }}</td>
            <td>{{ test.year }}</td>
            <td>{{ test.total_questions }}</td>
            <td>{{ test.time_limit ? test.time_limit : "Không giới hạn" }}</td>
            <td>
              <span :class="test.is_free ? 'text-success' : 'text-danger'">
                {{ test.is_free ? "Có" : "Không" }}
              </span>
            </td>
            <td>
              <span :class="test.is_published ? 'text-success' : 'text-danger'">
                {{ test.is_published ? "Có" : "Không" }}
              </span>
            </td>
            <td>
              <span
                :class="test.is_placement_test ? 'test-success' : 'test-danger'"
              >
                {{ test.is_placement_test ? "Có" : "Không" }}
              </span>
            </td>
            <td>{{ formatDate(test.created_at) }}</td>
            <td>
              <button
                class="btn btn-sm btn-warning me-2"
                data-bs-toggle="modal"
                data-bs-target="#editTestModal"
                @click="openEditModal(test)"
              >
                <i class="fas fa-edit"></i> Sửa
              </button>
              <button
                @click="deleteTest(test.id)"
                class="btn btn-sm btn-danger"
              >
                <i class="fas fa-trash"></i> Xóa
              </button>
            </td>
          </tr>
          <tr v-if="filteredTests.length === 0">
            <td colspan="10" class="text-center">
              Không tìm thấy bài thi nào.
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Modal thêm bài thi mới -->
    <div
      class="modal fade"
      id="addTestModal"
      tabindex="-1"
      aria-labelledby="addTestModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="addTestModalLabel">Thêm bài thi mới</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="submitNewTest">
              <div class="mb-3">
                <label for="test-title" class="form-label">Tiêu đề:</label>
                <input
                  type="text"
                  class="form-control"
                  id="test-title"
                  v-model="newTest.title"
                  required
                />
              </div>
              <div class="mb-3">
                <label for="test-description" class="form-label">Mô tả:</label>
                <textarea
                  class="form-control"
                  id="test-description"
                  v-model="newTest.description"
                  rows="3"
                  placeholder="Nhập mô tả bài thi (không bắt buộc)..."
                ></textarea>
              </div>
              <div class="mb-3">
                <label for="test-year" class="form-label">Năm:</label>
                <input
                  type="number"
                  class="form-control"
                  id="test-year"
                  v-model="newTest.year_id"
                  min="2010"
                  required
                />
              </div>
              <div class="mb-3">
                <label for="test-total-questions" class="form-label"
                  >Số câu hỏi:</label
                >
                <input
                  type="number"
                  class="form-control"
                  id="test-total-questions"
                  v-model="newTest.total_questions"
                  min="1"
                  required
                />
              </div>
              <div class="mb-3">
                <label for="test-time-limit" class="form-label"
                  >Thời gian giới hạn (phút):</label
                >
                <input
                  type="number"
                  class="form-control"
                  id="test-time-limit"
                  v-model="newTest.time_limit"
                  min="1"
                />
              </div>
              <div class="mb-3 form-check">
                <input
                  type="checkbox"
                  class="form-check-input"
                  id="test-is-free"
                  v-model="newTest.is_free"
                />
                <label class="form-check-label" for="test-is-free"
                  >Miễn phí (Có thể không chọn)</label
                >
              </div>
              <div class="mb-3 form-check">
                <input
                  type="checkbox"
                  class="form-check-input"
                  id="test-is-published"
                  v-model="newTest.is_published"
                />
                <label class="form-check-label" for="test-is-published"
                  >Công khai (Có thể không chọn)</label
                >
              </div>
              <div class="mb-3 form-check">
                <input
                  type="checkbox"
                  class="form-check-input"
                  id="test-is-placement"
                  v-model="newTest.is_placement_test"
                />
                <label class="form-check-label" for="test-is-placement"
                  >Đánh giá đầu vào (Chọn khi đó là mục thi đầu vào)</label
                >
              </div>
              <button type="submit" class="btn btn-primary w-100">
                Thêm bài thi
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal chỉnh sửa bài thi -->
    <div
      class="modal fade"
      id="editTestModal"
      tabindex="-1"
      aria-labelledby="editTestModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="editTestModalLabel">
              Chỉnh sửa bài thi
            </h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="submitEditTest">
              <div class="mb-3">
                <label for="edit-test-title" class="form-label">Tiêu đề:</label>
                <input
                  type="text"
                  class="form-control"
                  id="edit-test-title"
                  v-model="editTest.title"
                  required
                />
              </div>
              <div class="mb-3">
                <label for="edit-test-description" class="form-label"
                  >Mô tả:</label
                >
                <textarea
                  class="form-control"
                  id="edit-test-description"
                  v-model="editTest.description"
                  rows="3"
                  placeholder="Nhập mô tả bài thi (không bắt buộc)..."
                ></textarea>
              </div>
              <div class="mb-3">
                <label for="edit-test-year" class="form-label">Năm:</label>
                <input
                  type="number"
                  class="form-control"
                  id="edit-test-year"
                  v-model="editTest.year_id"
                  min="2010"
                  required
                />
              </div>
              <div class="mb-3">
                <label for="edit-test-total-questions" class="form-label"
                  >Số câu hỏi:</label
                >
                <input
                  type="number"
                  class="form-control"
                  id="edit-test-total-questions"
                  v-model="editTest.total_questions"
                  min="1"
                  required
                />
              </div>
              <div class="mb-3">
                <label for="edit-test-time-limit" class="form-label"
                  >Thời gian giới hạn (phút):</label
                >
                <input
                  type="number"
                  class="form-control"
                  id="edit-test-time-limit"
                  v-model="editTest.time_limit"
                  min="1"
                />
              </div>
              <div class="mb-3 form-check">
                <input
                  type="checkbox"
                  class="form-check-input"
                  id="edit-test-is-free"
                  v-model="editTest.is_free"
                />
                <label class="form-check-label" for="edit-test-is-free"
                  >Miễn phí (Có thể không chọn)</label
                >
              </div>
              <div class="mb-3 form-check">
                <input
                  type="checkbox"
                  class="form-check-input"
                  id="edit-test-is-published"
                  v-model="editTest.is_published"
                />
                <label class="form-check-label" for="edit-test-is-published"
                  >Công khai (Có thể không chọn)</label
                >
              </div>
              <div class="mb-3 form-check">
                <input
                  type="checkbox"
                  class="form-check-input"
                  id="edit-test-is-placement"
                  v-model="editTest.is_placement_test"
                />
                <label class="form-check-label" for="edit-test-is-placement"
                  >Đánh giá đầu vào (Chọn khi đó là mục thi đầu vào)</label
                >
              </div>
              <button type="submit" class="btn btn-primary w-100">
                Lưu thay đổi
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
// import adminService from "@/services/adminService";

export default {
  name: "TestBank",
  setup() {
    const router = useRouter();
    const tests = ref([]);
    const searchQuery = ref("");
    const newTest = ref({
      title: "",
      description: "",
      year_id: null,
      total_questions: null,
      time_limit: null,
      is_free: false,
      is_published: false,
      is_placement_test: false,
    });
    const editTest = ref({
      id: null,
      title: "",
      description: "",
      year_id: null,
      total_questions: null,
      time_limit: null,
      is_free: false,
      is_published: false,
      is_placement_test: false,
    });

    // Dữ liệu giả lập (mock data) để thay thế cho API
    const mockTests = [
      {
        id: 1,
        title: "Practice Set 2023 TOEIC Test 10",
        description: "A sample TOEIC test for 2023.",
        year_id: 2023,
        year: 2023,
        total_questions: 200,
        time_limit: 120,
        is_free: true,
        is_published: true,
        is_placement_test: false,
        created_at: "2023-01-01T10:00:00Z",
      },
      {
        id: 2,
        title: "Practice Set 2024 TOEIC Test 11",
        description: "A challenging TOEIC test for 2024.",
        year_id: 2024,
        year: 2024,
        total_questions: 200,
        time_limit: 120,
        is_free: true,
        is_published: false,
        is_placement_test: true,
        created_at: "2024-01-01T10:00:00Z",
      },
    ];

    // Khởi tạo danh sách bài thi từ mock data
    onMounted(() => {
      tests.value = mockTests;

      // Code gọi API (được comment lại, sử dụng khi có backend)
      /*
          try {
            const response = await adminService.getTests();
            tests.value = response.data.map((test) => ({
              ...test,
              year: test.year_id,
            }));
          } catch (error) {
            console.error("Lỗi khi lấy danh sách bài thi:", error);
          }
          */
    });

    // Lọc bài thi dựa trên tìm kiếm
    const filteredTests = computed(() => {
      if (!searchQuery.value) return tests.value;
      const query = searchQuery.value.toLowerCase();
      return tests.value.filter(
        (test) =>
          test.title.toLowerCase().includes(query) ||
          String(test.year).includes(query)
      );
    });

    // Hàm định dạng ngày
    const formatDate = (date) => {
      return new Date(date).toLocaleDateString("vi-VN", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
      });
    };

    // Hàm xóa bài thi
    const deleteTest = async (testId) => {
      if (confirm("Bạn có chắc chắn muốn xóa bài thi này?")) {
        tests.value = tests.value.filter((test) => test.id !== testId);
        alert("Xóa bài thi thành công!");

        // Code gọi API (được comment lại, sử dụng khi có backend)
        /*
            try {
              await adminService.deleteTest(testId);
              tests.value = tests.value.filter((test) => test.id !== testId);
              alert("Xóa bài thi thành công!");
            } catch (error) {
              console.error("Lỗi khi xóa bài thi:", error);
              alert("Có lỗi xảy ra khi xóa bài thi.");
            }
            */
      }
    };

    // Hàm mở modal chỉnh sửa và điền dữ liệu
    const openEditModal = (test) => {
      editTest.value = {
        id: test.id,
        title: test.title,
        description: test.description,
        year_id: test.year_id,
        total_questions: test.total_questions,
        time_limit: test.time_limit,
        is_free: test.is_free,
        is_published: test.is_published,
        is_placement_test: test.is_placement_test,
      };
    };

    // Hàm lọc bài thi (gọi khi searchQuery thay đổi)
    const filterTests = () => {
      // Logic đã được xử lý trong computed property filteredTests
    };

    // Hàm gửi form thêm bài thi mới
    const submitNewTest = async () => {
      if (newTest.value.year_id < 2010) {
        alert("Năm phải lớn hơn hoặc bằng 2010!");
        return;
      }

      // Kiểm tra tiêu đề trùng lặp
      const existingTest = tests.value.find(
        (test) => test.title.toLowerCase() === newTest.value.title.toLowerCase()
      );
      if (existingTest) {
        alert("Tiêu đề bài thi đã tồn tại!");
        return;
      }

      // Giả lập thêm bài thi
      const newTestData = {
        id: tests.value.length + 1, // Tạo ID giả lập
        ...newTest.value,
        year: newTest.value.year_id, // Map year_id thành year để hiển thị
        created_at: new Date().toISOString(), // Giả lập ngày tạo
      };
      tests.value.push(newTestData);
      alert("Thêm bài thi thành công!");
      const modal = document.getElementById("addTestModal");
      const bootstrapModal = bootstrap.Modal.getInstance(modal);
      bootstrapModal.hide();
      newTest.value = {
        title: "",
        description: "",
        year_id: null,
        total_questions: null,
        time_limit: null,
        is_free: false,
        is_published: false,
        is_placement_test: false,
      };

      // Code gọi API (được comment lại, sử dụng khi có backend)
      /*
          try {
            const response = await adminService.createTest(newTest.value);
            tests.value.push({
              ...response.data,
              year: newTest.value.year_id,
            });
            alert("Thêm bài thi thành công!");
            const modal = document.getElementById("addTestModal");
            const bootstrapModal = bootstrap.Modal.getInstance(modal);
            bootstrapModal.hide();
            newTest.value = {
              title: "",
              description: "",
              year_id: null,
              total_questions: null,
              time_limit: null,
              is_free: false,
              is_published: false,
              is_placement_test: false,
            };
          } catch (error) {
            console.error("Lỗi khi thêm bài thi:", error);
            alert("Có lỗi xảy ra: " + (error.response?.data?.message || error.message));
          }
          */
    };

    // Hàm gửi form chỉnh sửa bài thi
    const submitEditTest = async () => {
      if (editTest.value.year_id < 2010) {
        alert("Năm phải lớn hơn hoặc bằng 2010!");
        return;
      }

      // Kiểm tra tiêu đề trùng lặp (trừ bài thi đang chỉnh sửa)
      const existingTest = tests.value.find(
        (test) =>
          test.title.toLowerCase() === editTest.value.title.toLowerCase() &&
          test.id !== editTest.value.id
      );
      if (existingTest) {
        alert("Tiêu đề bài thi đã tồn tại!");
        return;
      }

      // Giả lập cập nhật bài thi
      const updatedTest = {
        ...editTest.value,
        year: editTest.value.year_id, // Map year_id thành year để hiển thị
        created_at: tests.value.find((test) => test.id === editTest.value.id)
          .created_at, // Giữ nguyên ngày tạo
      };
      tests.value = tests.value.map((test) =>
        test.id === editTest.value.id ? updatedTest : test
      );
      alert("Cập nhật bài thi thành công!");
      const modal = document.getElementById("editTestModal");
      const bootstrapModal = bootstrap.Modal.getInstance(modal);
      bootstrapModal.hide();

      // Code gọi API (được comment lại, sử dụng khi có backend)
      /*
          try {
            const response = await adminService.updateTest(editTest.value.id, editTest.value);
            tests.value = tests.value.map((test) =>
              test.id === editTest.value.id
                ? { ...response.data, year: editTest.value.year_id }
                : test
            );
            alert("Cập nhật bài thi thành công!");
            const modal = document.getElementById("editTestModal");
            const bootstrapModal = bootstrap.Modal.getInstance(modal);
            bootstrapModal.hide();
          } catch (error) {
            console.error("Lỗi khi cập nhật bài thi:", error);
            alert("Có lỗi xảy ra: " + (error.response?.data?.message || error.message));
          }
          */
    };

    return {
      searchQuery,
      filteredTests,
      formatDate,
      deleteTest,
      openEditModal,
      editTest,
      submitEditTest,
      newTest,
      submitNewTest,
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
