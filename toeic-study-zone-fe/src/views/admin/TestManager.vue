<template>
  <div class="test-manager container-fluid py-4">
    <!-- Dropdown chọn bài thi -->
    <div class="row mb-4">
      <div class="col-md-12">
        <label for="test-select" class="form-label">Chọn bài thi:</label>
        <select
          id="test-select"
          class="form-select"
          v-model="selectedTestId"
          @change="fetchSections"
        >
          <option value="" disabled>Chọn bài thi...</option>
          <option v-for="test in tests" :key="test.id" :value="test.id">
            {{ test.title }} ({{ test.year }})
          </option>
        </select>
      </div>
    </div>

    <!-- Tab Reading và Listening -->
    <div v-if="selectedTestId" class="card">
      <ul class="nav nav-tabs">
        <li class="nav-item">
          <a
            class="nav-link"
            :class="{ active: activeTab === 'listening' }"
            @click="setActiveTab('listening')"
            href="#"
          >
            Listening
          </a>
        </li>
        <li class="nav-item">
          <a
            class="nav-link"
            :class="{ active: activeTab === 'reading' }"
            @click="setActiveTab('reading')"
            href="#"
          >
            Reading
          </a>
        </li>
      </ul>

      <!-- Nội dung tab -->
      <div class="card-body">
        <!-- Tab Listening -->
        <div v-if="activeTab === 'listening'">
          <h5 class="card-title">Listening Sections</h5>
          <!-- Dropdown chọn Part -->
          <div class="row mb-4">
            <div class="col-md-6">
              <label for="listening-part-select" class="form-label">Chọn Part:</label>
              <select
                id="listening-part-select"
                class="form-select"
                v-model="selectedListeningPart"
              >
                <option value="" disabled>Chọn Part...</option>
                <option v-for="part in [1, 2, 3, 4]" :key="part" :value="part">
                  Part {{ part }}
                </option>
              </select>
            </div>
          </div>

          <!-- Hiển thị bảng câu hỏi Listening nếu đã chọn Part -->
          <div v-if="selectedListeningPart">
            <div class="d-flex justify-content-between align-items-center mb-2">
              <h6 class="mb-0">
                Part {{ selectedListeningPart }}: {{ currentListeningSection?.title || "Không có tiêu đề" }}
              </h6>
              <button
                class="btn btn-sm btn-success"
                @click="openAddQuestionModal"
                :disabled="isPartFull"
              >
                <i class="fas fa-plus me-1"></i>Thêm câu hỏi
              </button>
            </div>
            <div class="table-responsive">
              <table class="table table-bordered table-hover">
                <thead class="table-dark">
                  <tr>
                    <th v-if="['3', '4'].includes(selectedListeningPart)">Nhóm</th>
                    <th>Số thứ tự</th>
                    <th>Câu hỏi</th>
                    <th>Đáp án A</th>
                    <th>Đáp án B</th>
                    <th>Đáp án C</th>
                    <th>Đáp án D</th>
                    <th>Đáp án đúng</th>
                    <th>Transcript</th>
                    <th>File nghe</th>
                    <th>Hình ảnh</th>
                    <th>Hành động</th>
                  </tr>
                </thead>
                <tbody>
                  <template v-if="['3', '4'].includes(selectedListeningPart)">
                    <tr
                      v-for="(group, groupIndex) in questionGroups"
                      :key="'group-' + groupIndex"
                    >
                      <td :rowspan="group.questions.length">
                        Nhóm {{ groupIndex + 1 }}
                        <br />
                        File nghe: {{ group.audio_url ? getFileName(group.audio_url) : "Không có" }}
                      </td>
                      <td v-for="(question, qIndex) in group.questions" :key="question.id">
                        <tr v-if="qIndex === 0" />
                        <td>{{ question.question_number }}</td>
                        <td>
                          <span v-if="question.question_text">{{
                            truncateText(question.question_text, 30)
                          }}</span>
                          <span v-else class="text-muted">Không có</span>
                          <button
                            v-if="question.question_text && question.question_text.length > 30"
                            class="btn btn-link btn-sm p-0 ms-2"
                            @click="showFullQuestion(question.question_text)"
                          >
                            Xem thêm
                          </button>
                        </td>
                        <td>
                          <span v-if="getOption(question, 'A')">{{
                            truncateText(getOption(question, 'A'), 20)
                          }}</span>
                          <span v-else class="text-muted">Không có</span>
                        </td>
                        <td>
                          <span v-if="getOption(question, 'B')">{{
                            truncateText(getOption(question, 'B'), 20)
                          }}</span>
                          <span v-else class="text-muted">Không có</span>
                        </td>
                        <td>
                          <span v-if="getOption(question, 'C')">{{
                            truncateText(getOption(question, 'C'), 20)
                          }}</span>
                          <span v-else class="text-muted">Không có</span>
                        </td>
                        <td>
                          <span v-if="getOption(question, 'D')">{{
                            truncateText(getOption(question, 'D'), 20)
                          }}</span>
                          <span v-else class="text-muted">Không có</span>
                        </td>
                        <td>{{ getCorrectAnswer(question) }}</td>
                        <td>
                          <span v-if="question.transcript">{{
                            truncateText(question.transcript, 30)
                          }}</span>
                          <span v-else class="text-muted">Không có</span>
                          <button
                            v-if="question.transcript && question.transcript.length > 30"
                            class="btn btn-link btn-sm p-0 ms-2"
                            @click="showTranscript(question.transcript)"
                          >
                            Xem thêm
                          </button>
                        </td>
                        <td>{{ group.audio_url ? getFileName(group.audio_url) : "Không có" }}</td>
                        <td>
                          {{ question.image_url ? getFileName(question.image_url) : "Không có" }}
                        </td>
                        <td>
                          <div class="d-flex flex-column gap-2">
                            <button
                              class="btn btn-sm btn-warning"
                              @click="openEditQuestionModal(question)"
                            >
                              <i class="fas fa-edit"></i> Sửa
                            </button>
                            <button
                              class="btn btn-sm btn-danger"
                              @click="deleteQuestion(currentListeningSection?.id, question.id)"
                            >
                              <i class="fas fa-trash"></i> Xóa
                            </button>
                          </div>
                        </td>
                      </td>
                    </tr>
                  </template>
                  <template v-else>
                    <tr
                      v-for="question in currentListeningSection?.questions || []"
                      :key="question.id"
                    >
                      <td>{{ question.question_number }}</td>
                      <td>
                        <span v-if="question.question_text">{{
                          truncateText(question.question_text, 30)
                        }}</span>
                        <span v-else class="text-muted">Không có</span>
                        <button
                          v-if="question.question_text && question.question_text.length > 30"
                          class="btn btn-link btn-sm p-0 ms-2"
                          @click="showFullQuestion(question.question_text)"
                        >
                          Xem thêm
                        </button>
                      </td>
                      <td>
                        <span v-if="getOption(question, 'A')">{{
                          truncateText(getOption(question, 'A'), 20)
                        }}</span>
                        <span v-else class="text-muted">Không có</span>
                      </td>
                      <td>
                        <span v-if="getOption(question, 'B')">{{
                          truncateText(getOption(question, 'B'), 20)
                        }}</span>
                        <span v-else class="text-muted">Không có</span>
                      </td>
                      <td>
                        <span v-if="getOption(question, 'C')">{{
                          truncateText(getOption(question, 'C'), 20)
                        }}</span>
                        <span v-else class="text-muted">Không có</span>
                      </td>
                      <td>
                        <span v-if="getOption(question, 'D')">{{
                          truncateText(getOption(question, 'D'), 20)
                        }}</span>
                        <span v-else class="text-muted">Không có</span>
                      </td>
                      <td>{{ getCorrectAnswer(question) }}</td>
                      <td>
                        <span v-if="question.transcript">{{
                          truncateText(question.transcript, 30)
                        }}</span>
                        <span v-else class="text-muted">Không có</span>
                        <button
                          v-if="question.transcript && question.transcript.length > 30"
                          class="btn btn-link btn-sm p-0 ms-2"
                          @click="showTranscript(question.transcript)"
                        >
                          Xem thêm
                        </button>
                      </td>
                      <td>
                        {{ question.audio_url ? getFileName(question.audio_url) : "Không có" }}
                      </td>
                      <td>
                        {{ question.image_url ? getFileName(question.image_url) : "Không có" }}
                      </td>
                      <td>
                        <div class="d-flex flex-column gap-2">
                          <button
                            class="btn btn-sm btn-warning"
                            @click="openEditQuestionModal(question)"
                          >
                            <i class="fas fa-edit"></i> Sửa
                          </button>
                          <button
                            class="btn btn-sm btn-danger"
                            @click="deleteQuestion(currentListeningSection?.id, question.id)"
                          >
                            <i class="fas fa-trash"></i> Xóa
                          </button>
                        </div>
                      </td>
                    </tr>
                  </template>
                  <tr
                    v-if="
                      (!currentListeningSection?.questions ||
                        currentListeningSection?.questions.length === 0) &&
                      !['3', '4'].includes(selectedListeningPart)
                    "
                  >
                    <td :colspan="11" class="text-center">
                      Không có câu hỏi nào.
                    </td>
                  </tr>
                  <tr
                    v-if="['3', '4'].includes(selectedListeningPart) && questionGroups.length === 0"
                  >
                    <td colspan="12" class="text-center">
                      Không có nhóm câu hỏi nào.
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div v-else class="text-center text-muted">
            Vui lòng chọn một Part để xem câu hỏi.
          </div>
        </div>

        <!-- Tab Reading -->
        <div v-if="activeTab === 'reading'">
          <h5 class="card-title">Reading Sections</h5>
          <!-- Dropdown chọn Part -->
          <div class="row mb-4">
            <div class="col-md-6">
              <label for="reading-part-select" class="form-label">Chọn Part:</label>
              <select
                id="reading-part-select"
                class="form-select"
                v-model="selectedReadingPart"
              >
                <option value="" disabled>Chọn Part...</option>
                <option v-for="part in [5, 6, 7]" :key="part" :value="part">
                  Part {{ part }}
                </option>
              </select>
            </div>
          </div>

          <!-- Hiển thị bảng câu hỏi Reading nếu đã chọn Part -->
          <div v-if="selectedReadingPart">
            <div class="d-flex justify-content-between align-items-center mb-2">
              <h6 class="mb-0">
                Part {{ selectedReadingPart }}: {{ currentReadingSection?.title || "Không có tiêu đề" }}
              </h6>
              <button
                class="btn btn-sm btn-success"
                @click="openAddQuestionModal"
                :disabled="isPartFull"
              >
                <i class="fas fa-plus me-1"></i>Thêm câu hỏi
              </button>
            </div>
            <div class="table-responsive">
              <table class="table table-bordered table-hover">
                <thead class="table-dark">
                  <tr>
                    <th v-if="['6', '7'].includes(selectedReadingPart)">Hình ảnh đoạn văn</th>
                    <th>Số thứ tự</th>
                    <th>Câu hỏi</th>
                    <th>Đáp án A</th>
                    <th>Đáp án B</th>
                    <th>Đáp án C</th>
                    <th>Đáp án D</th>
                    <th>Đáp án đúng</th>
                    <th>Giải thích</th>
                    <th>Hình ảnh</th>
                    <th>Hành động</th>
                  </tr>
                </thead>
                <tbody>
                  <!-- Hiển thị nhóm câu hỏi nếu có -->
                  <template v-if="['6', '7'].includes(selectedReadingPart) && questionGroups.length > 0">
                    <tr
                      v-for="(group, groupIndex) in questionGroups"
                      :key="'group-' + groupIndex"
                    >
                      <td :rowspan="group.questions.length">
                        <div v-if="group.passage?.images && group.passage.images.length > 0">
                          <div v-for="(image, imgIndex) in group.passage.images" :key="'img-' + imgIndex">
                            <button
                              class="btn btn-link btn-sm p-0"
                              @click="showParagraphImage(image.image_url)"
                            >
                              {{ getFileName(image.image_url) }}
                            </button>
                          </div>
                        </div>
                        <span v-else class="text-muted">Không có hình ảnh</span>
                      </td>
                      <td v-for="(question, qIndex) in group.questions" :key="question.id">
                        <tr v-if="qIndex === 0" />
                        <td>{{ question.question_number }}</td>
                        <td>
                          <span v-if="question.question_text">{{
                            truncateText(question.question_text, 30)
                          }}</span>
                          <span v-else class="text-muted">Không có</span>
                          <button
                            v-if="question.question_text && question.question_text.length > 30"
                            class="btn btn-link btn-sm p-0 ms-2"
                            @click="showFullQuestion(question.question_text)"
                          >
                            Xem thêm
                          </button>
                        </td>
                        <td>
                          <span v-if="getOption(question, 'A')">{{
                            truncateText(getOption(question, 'A'), 20)
                          }}</span>
                          <span v-else class="text-muted">Không có</span>
                        </td>
                        <td>
                          <span v-if="getOption(question, 'B')">{{
                            truncateText(getOption(question, 'B'), 20)
                          }}</span>
                          <span v-else class="text-muted">Không có</span>
                        </td>
                        <td>
                          <span v-if="getOption(question, 'C')">{{
                            truncateText(getOption(question, 'C'), 20)
                          }}</span>
                          <span v-else class="text-muted">Không có</span>
                        </td>
                        <td>
                          <span v-if="getOption(question, 'D')">{{
                            truncateText(getOption(question, 'D'), 20)
                          }}</span>
                          <span v-else class="text-muted">Không có</span>
                        </td>
                        <td>{{ getCorrectAnswer(question) }}</td>
                        <td>
                          <span v-if="getExplanation(question)">{{
                            truncateText(getExplanation(question), 30)
                          }}</span>
                          <span v-else class="text-muted">Không có</span>
                          <button
                            v-if="getExplanation(question) && getExplanation(question).length > 30"
                            class="btn btn-link btn-sm p-0 ms-2"
                            @click="showTranscript(getExplanation(question))"
                          >
                            Xem thêm
                          </button>
                        </td>
                        <td>
                          {{ question.image_url ? getFileName(question.image_url) : "Không có" }}
                        </td>
                        <td>
                          <div class="d-flex flex-column gap-2">
                            <button
                              class="btn btn-sm btn-warning"
                              @click="openEditQuestionModal(question)"
                            >
                              <i class="fas fa-edit"></i> Sửa
                            </button>
                            <button
                              class="btn btn-sm btn-danger"
                              @click="deleteQuestion(currentReadingSection?.id, question.id)"
                            >
                              <i class="fas fa-trash"></i> Xóa
                            </button>
                          </div>
                        </td>
                      </td>
                    </tr>
                  </template>

                  <!-- Hiển thị câu hỏi riêng lẻ -->
                  <template v-else>
                    <tr
                      v-for="question in currentReadingSection?.questions || []"
                      :key="question.id"
                    >
                      <td v-if="['6', '7'].includes(selectedReadingPart)"></td> <!-- Cột Hình ảnh đoạn văn trống nếu không có nhóm -->
                      <td>{{ question.question_number }}</td>
                      <td>
                        <span v-if="question.question_text">{{
                          truncateText(question.question_text, 30)
                        }}</span>
                        <span v-else class="text-muted">Không có</span>
                        <button
                          v-if="question.question_text && question.question_text.length > 30"
                          class="btn btn-link btn-sm p-0 ms-2"
                          @click="showFullQuestion(question.question_text)"
                        >
                          Xem thêm
                        </button>
                      </td>
                      <td>
                        <span v-if="getOption(question, 'A')">{{
                          truncateText(getOption(question, 'A'), 20)
                        }}</span>
                        <span v-else class="text-muted">Không có</span>
                      </td>
                      <td>
                        <span v-if="getOption(question, 'B')">{{
                          truncateText(getOption(question, 'B'), 20)
                        }}</span>
                        <span v-else class="text-muted">Không có</span>
                      </td>
                      <td>
                        <span v-if="getOption(question, 'C')">{{
                          truncateText(getOption(question, 'C'), 20)
                        }}</span>
                        <span v-else class="text-muted">Không có</span>
                      </td>
                      <td>
                        <span v-if="getOption(question, 'D')">{{
                          truncateText(getOption(question, 'D'), 20)
                        }}</span>
                        <span v-else class="text-muted">Không có</span>
                      </td>
                      <td>{{ getCorrectAnswer(question) }}</td>
                      <td>
                        <span v-if="getExplanation(question)">{{
                          truncateText(getExplanation(question), 30)
                        }}</span>
                        <span v-else class="text-muted">Không có</span>
                        <button
                          v-if="getExplanation(question) && getExplanation(question).length > 30"
                          class="btn btn-link btn-sm p-0 ms-2"
                          @click="showTranscript(getExplanation(question))"
                        >
                          Xem thêm
                        </button>
                      </td>
                      <td>
                        {{ question.image_url ? getFileName(question.image_url) : "Không có" }}
                      </td>
                      <td>
                        <div class="d-flex flex-column gap-2">
                          <button
                            class="btn btn-sm btn-warning"
                            @click="openEditQuestionModal(question)"
                          >
                            <i class="fas fa-edit"></i> Sửa
                          </button>
                          <button
                            class="btn btn-sm btn-danger"
                            @click="deleteQuestion(currentReadingSection?.id, question.id)"
                          >
                            <i class="fas fa-trash"></i> Xóa
                          </button>
                        </div>
                      </td>
                    </tr>
                  </template>

                  <tr
                    v-if="
                      !currentReadingSection?.questions ||
                      currentReadingSection?.questions.length === 0
                    "
                  >
                    <td :colspan="['6', '7'].includes(selectedReadingPart) ? 11 : 10" class="text-center">
                      Không có câu hỏi nào.
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div v-else class="text-center text-muted">
            Vui lòng chọn một Part để xem câu hỏi.
          </div>
        </div>
      </div>
    </div>

    <!-- Thông báo khi chưa chọn bài thi -->
    <div v-else class="alert alert-info text-center">
      Vui lòng chọn một bài thi để quản lý các section.
    </div>

    <!-- Modal hiển thị transcript/giải thích -->
    <div
      class="modal fade"
      id="transcriptModal"
      tabindex="-1"
      aria-labelledby="transcriptModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="transcriptModalLabel">Nội dung</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <pre v-if="selectedTranscript" class="transcript-content">{{ selectedTranscript }}</pre>
            <p v-else class="text-muted">Không có nội dung.</p>
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

    <!-- Modal hiển thị câu hỏi đầy đủ -->
    <div
      class="modal fade"
      id="questionModal"
      tabindex="-1"
      aria-labelledby="questionModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="questionModalLabel">Nội dung câu hỏi</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <pre v-if="selectedFullQuestion" class="question-content">{{ selectedFullQuestion }}</pre>
            <p v-else class="text-muted">Không có nội dung câu hỏi.</p>
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

    <!-- Modal hiển thị hình ảnh đoạn văn -->
    <div
      class="modal fade"
      id="paragraphImageModal"
      tabindex="-1"
      aria-labelledby="paragraphImageModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="paragraphImageModalLabel">Hình ảnh đoạn văn</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <img
              v-if="selectedParagraphImage"
              :src="selectedParagraphImage"
              alt="Hình ảnh đoạn văn"
              class="img-fluid"
            />
            <p v-else class="text-muted">Không có hình ảnh.</p>
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

    <!-- Modal thêm/sửa câu hỏi -->
    <div
      class="modal fade"
      id="addEditQuestionModal"
      tabindex="-1"
      aria-labelledby="addEditQuestionModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="addEditQuestionModalLabel">
              {{ questionModalMode === "add" ? "Thêm câu hỏi" : "Sửa câu hỏi" }}
            </h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
              @click="resetQuestionForm"
            ></button>
          </div>
          <div class="modal-body">
            <form>
              <div class="row">
                <div class="col-md-6 mb-3">
                  <label class="form-label">Số thứ tự: </label>
                  <input
                    type="number"
                    class="form-control"
                    v-model="newQuestion.question_number"
                    :min="partRanges[selectedListeningPart || selectedReadingPart]?.min"
                    :max="partRanges[selectedListeningPart || selectedReadingPart]?.max"
                    required
                  />
                </div>
                <div class="col-md-6 mb-3">
                  <label class="form-label">Đáp án đúng: </label>
                  <select
                    class="form-select"
                    v-model="newQuestion.correct_answer"
                    required
                  >
                    <option value="" disabled>Chọn đáp án đúng...</option>
                    <option value="A">A</option>
                    <option value="B">B</option>
                    <option value="C">C</option>
                    <option value="D">D</option>
                  </select>
                </div>
              </div>
              <div v-if="['6', '7'].includes(selectedReadingPart)" class="mb-3">
                <label class="form-label">Nhóm đoạn văn: </label>
                <select
                  class="form-select"
                  v-model="newQuestion.passage_id"
                  required
                >
                  <option value="" disabled>Chọn nhóm...</option>
                  <option v-for="passage in passages" :key="passage.id" :value="passage.id">
                    Nhóm {{ passage.id }} (Câu {{ passage.question_range }})
                  </option>
                </select>
              </div>
              <div class="mb-3">
                <label class="form-label">Câu hỏi: </label>
                <textarea
                  class="form-control"
                  v-model="newQuestion.question_text"
                  rows="3"
                  required
                ></textarea>
              </div>
              <div class="row">
                <div class="col-md-6 mb-3">
                  <label class="form-label">Đáp án A: </label>
                  <input
                    type="text"
                    class="form-control"
                    v-model="newQuestion.options['A'].option_text"
                    required
                  />
                </div>
                <div class="col-md-6 mb-3">
                  <label class="form-label">Đáp án B: </label>
                  <input
                    type="text"
                    class="form-control"
                    v-model="newQuestion.options['B'].option_text"
                    required
                  />
                </div>
              </div>
              <div class="row">
                <div class="col-md-6 mb-3">
                  <label class="form-label">Đáp án C: </label>
                  <input
                    type="text"
                    class="form-control"
                    v-model="newQuestion.options['C'].option_text"
                    required
                  />
                </div>
                <div class="col-md-6 mb-3">
                  <label class="form-label">Đáp án D: </label>
                  <input
                    type="text"
                    class="form-control"
                    v-model="newQuestion.options['D'].option_text"
                    required
                  />
                </div>
              </div>
              <div v-if="activeTab === 'listening'" class="mb-3">
                <label class="form-label">Transcript: </label>
                <textarea
                  class="form-control"
                  v-model="newQuestion.transcript"
                  rows="3"
                ></textarea>
              </div>
              <div v-if="activeTab === 'reading'" class="mb-3">
                <label class="form-label">Giải thích: </label>
                <textarea
                  class="form-control"
                  v-model="newQuestion.explanation"
                  rows="3"
                ></textarea>
              </div>
              <div class="row">
                <div v-if="activeTab === 'listening'" class="col-md-6 mb-3">
                  <label class="form-label">File nghe: </label>
                  <input
                    type="text"
                    class="form-control"
                    v-model="newQuestion.audio_url"
                    placeholder="Đường dẫn file nghe"
                    :required="['1', '2', '3', '4'].includes(selectedListeningPart)"
                  />
                </div>
                <div class="col-md-6 mb-3">
                  <label class="form-label">Hình ảnh: </label>
                  <input
                    type="text"
                    class="form-control"
                    v-model="newQuestion.image_url"
                    placeholder="Đường dẫn hình ảnh"
                    :disabled="!canHaveImage"
                    :required="selectedListeningPart === '1' || selectedReadingPart === '6'"
                  />
                  <small
                    v-if="['3', '4'].includes(selectedListeningPart) && !canHaveImage"
                    class="text-muted"
                  >
                    Hình ảnh chỉ được thêm cho {{ selectedListeningPart === '3' ? '3' : '2' }} nhóm cuối.
                  </small>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
              @click="resetQuestionForm"
            >
              Đóng
            </button>
            <button type="button" class="btn btn-primary" @click="saveQuestion">
              Lưu
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from "vue";
import * as bootstrap from "bootstrap";

export default {
  name: "TestManager",
  setup() {
    const tests = ref([]);
    const selectedTestId = ref("");
    const sections = ref([]);
    const activeTab = ref("listening");
    const selectedListeningPart = ref("");
    const selectedReadingPart = ref("");
    const selectedTranscript = ref(null);
    const selectedFullQuestion = ref(null);
    const selectedParagraphImage = ref(null);
    const questionModalMode = ref("add");
    const newQuestion = ref({
      id: null,
      question_number: null,
      question_text: "",
      options: {
        A: { option_text: "", is_correct: false },
        B: { option_text: "", is_correct: false },
        C: { option_text: "", is_correct: false },
        D: { option_text: "", is_correct: false },
      },
      correct_answer: "",
      transcript: "",
      audio_url: "",
      image_url: "",
      explanation: "",
      passage_id: null,
    });
    const editingQuestionId = ref(null);
    const passages = ref([]);
    let addEditModal = null;

    const partRanges = {
      "1": { min: 1, max: 6, questionCount: 6 },
      "2": { min: 7, max: 31, questionCount: 25 },
      "3": { min: 32, max: 70, questionCount: 39, groupSize: 3 },
      "4": { min: 71, max: 100, questionCount: 30, groupSize: 3 },
      "5": { min: 101, max: 130, questionCount: 30 },
      "6": { min: 131, max: 146, questionCount: 16, groupSize: 4 },
      "7": { min: 147, max: 200, questionCount: 54 },
    };

    const currentListeningSection = computed(() => {
      if (!selectedListeningPart.value) return null;
      return sections.value.find(
        (section) =>
          section.section_type === "LISTENING" &&
          section.section_part === parseInt(selectedListeningPart.value)
      );
    });

    const currentReadingSection = computed(() => {
      if (!selectedReadingPart.value) return null;
      return sections.value.find(
        (section) =>
          section.section_type === "READING" &&
          section.section_part === parseInt(selectedReadingPart.value)
      );
    });

    const currentSection = computed(() =>
      activeTab.value === "listening" ? currentListeningSection.value : currentReadingSection.value
    );

    const questionGroups = computed(() => {
      const part = selectedListeningPart.value || selectedReadingPart.value;
      if (!["3", "4", "6", "7"].includes(part) || !currentSection.value) return [];
      const questions = (currentSection.value.questions || []).sort(
        (a, b) => a.question_number - b.question_number
      );

      const groups = [];
      if (part === "3" || part === "4") {
        const groupSize = partRanges[part].groupSize || 1;
        for (let i = 0; i < questions.length; i += groupSize) {
          const groupQuestions = questions.slice(i, i + groupSize);
          if (groupQuestions.length > 0) {
            groups.push({
              audio_url: groupQuestions[0]?.audio_url,
              questions: groupQuestions,
            });
          }
        }
      } else if (part === "6" || part === "7") {
        const passageGroups = {};
        questions.forEach((question) => {
          const pid = question.passage_id;
          if (!passageGroups[pid]) {
            const passage = passages.value.find((p) => p.id === pid) || { images: [] };
            passageGroups[pid] = {
              passage: passage,
              questions: [],
            };
          }
          passageGroups[pid].questions.push(question);
        });
        return Object.values(passageGroups).sort((a, b) => a.questions[0].question_number - b.questions[0].question_number);
      }
      return groups;
    });

    const isPartFull = computed(() => {
      const part = selectedListeningPart.value || selectedReadingPart.value;
      if (!part || !currentSection.value) return false;
      const maxQuestions = partRanges[part].questionCount;
      return (currentSection.value.questions?.length || 0) >= maxQuestions;
    });

    const canHaveImage = computed(() => {
      const part = selectedListeningPart.value || selectedReadingPart.value;
      if (!part || !currentSection.value) return false;

      if (part === "1" || part === "6") return true;
      if (part === "2" || part === "5" || part === "7") return false;

      if (part === "3") {
        const totalQuestions = currentSection.value.questions?.length || 0;
        const totalGroups = Math.ceil(totalQuestions / 3);
        const currentGroupIndex = Math.floor(totalQuestions / 3);
        return totalGroups >= 10 && currentGroupIndex >= 9;
      }

      if (part === "4") {
        const totalQuestions = currentSection.value.questions?.length || 0;
        const totalGroups = Math.ceil(totalQuestions / 3);
        const currentGroupIndex = Math.floor(totalQuestions / 3);
        return totalGroups >= 9 && currentGroupIndex >= 8;
      }

      return false;
    });

    const getOption = (question, label) => {
      const option = question.options.find((opt) => opt.option_label === label);
      return option ? option.option_text : "";
    };

    const getCorrectAnswer = (question) => {
      const correctOption = question.options.find((opt) => opt.is_correct);
      return correctOption ? correctOption.option_label : "";
    };

    const getExplanation = (question) => {
      const correctOption = question.options.find((opt) => opt.is_correct);
      return correctOption ? correctOption.explanation : "";
    };

    onMounted(() => {
      tests.value = [
        { id: 1, title: "New Economy TOEIC Test 1", year: 2023 },
        { id: 2, title: "New Economy TOEIC Test 2", year: 2024 },
      ];
    });

    const fetchSections = async () => {
      if (!selectedTestId.value) {
        sections.value = [];
        selectedListeningPart.value = "";
        selectedReadingPart.value = "";
        passages.value = [];
        return;
      }
      try {
        // Dữ liệu giả lập cho passages và paragraph_images
        passages.value = [
          { 
            id: 1, 
            section_id: 7, 
            passage_type: "SINGLE", 
            passage_order: 1, 
            question_range: "147-148",
            paragraphs: [
              { 
                id: 1, 
                paragraph_text: "", 
                paragraph_order: 1, 
                images: [
                  { id: 1, image_url: "/images/part7_passage1.jpg", image_order: 1 }
                ]
              }
            ]
          },
          { 
            id: 2, 
            section_id: 7, 
            passage_type: "SINGLE", 
            passage_order: 2, 
            question_range: "155-157",
            paragraphs: [
              { 
                id: 2, 
                paragraph_text: "", 
                paragraph_order: 1, 
                images: [
                  { id: 2, image_url: "/images/part7_passage2.jpg", image_order: 1 }
                ]
              }
            ]
          },
          { 
            id: 3, 
            section_id: 7, 
            passage_type: "SINGLE", 
            passage_order: 3, 
            question_range: "164-167",
            paragraphs: [
              { 
                id: 3, 
                paragraph_text: "", 
                paragraph_order: 1, 
                images: [
                  { id: 3, image_url: "/images/part7_passage3.jpg", image_order: 1 }
                ]
              }
            ]
          },
          { 
            id: 4, 
            section_id: 7, 
            passage_type: "DOUBLE", 
            passage_order: 4, 
            question_range: "176-180",
            paragraphs: [
              { 
                id: 4, 
                paragraph_text: "", 
                paragraph_order: 1, 
                images: [
                  { id: 4, image_url: "/images/part7_passage4_1.jpg", image_order: 1 },
                  { id: 5, image_url: "/images/part7_passage4_2.jpg", image_order: 2 }
                ]
              }
            ]
          },
          { 
            id: 5, 
            section_id: 7, 
            passage_type: "TRIPLE", 
            passage_order: 5, 
            question_range: "186-190",
            paragraphs: [
              { 
                id: 5, 
                paragraph_text: "", 
                paragraph_order: 1, 
                images: [
                  { id: 6, image_url: "/images/part7_passage5_1.jpg", image_order: 1 },
                  { id: 7, image_url: "/images/part7_passage5_2.jpg", image_order: 2 },
                  { id: 8, image_url: "/images/part7_passage5_3.jpg", image_order: 3 }
                ]
              }
            ]
          },
        ];

        // Gộp tất cả hình ảnh từ paragraphs vào passages để dễ truy cập
        passages.value.forEach(passage => {
          passage.images = passage.paragraphs.flatMap(paragraph => paragraph.images);
        });

        sections.value = [
          {
            id: 1,
            test_id: 1,
            section_type: "LISTENING",
            section_part: 1,
            title: "Photographs",
            questions: [
              {
                id: 1,
                question_number: 1,
                question_text: "What is the man doing in the picture?",
                options: [
                  { id: 1, option_text: "Sitting", option_label: "A", is_correct: false, explanation: "" },
                  { id: 2, option_text: "Standing", option_label: "B", is_correct: true, explanation: "The man is standing near a tree." },
                  { id: 3, option_text: "Running", option_label: "C", is_correct: false, explanation: "" },
                  { id: 4, option_text: "Walking", option_label: "D", is_correct: false, explanation: "" },
                ],
                transcript: "The man is standing near a tree.",
                audio_url: "/audio/part1_question1.mp3",
                image_url: "/images/part1_question1.jpg",
              },
            ],
          },
          {
            id: 2,
            test_id: 1,
            section_type: "LISTENING",
            section_part: 2,
            title: "Question-Response",
            questions: [
              {
                id: 2,
                question_number: 7,
                question_text: "What time does the meeting start?",
                options: [
                  { id: 5, option_text: "At 9 AM", option_label: "A", is_correct: true, explanation: "The meeting starts at 9 AM." },
                  { id: 6, option_text: "At 10 AM", option_label: "B", is_correct: false, explanation: "" },
                  { id: 7, option_text: "At 11 AM", option_label: "C", is_correct: false, explanation: "" },
                  { id: 8, option_text: "At 12 PM", option_label: "D", is_correct: false, explanation: "" },
                ],
                transcript: "The meeting starts at 9 AM.",
                audio_url: "/audio/part2_question7.mp3",
                image_url: null,
              },
            ],
          },
          {
            id: 3,
            test_id: 1,
            section_type: "LISTENING",
            section_part: 3,
            title: "Conversations",
            questions: [
              {
                id: 3,
                question_number: 32,
                question_text: "What is the man planning to do?",
                options: [
                  { id: 9, option_text: "Attend a meeting", option_label: "A", is_correct: false, explanation: "" },
                  { id: 10, option_text: "Travel abroad", option_label: "B", is_correct: true, explanation: "The man mentions planning to travel abroad." },
                  { id: 11, option_text: "Buy a new car", option_label: "C", is_correct: false, explanation: "" },
                  { id: 12, option_text: "Hire an assistant", option_label: "D", is_correct: false, explanation: "" },
                ],
                transcript: "Man: I'm planning to travel abroad next month.",
                audio_url: "/audio/part3_conversation1.mp3",
                image_url: null,
              },
              {
                id: 4,
                question_number: 33,
                question_text: "Where is the man traveling to?",
                options: [
                  { id: 13, option_text: "Japan", option_label: "A", is_correct: true, explanation: "The man is traveling to Japan." },
                  { id: 14, option_text: "France", option_label: "B", is_correct: false, explanation: "" },
                  { id: 15, option_text: "Brazil", option_label: "C", is_correct: false, explanation: "" },
                  { id: 16, option_text: "Australia", option_label: "D", is_correct: false, explanation: "" },
                ],
                transcript: "Woman: Oh, where to? Man: Japan.",
                audio_url: "/audio/part3_conversation1.mp3",
                image_url: null,
              },
              {
                id: 5,
                question_number: 34,
                question_text: "How long will the man stay there?",
                options: [
                  { id: 17, option_text: "One week", option_label: "A", is_correct: false, explanation: "" },
                  { id: 18, option_text: "Two weeks", option_label: "B", is_correct: true, explanation: "The man will stay for two weeks." },
                  { id: 19, option_text: "Three weeks", option_label: "C", is_correct: false, explanation: "" },
                  { id: 20, option_text: "One month", option_label: "D", is_correct: false, explanation: "" },
                ],
                transcript: "Woman: How long? Man: Two weeks.",
                audio_url: "/audio/part3_conversation1.mp3",
                image_url: null,
              },
            ],
          },
          {
            id: 4,
            test_id: 1,
            section_type: "LISTENING",
            section_part: 4,
            title: "Talks",
            questions: [
              {
                id: 6,
                question_number: 71,
                question_text: "What is the topic of the talk?",
                options: [
                  { id: 21, option_text: "New product launch", option_label: "A", is_correct: true, explanation: "The talk is about a new product launch." },
                  { id: 22, option_text: "Company merger", option_label: "B", is_correct: false, explanation: "" },
                  { id: 23, option_text: "Financial report", option_label: "C", is_correct: false, explanation: "" },
                  { id: 24, option_text: "Employee training", option_label: "D", is_correct: false, explanation: "" },
                ],
                transcript: "Today, we’re excited to announce our new product launch.",
                audio_url: "/audio/part4_talk1.mp3",
                image_url: null,
              },
            ],
          },
          {
            id: 5,
            test_id: 1,
            section_type: "READING",
            section_part: 5,
            title: "Incomplete Sentences",
            questions: [
              {
                id: 7,
                question_number: 101,
                question_text: "The company ___ a new branch next month.",
                options: [
                  { id: 25, option_text: "open", option_label: "A", is_correct: false, explanation: "" },
                  { id: 26, option_text: "opens", option_label: "B", is_correct: false, explanation: "" },
                  { id: 27, option_text: "will open", option_label: "C", is_correct: true, explanation: "The correct answer uses future tense 'will open' for next month." },
                  { id: 28, option_text: "opened", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
              },
            ],
          },
          {
            id: 6,
            test_id: 1,
            section_type: "READING",
            section_part: 6,
            title: "Text Completion",
            questions: [
              {
                id: 8,
                question_number: 131,
                question_text: "The report needs to be ___ by Friday.",
                options: [
                  { id: 29, option_text: "submit", option_label: "A", is_correct: false, explanation: "" },
                  { id: 30, option_text: "submitted", option_label: "B", is_correct: true, explanation: "Passive voice 'submitted' is correct here." },
                  { id: 31, option_text: "submitting", option_label: "C", is_correct: false, explanation: "" },
                  { id: 32, option_text: "submission", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: "/images/part6_question131.jpg",
              },
              {
                id: 9,
                question_number: 132,
                question_text: "Please ensure all data is ___ before submission.",
                options: [
                  { id: 33, option_text: "accurate", option_label: "A", is_correct: true, explanation: "Adjective 'accurate' is needed to describe data." },
                  { id: 34, option_text: "accurately", option_label: "B", is_correct: false, explanation: "" },
                  { id: 35, option_text: "accuracy", option_label: "C", is_correct: false, explanation: "" },
                  { id: 36, option_text: "accurated", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: "/images/part6_question132.jpg",
              },
            ],
          },
          {
            id: 7,
            test_id: 1,
            section_type: "READING",
            section_part: 7,
            title: "Reading Comprehension",
            questions: [
              // Nhóm 1: 147-148 (1 ảnh, 2 câu)
              {
                id: 10,
                question_number: 147,
                question_text: "What is the main idea of the passage?",
                options: [
                  { id: 37, option_text: "Company growth", option_label: "A", is_correct: true, explanation: "The passage discusses the company’s expansion strategy." },
                  { id: 38, option_text: "Employee benefits", option_label: "B", is_correct: false, explanation: "" },
                  { id: 39, option_text: "Market analysis", option_label: "C", is_correct: false, explanation: "" },
                  { id: 40, option_text: "Product development", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 1,
              },
              {
                id: 11,
                question_number: 148,
                question_text: "What is the company planning to do next year?",
                options: [
                  { id: 41, option_text: "Open new branches", option_label: "A", is_correct: true, explanation: "The passage mentions plans to open new branches next year." },
                  { id: 42, option_text: "Launch a new product", option_label: "B", is_correct: false, explanation: "" },
                  { id: 43, option_text: "Hire more staff", option_label: "C", is_correct: false, explanation: "" },
                  { id: 44, option_text: "Reduce costs", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 1,
              },
              // Nhóm 2: 155-157 (1 ảnh, 3 câu)
              {
                id: 12,
                question_number: 155,
                question_text: "What is the advertisement promoting?",
                options: [
                  { id: 45, option_text: "A new product", option_label: "A", is_correct: false, explanation: "" },
                  { id: 46, option_text: "A sale event", option_label: "B", is_correct: true, explanation: "The advertisement promotes a sale event." },
                  { id: 47, option_text: "A service", option_label: "C", is_correct: false, explanation: "" },
                  { id: 48, option_text: "A charity event", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 2,
              },
              {
                id: 13,
                question_number: 156,
                question_text: "When does the event start?",
                options: [
                  { id: 49, option_text: "Today", option_label: "A", is_correct: false, explanation: "" },
                  { id: 50, option_text: "Tomorrow", option_label: "B", is_correct: false, explanation: "" },
                  { id: 51, option_text: "Next week", option_label: "C", is_correct: true, explanation: "The event starts next week." },
                  { id: 52, option_text: "Next month", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 2,
              },
              {
                id: 14,
                question_number: 157,
                question_text: "What is the discount offered?",
                options: [
                  { id: 53, option_text: "10%", option_label: "A", is_correct: false, explanation: "" },
                  { id: 54, option_text: "20%", option_label: "B", is_correct: true, explanation: "The discount offered is 20%." },
                  { id: 55, option_text: "30%", option_label: "C", is_correct: false, explanation: "" },
                  { id: 56, option_text: "50%", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 2,
              },
              // Nhóm 3: 164-167 (1 ảnh, 4 câu)
              {
                id: 15,
                question_number: 164,
                question_text: "What is the report about?",
                options: [
                  { id: 57, option_text: "Sales performance", option_label: "A", is_correct: true, explanation: "The report discusses sales performance." },
                  { id: 58, option_text: "Employee satisfaction", option_label: "B", is_correct: false, explanation: "" },
                  { id: 59, option_text: "Market research", option_label: "C", is_correct: false, explanation: "" },
                  { id: 60, option_text: "Financial audit", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 3,
              },
              {
                id: 16,
                question_number: 165,
                question_text: "What was the sales trend last quarter?",
                options: [
                  { id: 61, option_text: "Increased", option_label: "A", is_correct: true, explanation: "The sales increased last quarter." },
                  { id: 62, option_text: "Decreased", option_label: "B", is_correct: false, explanation: "" },
                  { id: 63, option_text: "Stable", option_label: "C", is_correct: false, explanation: "" },
                  { id: 64, option_text: "Fluctuated", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 3,
              },
              {
                id: 17,
                question_number: 166,
                question_text: "Which region had the highest sales?",
                options: [
                  { id: 65, option_text: "North", option_label: "A", is_correct: false, explanation: "" },
                  { id: 66, option_text: "South", option_label: "B", is_correct: false, explanation: "" },
                  { id: 67, option_text: "East", option_label: "C", is_correct: true, explanation: "The East region had the highest sales." },
                  { id: 68, option_text: "West", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 3,
              },
              {
                id: 18,
                question_number: 167,
                question_text: "What does the report recommend?",
                options: [
                  { id: 69, option_text: "Increase marketing", option_label: "A", is_correct: true, explanation: "The report recommends increasing marketing." },
                  { id: 70, option_text: "Reduce staff", option_label: "B", is_correct: false, explanation: "" },
                  { id: 71, option_text: "Expand production", option_label: "C", is_correct: false, explanation: "" },
                  { id: 72, option_text: "Cut costs", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 3,
              },
              // Nhóm 4: 176-180 (2 ảnh, 5 câu)
              {
                id: 19,
                question_number: 176,
                question_text: "What are the two documents about?",
                options: [
                  { id: 73, option_text: "Sales and marketing", option_label: "A", is_correct: true, explanation: "The documents discuss sales and marketing." },
                  { id: 74, option_text: "HR and finance", option_label: "B", is_correct: false, explanation: "" },
                  { id: 75, option_text: "Production and logistics", option_label: "C", is_correct: false, explanation: "" },
                  { id: 76, option_text: "Training and development", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 4,
              },
              {
                id: 20,
                question_number: 177,
                question_text: "What does the first document mention?",
                options: [
                  { id: 77, option_text: "Sales targets", option_label: "A", is_correct: true, explanation: "The first document mentions sales targets." },
                  { id: 78, option_text: "Marketing budget", option_label: "B", is_correct: false, explanation: "" },
                  { id: 79, option_text: "Staff training", option_label: "C", is_correct: false, explanation: "" },
                  { id: 80, option_text: "Production costs", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 4,
              },
              {
                id: 21,
                question_number: 178,
                question_text: "What is the focus of the second document?",
                options: [
                  { id: 81, option_text: "Sales performance", option_label: "A", is_correct: false, explanation: "" },
                  { id: 82, option_text: "Marketing strategy", option_label: "B", is_correct: true, explanation: "The second document focuses on marketing strategy." },
                  { id: 83, option_text: "Employee benefits", option_label: "C", is_correct: false, explanation: "" },
                  { id: 84, option_text: "Financial audit", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 4,
              },
              {
                id: 22,
                question_number: 179,
                question_text: "What is the deadline mentioned in the first document?",
                options: [
                  { id: 85, option_text: "Next week", option_label: "A", is_correct: false, explanation: "" },
                  { id: 86, option_text: "Next month", option_label: "B", is_correct: false, explanation: "" },
                  { id: 87, option_text: "End of the year", option_label: "C", is_correct: true, explanation: "The deadline is the end of the year." },
                  { id: 88, option_text: "Tomorrow", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 4,
              },
              {
                id: 23,
                question_number: 180,
                question_text: "Who prepared the second document?",
                options: [
                  { id: 89, option_text: "Marketing team", option_label: "A", is_correct: true, explanation: "The marketing team prepared the second document." },
                  { id: 90, option_text: "HR department", option_label: "B", is_correct: false, explanation: "" },
                  { id: 91, option_text: "Finance team", option_label: "C", is_correct: false, explanation: "" },
                  { id: 92, option_text: "CEO", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 4,
              },
              // Nhóm 5: 186-190 (3 ảnh, 5 câu)
              {
                id: 24,
                question_number: 186,
                question_text: "What are the three documents about?",
                options: [
                  { id: 93, option_text: "Sales, marketing, and finance", option_label: "A", is_correct: true, explanation: "The documents are about sales, marketing, and finance." },
                  { id: 94, option_text: "HR, training, and policy", option_label: "B", is_correct: false, explanation: "" },
                  { id: 95, option_text: "Production, logistics, and sales", option_label: "C", is_correct: false, explanation: "" },
                  { id: 96, option_text: "Budget, audit, and marketing", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 5,
              },
              {
                id: 25,
                question_number: 187,
                question_text: "What does the first document discuss?",
                options: [
                  { id: 97, option_text: "Sales performance", option_label: "A", is_correct: true, explanation: "The first document discusses sales performance." },
                  { id: 98, option_text: "Marketing strategy", option_label: "B", is_correct: false, explanation: "" },
                  { id: 99, option_text: "Financial report", option_label: "C", is_correct: false, explanation: "" },
                  { id: 100, option_text: "Employee benefits", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 5,
              },
              {
                id: 26,
                question_number: 188,
                question_text: "What is the focus of the second document?",
                options: [
                  { id: 101, option_text: "Sales targets", option_label: "A", is_correct: false, explanation: "" },
                  { id: 102, option_text: "Marketing campaign", option_label: "B", is_correct: true, explanation: "The second document focuses on a marketing campaign." },
                  { id: 103, option_text: "Staff training", option_label: "C", is_correct: false, explanation: "" },
                  { id: 104, option_text: "Production costs", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 5,
              },
              {
                id: 27,
                question_number: 189,
                question_text: "What does the third document recommend?",
                options: [
                  { id: 105, option_text: "Increase budget", option_label: "A", is_correct: true, explanation: "The third document recommends increasing the budget." },
                  { id: 106, option_text: "Reduce staff", option_label: "B", is_correct: false, explanation: "" },
                  { id: 107, option_text: "Expand production", option_label: "C", is_correct: false, explanation: "" },
                  { id: 108, option_text: "Cut costs", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 5,
              },
              {
                id: 28,
                question_number: 190,
                question_text: "Who prepared the first document?",
                options: [
                  { id: 109, option_text: "Sales team", option_label: "A", is_correct: true, explanation: "The sales team prepared the first document." },
                  { id: 110, option_text: "Marketing team", option_label: "B", is_correct: false, explanation: "" },
                  { id: 111, option_text: "Finance team", option_label: "C", is_correct: false, explanation: "" },
                  { id: 112, option_text: "HR department", option_label: "D", is_correct: false, explanation: "" },
                ],
                image_url: null,
                passage_id: 5,
              },
            ],
          },
        ].filter((section) => section.test_id === parseInt(selectedTestId.value));
      } catch (error) {
        console.error("Lỗi khi lấy danh sách section:", error);
        sections.value = [];
        selectedListeningPart.value = "";
        selectedReadingPart.value = "";
        passages.value = [];
      }
    };

    const setActiveTab = (tab) => {
      activeTab.value = tab;
      if (tab === "listening" && sections.value.length > 0) {
        selectedListeningPart.value = "1";
        selectedReadingPart.value = "";
      } else if (tab === "reading" && sections.value.length > 0) {
        selectedReadingPart.value = "5";
        selectedListeningPart.value = "";
      }
    };

    const deleteQuestion = async (sectionId, questionId) => {
      if (!sectionId) {
        alert("Section chưa được tạo, không thể xóa câu hỏi!");
        return;
      }
      if (confirm("Bạn có chắc chắn muốn xóa câu hỏi này?")) {
        try {
          sections.value = sections.value.map((section) => {
            if (section.id === sectionId) {
              return {
                ...section,
                questions: section.questions.filter((question) => question.id !== questionId),
              };
            }
            return section;
          });
          alert("Xóa câu hỏi thành công!");
        } catch (error) {
          console.error("Lỗi khi xóa câu hỏi:", error);
          alert("Có lỗi xảy ra khi xóa câu hỏi.");
        }
      }
    };

    const openAddQuestionModal = () => {
      questionModalMode.value = "add";
      resetQuestionForm();
      const part = selectedListeningPart.value || selectedReadingPart.value;
      const currentQuestions = currentSection.value?.questions || [];
      const range = partRanges[part];
      if (currentQuestions.length > 0) {
        const lastQuestionNumber = Math.max(
          ...currentQuestions.map((q) => q.question_number)
        );
        newQuestion.value.question_number = Math.min(
          lastQuestionNumber + 1,
          range.max
        );
      } else {
        newQuestion.value.question_number = range.min;
      }
      addEditModal = new bootstrap.Modal(
        document.getElementById("addEditQuestionModal"),
        {}
      );
      addEditModal.show();
    };

    const openEditQuestionModal = (question) => {
      questionModalMode.value = "edit";
      editingQuestionId.value = question.id;
      newQuestion.value = {
        id: question.id,
        question_number: question.question_number,
        question_text: question.question_text,
        options: {
          A: { option_text: getOption(question, 'A'), is_correct: getCorrectAnswer(question) === 'A' },
          B: { option_text: getOption(question, 'B'), is_correct: getCorrectAnswer(question) === 'B' },
          C: { option_text: getOption(question, 'C'), is_correct: getCorrectAnswer(question) === 'C' },
          D: { option_text: getOption(question, 'D'), is_correct: getCorrectAnswer(question) === 'D' },
        },
        correct_answer: getCorrectAnswer(question),
        transcript: question.transcript || "",
        audio_url: question.audio_url || "",
        image_url: question.image_url || "",
        explanation: getExplanation(question) || "",
        passage_id: question.passage_id || null,
      };
      addEditModal = new bootstrap.Modal(
        document.getElementById("addEditQuestionModal"),
        {}
      );
      addEditModal.show();
    };

    const saveQuestion = async () => {
      const part = selectedListeningPart.value || selectedReadingPart.value;
      const sectionId = currentSection.value?.id;
      if (!sectionId) {
        alert("Section chưa được tạo, không thể lưu câu hỏi!");
        return;
      }

      const questionData = {
        id: newQuestion.value.id || Date.now(),
        question_number: newQuestion.value.question_number,
        question_text: newQuestion.value.question_text,
        options: [
          { id: Date.now() + 1, option_text: newQuestion.value.options['A'].option_text, option_label: "A", is_correct: newQuestion.value.correct_answer === "A", explanation: newQuestion.value.explanation },
          { id: Date.now() + 2, option_text: newQuestion.value.options['B'].option_text, option_label: "B", is_correct: newQuestion.value.correct_answer === "B", explanation: "" },
          { id: Date.now() + 3, option_text: newQuestion.value.options['C'].option_text, option_label: "C", is_correct: newQuestion.value.correct_answer === "C", explanation: "" },
          { id: Date.now() + 4, option_text: newQuestion.value.options['D'].option_text, option_label: "D", is_correct: newQuestion.value.correct_answer === "D", explanation: "" },
        ],
        transcript: newQuestion.value.transcript,
        audio_url: newQuestion.value.audio_url,
        image_url: newQuestion.value.image_url,
        passage_id: newQuestion.value.passage_id,
      };

      const currentQuestions = currentSection.value.questions || [];
      const range = partRanges[part];

      if (
        questionData.question_number < range.min ||
        questionData.question_number > range.max
      ) {
        alert(
          `Số thứ tự câu hỏi phải nằm trong khoảng từ ${range.min} đến ${range.max}!`
        );
        return;
      }

      if (
        currentQuestions.some(
          (q) =>
            q.question_number === questionData.question_number &&
            (questionModalMode.value === "add" || q.id !== editingQuestionId.value)
        )
      ) {
        alert("Số thứ tự câu hỏi đã tồn tại!");
        return;
      }

      try {
        if (questionModalMode.value === "add") {
          sections.value = sections.value.map((section) => {
            if (section.id === sectionId) {
              return {
                ...section,
                questions: [...(section.questions || []), questionData],
              };
            }
            return section;
          });
          alert("Thêm câu hỏi thành công!");
        } else {
          sections.value = sections.value.map((section) => {
            if (section.id === sectionId) {
              return {
                ...section,
                questions: section.questions.map((q) =>
                  q.id === editingQuestionId.value ? questionData : q
                ),
              };
            }
            return section;
          });
          alert("Cập nhật câu hỏi thành công!");
        }
        addEditModal.hide();
        resetQuestionForm();
      } catch (error) {
        console.error("Lỗi khi lưu câu hỏi:", error);
        alert("Có lỗi xảy ra khi lưu câu hỏi.");
      }
    };

    const resetQuestionForm = () => {
      newQuestion.value = {
        id: null,
        question_number: null,
        question_text: "",
        options: {
          A: { option_text: "", is_correct: false },
          B: { option_text: "", is_correct: false },
          C: { option_text: "", is_correct: false },
          D: { option_text: "", is_correct: false },
        },
        correct_answer: "",
        transcript: "",
        audio_url: "",
        image_url: "",
        explanation: "",
        passage_id: null,
      };
      editingQuestionId.value = null;
    };

    const showTranscript = (transcript) => {
      selectedTranscript.value = transcript;
      const modal = new bootstrap.Modal(
        document.getElementById("transcriptModal"),
        {}
      );
      modal.show();
    };

    const showFullQuestion = (questionText) => {
      selectedFullQuestion.value = questionText;
      const modal = new bootstrap.Modal(
        document.getElementById("questionModal"),
        {}
      );
      modal.show();
    };

    const showParagraphImage = (imageUrl) => {
      selectedParagraphImage.value = imageUrl;
      const modal = new bootstrap.Modal(
        document.getElementById("paragraphImageModal"),
        {}
      );
      modal.show();
    };

    const truncateText = (text, maxLength) => {
      if (!text) return "";
      return text.length > maxLength ? text.slice(0, maxLength) + "..." : text;
    };

    const getFileName = (url) => {
      if (!url) return "";
      const parts = url.split("/");
      return parts[parts.length - 1];
    };

    return {
      tests,
      selectedTestId,
      fetchSections,
      sections,
      activeTab,
      setActiveTab,
      selectedListeningPart,
      selectedReadingPart,
      currentListeningSection,
      currentReadingSection,
      questionGroups,
      isPartFull,
      canHaveImage,
      getOption,
      getCorrectAnswer,
      getExplanation,
      deleteQuestion,
      openAddQuestionModal,
      openEditQuestionModal,
      saveQuestion,
      showTranscript,
      selectedTranscript,
      showFullQuestion,
      selectedFullQuestion,
      showParagraphImage,
      selectedParagraphImage,
      truncateText,
      getFileName,
      newQuestion,
      questionModalMode,
      passages,
      partRanges,
    };
  },
};
</script>

<style scoped>
.test-manager {
  max-width: 100%;
  margin: 0 auto;
}

.nav-tabs {
  margin-bottom: 1rem;
}

.nav-tabs .nav-link.active {
  font-weight: bold;
}

.table-responsive {
  overflow-x: auto;
}

.table th,
.table td {
  vertical-align: middle;
}

.transcript-content,
.question-content {
  white-space: pre-wrap;
  background-color: #f8f9fa;
  padding: 1rem;
  border-radius: 0.25rem;
}

.btn-sm {
  padding: 0.25rem 0.5rem;
  font-size: 0.875rem;
}

.d-flex.flex-column.gap-2 {
  gap: 0.5rem;
}

.text-muted {
  color: #6c757d !important;
}

.modal-body img {
  max-width: 100%;
  height: auto;
}

.table-hover tbody tr:hover {
  background-color: #f1f1f1;
}

.alert-info {
  font-size: 1rem;
}
</style>