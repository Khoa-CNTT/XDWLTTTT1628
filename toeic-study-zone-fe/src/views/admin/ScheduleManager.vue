<template>
  <div class="p-4">
    <!-- Tabs Gợi ý / Lịch học -->
    <ul class="nav nav-pills mb-4">
      <li class="nav-item">
        <button
          class="nav-link"
          :class="{ active: activeTab === 'suggest' }"
          @click="activeTab = 'suggest'"
        >
          Gợi ý
        </button>
      </li>
      <li class="nav-item">
        <button
          class="nav-link"
          :class="{ active: activeTab === 'list' }"
          @click="activeTab = 'list'"
        >
          Lịch học
        </button>
      </li>
    </ul>

    <!-- Tab Gợi ý -->
    <div v-if="activeTab === 'suggest'">
      <div class="mb-4 border rounded shadow-sm p-4">
        <!-- Nút tạo lịch học nằm ở đầu -->
        <div class="text-end mb-3">
          <button class="btn btn-primary" @click="showScheduleModal = true">
            + Tạo lịch học
          </button>
        </div>

        <!-- Danh sách lịch học đã tạo -->
        <div class="row">
          <div
            class="col-md-4 mb-3"
            v-for="(todo, index) in createdTodos"
            :key="index"
          >
            <div class="border rounded p-3 shadow-sm">
              <div
                class="d-flex justify-content-between align-items-center mb-2"
              >
                <div>
                  <h5 class="fw-bold">
                    {{ todo.title }}
                    <i
                      class="bi bi-pencil-square cursor-pointer"
                      @click="editToDo(index)"
                    ></i>
                  </h5>
                  <p class="text-muted">{{ todo.description }}</p>
                </div>
                <span class="badge bg-success" v-if="todo.available"
                  >Active</span
                >
              </div>
              <ul class="nav nav-tabs mt-2">
                <li class="nav-item">
                  <a class="nav-link active">Chỉnh sửa/Thêm mới</a>
                </li>
              </ul>
              <div class="text-danger small mt-2">
                Click vào mỗi task để chỉnh sửa hoặc vào nút
                <strong>X</strong> màu đỏ để xoá.
              </div>
              <div class="mt-2">
                <div
                  v-for="(list, i) in todo.todoLists"
                  :key="i"
                  class="border rounded p-3 mb-3"
                >
                  <div
                    class="d-flex justify-content-between align-items-center mb-2"
                  >
                    <span class="badge bg-info text-dark"
                      >{{ list.title }}: {{ list.type }}</span
                    >
                    <div>
                      <i
                        class="bi bi-pencil-square cursor-pointer me-2"
                        @click="editTodoList(index, i)"
                      ></i>
                      <i
                        class="bi bi-trash text-danger cursor-pointer"
                        @click="removeTodoList(index, i)"
                      ></i>
                    </div>
                  </div>
                  <div
                    class="bg-light text-primary text-center p-2 rounded mb-2 cursor-pointer"
                    @click="addTask(index, i)"
                  >
                    + Thêm task
                  </div>
                  <ul class="ps-3">
                    <li v-for="(task, j) in list.tasks" :key="j">
                      <span
                        class="cursor-pointer me-2"
                        @click="editTask(index, i, j)"
                        >{{ task }}</span
                      >
                      <button
                        class="btn btn-sm btn-danger"
                        @click="removeTask(index, i, j)"
                      >
                        X
                      </button>
                    </li>
                  </ul>
                </div>
                <button
                  class="btn btn-outline-primary btn-sm"
                  @click="addTodoList(index)"
                >
                  + Thêm To-do list
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Tab Lịch học -->
    <div v-if="activeTab === 'list'">
      <div class="card shadow-sm p-4">
        <h5 class="fw-bold mb-3">Quản lý lịch học</h5>
        <table class="table table-bordered align-middle">
          <thead class="table-light">
            <tr>
              <th>#</th>
              <th>Người dùng</th>
              <th>Tiêu đề</th>
              <th>Ngày bắt đầu</th>
              <th>Ngày kết thúc</th>
              <th>Trạng thái</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(schedule, i) in schedules" :key="i">
              <td>{{ i + 1 }}</td>
              <td>{{ schedule.user }}</td>
              <td>{{ schedule.title }}</td>
              <td>{{ schedule.start }}</td>
              <td>{{ schedule.end }}</td>
              <td>
                <span
                  class="badge"
                  :class="schedule.isCompleted ? 'bg-success' : 'bg-secondary'"
                >
                  {{ schedule.isCompleted ? "Hoàn thành" : "Chưa hoàn thành" }}
                </span>
              </td>
              <td>
                <button class="btn btn-danger btn-sm ms-2">Xoá</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Modal tạo lịch học -->
    <div
      v-if="showScheduleModal"
      class="modal-backdrop d-flex align-items-center justify-content-center"
    >
      <div
        class="modal-content p-4 rounded shadow bg-white"
        style="max-width: 500px; width: 100%"
      >
        <div class="d-flex justify-content-between align-items-center mb-3">
          <h4 class="fw-bold">Tạo lịch học</h4>
          <button class="btn-close" @click="showScheduleModal = false"></button>
        </div>
        <div class="mb-3">
          <label>Tên / tiêu đề</label>
          <input class="form-control" v-model="newTodo.title" />
        </div>
        <div class="mb-3">
          <label>Mô tả</label>
          <textarea
            class="form-control"
            rows="3"
            v-model="newTodo.description"
          ></textarea>
        </div>
        <div class="form-check mb-3">
          <input
            class="form-check-input"
            type="checkbox"
            v-model="newTodo.available"
          />
          <label class="form-check-label">Lịch học khả dụng</label>
        </div>
        <button class="btn btn-primary" @click="addNewSchedule">Lưu</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from "vue";

const activeTab = ref("suggest");
const showScheduleModal = ref(false);

const newTodo = reactive({
  title: "",
  description: "",
  available: true,
  todoLists: [],
});

const createdTodos = ref([]);

function addNewSchedule() {
  createdTodos.value.push({
    title: newTodo.title,
    description: newTodo.description,
    available: newTodo.available,
    todoLists: [],
  });
  showScheduleModal.value = false;
  newTodo.title = "";
  newTodo.description = "";
  newTodo.available = true;
}

function addTodoList(scheduleIndex) {
  const title = prompt("Nhập tiêu đề To-do list:");
  if (title) {
    createdTodos.value[scheduleIndex].todoLists.push({
      title,
      type: "Hằng ngày",
      tasks: [],
    });
  }
}

function editTodoList(scheduleIndex, listIndex) {
  const updated = prompt(
    "Chỉnh sửa tiêu đề:",
    createdTodos.value[scheduleIndex].todoLists[listIndex].title
  );
  if (updated !== null) {
    createdTodos.value[scheduleIndex].todoLists[listIndex].title = updated;
  }
}

function removeTodoList(scheduleIndex, listIndex) {
  createdTodos.value[scheduleIndex].todoLists.splice(listIndex, 1);
}

function addTask(scheduleIndex, listIndex) {
  const task = prompt("Nhập nội dung task:");
  if (task) {
    createdTodos.value[scheduleIndex].todoLists[listIndex].tasks.push(task);
  }
}

function removeTask(scheduleIndex, listIndex, taskIndex) {
  createdTodos.value[scheduleIndex].todoLists[listIndex].tasks.splice(
    taskIndex,
    1
  );
}

function editTask(scheduleIndex, listIndex, taskIndex) {
  const updated = prompt(
    "Chỉnh sửa task:",
    createdTodos.value[scheduleIndex].todoLists[listIndex].tasks[taskIndex]
  );
  if (updated !== null) {
    createdTodos.value[scheduleIndex].todoLists[listIndex].tasks[taskIndex] =
      updated;
  }
}

function editToDo(scheduleIndex) {
  const updated = prompt(
    "Chỉnh sửa tiêu đề:",
    createdTodos.value[scheduleIndex].title
  );
  if (updated !== null) {
    createdTodos.value[scheduleIndex].title = updated;
  }
}

const schedules = ref([
  {
    user: "Lê Thị Ánh Ngọc",
    title: "Ôn Part 5 - Reading TOEIC",
    start: "09:00:00 20/4/2025",
    end: "10:00:00 20/4/2025",
    isCompleted: false,
  },
]);
</script>

<style scoped>
.nav-pills .nav-link.active {
  font-weight: bold;
}
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1050;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.4);
}
.modal-content {
  background: #fff;
}
.cursor-pointer {
  cursor: pointer;
}
</style>
