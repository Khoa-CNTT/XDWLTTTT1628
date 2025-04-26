<template>
  <main class="container mt-4">
    <div class="row">
      <!-- Left Column: Test Content -->
      <div class="col-lg-8 left-column">
        <div class="submit-test-content-wrapper">
          <!-- Thông báo xanh phía trên -->
          <div class="alert alert-success" role="alert">
            <i class="fas fa-exclamation-circle mr-2 me-2"></i>
            <strong> Chúc mừng</strong> bạn đã <u>hoàn thành bài thi</u>! Hãy
            xem xét kết quả và <u>xem lại những phần bạn trả lời sai</u> để cải
            thiện kỹ năng. Đừng quên theo dõi tiến trình và tiếp tục luyện tập
            để đạt được mục tiêu của bạn!
          </div>

          <!-- Tiêu đề kết quả -->
          <h2 class="test-result-title">
            Kết quả thi: Practice Set TOEIC Test 10
          </h2>

          <!-- Nút thao tác -->
          <div class="action-buttons mb-3">
            <button class="btn btn-sm btn-primary me-2">Xem chi tiết</button>
            <button class="btn btn-sm btn-outline-primary">
              Xem đề thi đã làm
            </button>
          </div>

          <!-- Bảng tổng kết -->
          <div class="result-summary row mb-4">
            <div class="col-12">
              <div class="summary-grid">
                <div class="summary-card">
                  <div class="stat-icon accuracy-icon">
                    <i class="fas fa-percentage"></i>
                  </div>
                  <p class="stat-label">Độ chính xác</p>
                  <h3 class="stat-value text-success-percent">71.5%</h3>
                </div>
                <div class="summary-card accuracy-card">
                  <div class="stat-icon check-icon">
                    <i class="fas fa-check"></i>
                  </div>
                  <p class="stat-label">Kết quả làm bài</p>
                  <h3 class="stat-value">143/200</h3>
                </div>
                <div class="summary-card">
                  <div class="stat-icon correct-icon">
                    <i class="fas fa-check-circle"></i>
                  </div>
                  <p class="stat-label">Trả lời đúng</p>
                  <h3 class="stat-value text-success">143</h3>
                </div>
                <div class="summary-card">
                  <div class="stat-icon wrong-icon">
                    <i class="fas fa-times-circle"></i>
                  </div>
                  <p class="stat-label">Trả lời sai</p>
                  <h3 class="stat-value text-danger">57</h3>
                </div>
                <div class="summary-card">
                  <div class="stat-icon skip-icon">
                    <i class="fas fa-forward"></i>
                  </div>
                  <p class="stat-label">Bỏ qua</p>
                  <h3 class="stat-value text-secondary">0</h3>
                </div>
                <div class="summary-card">
                  <div class="stat-icon score-icon">
                    <i class="fas fa-trophy"></i>
                  </div>
                  <p class="stat-label">Điểm</p>
                  <h3 class="stat-value text-primary">720</h3>
                </div>
                <div class="summary-card">
                  <div class="stat-icon time-icon">
                    <i class="fas fa-clock"></i>
                  </div>
                  <p class="stat-label">Thời gian hoàn thành</p>
                  <h3 class="stat-value">1:57:43</h3>
                </div>
              </div>
            </div>
          </div>

          <!-- Bảng điểm -->
          <div class="score-section mb-4">
            <div class="score-details row">
              <div class="col-md-6">
                <div class="score-card">
                  <h4>Listening</h4>
                  <div class="score-value">350/495</div>
                  <div class="score-progress">
                    <div class="progress">
                      <div
                        class="progress-bar bg-primary"
                        role="progressbar"
                        style="width: 71%"
                      ></div>
                    </div>
                    <span class="score-percent">Trả lời đúng: 71/100</span>
                  </div>
                </div>
              </div>
              <div class="col-md-6">
                <div class="score-card">
                  <h4>Reading</h4>
                  <div class="score-value">370/495</div>
                  <div class="score-progress">
                    <div class="progress">
                      <div
                        class="progress-bar bg-primary"
                        role="progressbar"
                        style="width: 70%"
                      ></div>
                    </div>
                    <span class="score-percent">Trả lời đúng: 70/100</span>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Hiển thị đáp án -->
          <div class="answer-section">
            <h4>Đáp án</h4>

            <div class="answer-controls d-flex justify-content-between mb-3">
              <button class="btn btn-sm btn-sky-custom">
                Xem chi tiết đáp án
              </button>
              <div class="answer-filter d-flex align-items-center">
                <button class="btn btn-sm btn-outline-secondary">
                  Lọc đáp án
                </button>
                <input
                  type="text"
                  class="filter-input me-2"
                  placeholder="Nhập từ khóa để lọc..."
                  v-model="searchQuery"
                />
              </div>
            </div>

            <!-- Tip -->
            <div class="alert alert-info" role="alert">
              <i class="fas fa-leaf me-2"></i>
              Tips: Ấn vào mũi tên trái hoặc đầu để đi đến câu hỏi của bạn chọn
              để mở flashcards và hoạt động ghi chú.
            </div>

            <!-- Đáp án chi tiết -->
            <div class="part-answers" v-for="part in parts" :key="part.id">
              <h5>Part {{ part.id }}</h5>
              <div class="answer-grid row">
                <div
                  class="col-2 answer-item"
                  v-for="answer in filteredAnswers(part.id).slice(
                    0,
                    showMore[part.id] ? undefined : 6
                  )"
                  :key="answer.questionNumber"
                >
                  <div class="answer-number">{{ answer.questionNumber }}</div>
                  <div class="answer-options">
                    <span class="option correct"
                      >{{ answer.correctAnswer }}:</span
                    >
                    <span class="option selected">{{
                      answer.selectedAnswer
                    }}</span>
                    <span
                      class="answer-status"
                      :class="answer.isCorrect ? 'correct' : 'wrong'"
                      >{{ answer.isCorrect ? "✅" : "❌" }}</span
                    >
                    <a
                      href="#"
                      class="answer-details"
                      @click.prevent="openResultSummary(answer)"
                      >[Chi tiết]</a
                    >
                  </div>
                </div>
              </div>
              <button
                v-if="filteredAnswers(part.id).length > 6"
                class="btn btn-sm btn-outline-primary mt-2"
                @click="toggleShowMore(part.id)"
              >
                {{ showMore[part.id] ? "Thu gọn" : "Xem thêm" }}
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Right Column: Sidebar -->
      <div class="col-lg-4 right-column">
        <div class="sidebar">
          <div class="user-info">
            <div class="avatar"></div>
            <div class="user-details">
              <h4>nguyenlongvu22122003</h4>
              <p>Ngày dự thi: 13/12/2025</p>
              <p>Ngành dự thi: TOEIC</p>
              <p>Điểm mục tiêu: 750</p>
              <button class="stats-button">
                <span class="icon">📊</span> Lịch sử bài thi
              </button>
            </div>
          </div>
          <div class="promo-banner">
            <h3>Kiểm tra trình độ miễn phí</h3>
            <p>Nhanh chóng - Chính xác - Hiệu quả</p>
            <button class="start-button">START</button>
          </div>
          <div class="study-group">
            <h4>Trao đổi, học tập, Flashcards</h4>
            <p>StudyZone Extension</p>
            <button class="extension-button">Cài đặt ngay</button>
          </div>
          <div class="community">
            <h4>Cộng đồng luyện thi TOEIC</h4>
            <p>Tham gia nhóm Facebook StudyZone</p>
            <button class="join-button">Tham gia ngay</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Pop-up ResultSummary -->
    <div v-if="showModal" class="modal-overlay">
      <div class="modal-content">
        <ResultSummary
          :question="selectedQuestion"
          @close="showModal = false"
        />
      </div>
    </div>
  </main>
</template>

<script>
import { ref } from "vue";
import ResultSummary from "@/components/exams/ResultSummary.vue";

export default {
  name: "SubmitTest",
  components: {
    ResultSummary,
  },
  setup() {
    // Dữ liệu mẫu
    const testResult = ref({
      testName: "Practice Set TOEIC Test 10",
      totalCorrect: 143,
      totalWrong: 57,
      totalSkipped: 0,
      totalScore: 720,
      accuracy: 71.5,
      timeTaken: "1:57:43",
      listening: {
        score: 350,
        maxScore: 495,
        correctPercent: 71,
      },
      reading: {
        score: 370,
        maxScore: 495,
        correctPercent: 70,
      },
    });

    // Dữ liệu mẫu đáp án (mô phỏng từ database)
    const answers = ref([
      // Part 1 (6 câu)
      {
        part: 1,
        questionNumber: 1,
        correctAnswer: "C",
        selectedAnswer: "C",
        isCorrect: true,
        image: "https://picsum.photos/500/300?random=1",
        audio: "/assets/audio/part1_question1.mp3",
        options: [
          { value: "A", text: "Option A for photo 1" },
          { value: "B", text: "Option B for photo 1" },
          { value: "C", text: "Option C for photo 1" },
          { value: "D", text: "Option D for photo 1" },
        ],
        explanation:
          "The correct answer is C because the photo depicts a scene that matches the description in option C.",
        transcript:
          "Transcript for question 1: This describes the scene in the photo where two people are rowing a canoe on a lake.",
        questionText: "What are the people doing in the photo?",
      },
      {
        part: 1,
        questionNumber: 2,
        correctAnswer: "C",
        selectedAnswer: "C",
        isCorrect: true,
        image: "https://picsum.photos/500/300?random=2",
        audio: "/assets/audio/part1_question2.mp3",
        options: [
          { value: "A", text: "Option A for photo 2" },
          { value: "B", text: "Option B for photo 2" },
          { value: "C", text: "Option C for photo 2" },
          { value: "D", text: "Option D for photo 2" },
        ],
        explanation:
          "The correct answer is C because the photo depicts a scene that matches the description in option C.",
        transcript:
          "Transcript for question 2: This describes the scene in the photo for question 2.",
        questionText: "What is happening in the photo?",
      },
      {
        part: 1,
        questionNumber: 3,
        correctAnswer: "D",
        selectedAnswer: "D",
        isCorrect: true,
        image: "https://picsum.photos/500/300?random=3",
        audio: "/assets/audio/part1_question3.mp3",
        options: [
          { value: "A", text: "Option A for photo 3" },
          { value: "B", text: "Option B for photo 3" },
          { value: "C", text: "Option C for photo 3" },
          { value: "D", text: "Option D for photo 3" },
        ],
        explanation:
          "The correct answer is D because the photo depicts a scene that matches the description in option D.",
        transcript:
          "Transcript for question 3: This describes the scene in the photo for question 3.",
        questionText: "What are the people doing in the photo?",
      },
      {
        part: 1,
        questionNumber: 4,
        correctAnswer: "D",
        selectedAnswer: "D",
        isCorrect: true,
        image: "https://picsum.photos/500/300?random=4",
        audio: "/assets/audio/part1_question4.mp3",
        options: [
          { value: "A", text: "Workers are exiting from the back of a van." },
          { value: "B", text: "Some trees have fallen across a road." },
          { value: "C", text: "A man is watering some potted plants." },
          { value: "D", text: "Some people are seated outdoors." },
        ],
        explanation:
          "The correct answer is D because the photo depicts a scene that matches the description in option D.",
        transcript:
          "Transcript for question 4: This describes the scene in the photo where some people are seated outdoors.",
        questionText: "What are the people doing in the photo?",
      },
      {
        part: 1,
        questionNumber: 5,
        correctAnswer: "D",
        selectedAnswer: "D",
        isCorrect: true,
        image: "https://picsum.photos/500/300?random=5",
        audio: "/assets/audio/part1_question5.mp3",
        options: [
          { value: "A", text: "Option A for photo 5" },
          { value: "B", text: "Option B for photo 5" },
          { value: "C", text: "Option C for photo 5" },
          { value: "D", text: "Option D for photo 5" },
        ],
        explanation:
          "The correct answer is D because the photo depicts a scene that matches the description in option D.",
        transcript:
          "Transcript for question 5: This describes the scene in the photo for question 5.",
        questionText: "What is happening in the photo?",
      },
      {
        part: 1,
        questionNumber: 6,
        correctAnswer: "B",
        selectedAnswer: "B",
        isCorrect: true,
        image: "https://picsum.photos/500/300?random=6",
        audio: "/assets/audio/part1_question6.mp3",
        options: [
          { value: "A", text: "Option A for photo 6" },
          { value: "B", text: "Option B for photo 6" },
          { value: "C", text: "Option C for photo 6" },
          { value: "D", text: "Option D for photo 6" },
        ],
        explanation:
          "The correct answer is B because the photo depicts a scene that matches the description in option B.",
        transcript:
          "Transcript for question 6: This describes the scene in the photo for question 6.",
        questionText: "What are the people doing in the photo?",
      },
      // Part 2 (25 câu, chỉ liệt kê vài câu mẫu)
      {
        part: 2,
        questionNumber: 7,
        correctAnswer: "B",
        selectedAnswer: "B",
        isCorrect: true,
        audio: "/assets/audio/part2_question1.mp3",
        options: [
          { value: "A", text: "Response A for question 7" },
          { value: "B", text: "Response B for question 7" },
          { value: "C", text: "Response C for question 7" },
          { value: "D", text: "Response D for question 7" },
        ],
        explanation:
          "The correct answer is B because it matches the context of the question.",
        transcript:
          "Transcript for question 7: This is the audio transcript for the question and response in Part 2.",
        questionText: "Where is the meeting taking place?",
      },
      {
        part: 2,
        questionNumber: 8,
        correctAnswer: "C",
        selectedAnswer: "B",
        isCorrect: false,
        audio: "/assets/audio/part2_question2.mp3",
        options: [
          { value: "A", text: "Response A for question 8" },
          { value: "B", text: "Response B for question 8" },
          { value: "C", text: "Response C for question 8" },
          { value: "D", text: "Response D for question 8" },
        ],
        explanation:
          "The correct answer is C because it matches the context of the question. The user selected B, which is incorrect.",
        transcript:
          "Transcript for question 8: This is the audio transcript for the question and response in Part 2.",
        questionText: "What time does the event start?",
      },
      {
        part: 2,
        questionNumber: 9,
        correctAnswer: "B",
        selectedAnswer: "B",
        isCorrect: true,
        audio: "/assets/audio/part2_question3.mp3",
        options: [
          { value: "A", text: "Response A for question 9" },
          { value: "B", text: "Response B for question 9" },
          { value: "C", text: "Response C for question 9" },
          { value: "D", text: "Response D for question 9" },
        ],
        explanation:
          "The correct answer is B because it matches the context of the question.",
        transcript:
          "Transcript for question 9: This is the audio transcript for the question and response in Part 2.",
        questionText: "Who is in charge of the project?",
      },
      {
        part: 2,
        questionNumber: 10,
        correctAnswer: "C",
        selectedAnswer: "C",
        isCorrect: true,
        audio: "/assets/audio/part2_question4.mp3",
        options: [
          { value: "A", text: "Response A for question 10" },
          { value: "B", text: "Response B for question 10" },
          { value: "C", text: "Response C for question 10" },
          { value: "D", text: "Response D for question 10" },
        ],
        explanation:
          "The correct answer is C because it matches the context of the question.",
        transcript:
          "Transcript for question 10: This is the audio transcript for the question and response in Part 2.",
        questionText: "How will the team communicate during the event?",
      },
      // Part 3 (39 câu, chỉ liệt kê vài câu mẫu)
      {
        part: 3,
        questionNumber: 32,
        correctAnswer: "A",
        selectedAnswer: "A",
        isCorrect: true,
        audio: "/assets/audio/part3_conversation1.mp3",
        options: [
          { value: "A", text: "To request a ticket change" },
          { value: "B", text: "To make a dinner reservation" },
          { value: "C", text: "To order merchandise" },
          { value: "D", text: "To plan a vacation" },
        ],
        explanation:
          "The correct answer is A because the conversation indicates the woman is calling to request a ticket change.",
        transcript:
          "Transcript for question 32: Woman: Hello, I’d like to change my ticket for the concert. Man: Sure, let me check the availability for you.",
        questionText: "Why is the woman calling?",
      },
      {
        part: 3,
        questionNumber: 33,
        correctAnswer: "B",
        selectedAnswer: "B",
        isCorrect: true,
        audio: "/assets/audio/part3_conversation1.mp3",
        options: [
          { value: "A", text: "An event was canceled" },
          { value: "B", text: "A line is very long" },
          { value: "C", text: "A payment option is unavailable" },
          { value: "D", text: "A computer program is not working" },
        ],
        explanation:
          "The correct answer is B because the man apologizes for the long line.",
        transcript:
          "Transcript for question 33: Man: I’m sorry for the long line at the ticket counter. We’re doing our best to assist everyone.",
        questionText: "Why does the man apologize?",
      },
      {
        part: 3,
        questionNumber: 34,
        correctAnswer: "C",
        selectedAnswer: "B",
        isCorrect: false,
        audio: "/assets/audio/part3_conversation1.mp3",
        options: [
          { value: "A", text: "A meal voucher" },
          { value: "B", text: "Some free souvenirs" },
          { value: "C", text: "An increase in price" },
          { value: "D", text: "A refund policy" },
        ],
        explanation:
          "The correct answer is C because the man reminds the woman about an increase in price. The user selected B, which is incorrect.",
        transcript:
          "Transcript for question 34: Man: Just a reminder, there’s been a slight increase in price due to the event’s popularity.",
        questionText: "What does the man remind the woman about?",
      },
      // Part 4 (30 câu, chỉ liệt kê vài câu mẫu)
      {
        part: 4,
        questionNumber: 71,
        correctAnswer: "D",
        selectedAnswer: "D",
        isCorrect: true,
        audio: "/assets/audio/part4_talk1.mp3",
        options: [
          { value: "A", text: "A new company policy" },
          { value: "B", text: "A product launch event" },
          { value: "C", text: "A staff training session" },
          { value: "D", text: "A company merger" },
        ],
        explanation:
          "The correct answer is D because the talk discusses a company merger.",
        transcript:
          "Transcript for question 71: Welcome everyone. Today, I’m excited to announce that our company will be merging with a leading tech firm.",
        questionText: "What is the main topic of the talk?",
      },
      {
        part: 4,
        questionNumber: 72,
        correctAnswer: "A",
        selectedAnswer: "B",
        isCorrect: false,
        audio: "/assets/audio/part4_talk1.mp3",
        options: [
          { value: "A", text: "New employees" },
          { value: "B", text: "Senior managers" },
          { value: "C", text: "Clients" },
          { value: "D", text: "Shareholders" },
        ],
        explanation:
          "The correct answer is A because the speaker is addressing new employees. The user selected B, which is incorrect.",
        transcript:
          "Transcript for question 72: I’d like to extend a warm welcome to all the new employees joining us today.",
        questionText: "Who is the speaker addressing?",
      },
      // Part 5 (30 câu, chỉ liệt kê vài câu mẫu)
      {
        part: 5,
        questionNumber: 101,
        correctAnswer: "C",
        selectedAnswer: "C",
        isCorrect: true,
        options: [
          { value: "A", text: "encourage" },
          { value: "B", text: "is encouraging" },
          { value: "C", text: "encouraged" },
          { value: "D", text: "was encouraged" },
        ],
        explanation:
          "The correct answer is C because the past tense 'encouraged' fits the context of the sentence.",
        transcript:
          "Transcript for question 101: This is a placeholder transcript for Part 5, which does not typically have audio.",
        questionText:
          "The team was ______ to participate in the new initiative.",
      },
      {
        part: 5,
        questionNumber: 102,
        correctAnswer: "B",
        selectedAnswer: "A",
        isCorrect: false,
        options: [
          { value: "A", text: "for" },
          { value: "B", text: "about" },
          { value: "C", text: "to" },
          { value: "D", text: "at" },
        ],
        explanation:
          "The correct answer is B because the preposition 'about' is appropriate for the context. The user selected A, which is incorrect.",
        transcript:
          "Transcript for question 102: This is a placeholder transcript for Part 5, which does not typically have audio.",
        questionText: "She was concerned ______ the project deadline.",
      },
      // Part 6 (16 câu, chỉ liệt kê vài câu mẫu)
      {
        part: 6,
        questionNumber: 131,
        correctAnswer: "A",
        selectedAnswer: "A",
        isCorrect: true,
        image: "https://picsum.photos/500/300?random=part6_group1",
        options: [
          { value: "A", text: "Option A for question 131" },
          { value: "B", text: "Option B for question 131" },
          { value: "C", text: "Option C for question 131" },
          { value: "D", text: "Option D for question 131" },
        ],
        explanation:
          "The correct answer is A because it fits the context of the passage.",
        transcript:
          "Transcript for question 131: This is a placeholder transcript for Part 6, which does not typically have audio.",
        questionText:
          "What is the purpose of the memo mentioned in the passage?",
      },
      {
        part: 6,
        questionNumber: 132,
        correctAnswer: "D",
        selectedAnswer: "D",
        isCorrect: true,
        image: "https://picsum.photos/500/300?random=part6_group1",
        options: [
          { value: "A", text: "Option A for question 132" },
          { value: "B", text: "Option B for question 132" },
          { value: "C", text: "Option C for question 132" },
          { value: "D", text: "Option D for question 132" },
        ],
        explanation:
          "The correct answer is D because it fits the context of the passage.",
        transcript:
          "Transcript for question 132: This is a placeholder transcript for Part 6, which does not typically have audio.",
        questionText: "Who is the intended audience of the passage?",
      },
      // Part 7 (54 câu, chỉ liệt kê vài câu mẫu)
      {
        part: 7,
        questionNumber: 147,
        correctAnswer: "B",
        selectedAnswer: "B",
        isCorrect: true,
        image: "https://picsum.photos/500/300?random=part7_single1",
        options: [
          { value: "A", text: "Option A for question 147" },
          { value: "B", text: "Option B for question 147" },
          { value: "C", text: "Option C for question 147" },
          { value: "D", text: "Option D for question 147" },
        ],
        explanation:
          "The correct answer is B because it matches the main purpose of the passage.",
        transcript:
          "Transcript for question 147: This is a placeholder transcript for Part 7, which does not typically have audio.",
        questionText: "What is the main purpose of the passage?",
      },
      {
        part: 7,
        questionNumber: 148,
        correctAnswer: "C",
        selectedAnswer: "A",
        isCorrect: false,
        image: "https://picsum.photos/500/300?random=part7_single1",
        options: [
          { value: "A", text: "Option A for question 148" },
          { value: "B", text: "Option B for question 148" },
          { value: "C", text: "Option C for question 148" },
          { value: "D", text: "Option D for question 148" },
        ],
        explanation:
          "The correct answer is C because it matches the main purpose of the passage. The user selected A, which is incorrect.",
        transcript:
          "Transcript for question 148: This is a placeholder transcript for Part 7, which does not typically have audio.",
        questionText: "What does the passage suggest about the company?",
      },
    ]);

    // Danh sách Part
    const parts = ref([
      { id: 1, total: 6 },
      { id: 2, total: 25 },
      { id: 3, total: 39 },
      { id: 4, total: 30 },
      { id: 5, total: 30 },
      { id: 6, total: 16 },
      { id: 7, total: 54 },
    ]);

    // Trạng thái "Xem thêm" cho mỗi Part
    const showMore = ref({
      1: false,
      2: false,
      3: false,
      4: false,
      5: false,
      6: false,
      7: false,
    });

    // Tìm kiếm
    const searchQuery = ref("");

    // Pop-up state
    const showModal = ref(false);
    const selectedQuestion = ref(null);

    // Lọc đáp án theo Part và từ khóa tìm kiếm
    const filteredAnswers = (partId) => {
      return answers.value.filter(
        (answer) =>
          answer.part === partId &&
          (searchQuery.value === "" ||
            answer.questionNumber.toString().includes(searchQuery.value))
      );
    };

    // Toggle "Xem thêm"
    const toggleShowMore = (partId) => {
      showMore.value[partId] = !showMore.value[partId];
    };

    // Mở pop-up ResultSummary
    const openResultSummary = (answer) => {
      selectedQuestion.value = answer;
      showModal.value = true;
    };

    return {
      testResult,
      parts,
      filteredAnswers,
      showMore,
      toggleShowMore,
      searchQuery,
      showModal,
      selectedQuestion,
      openResultSummary,
    };
  },
};
</script>

<style scoped>
.container {
  max-width: 1500px;
}

.submit-test-content-wrapper {
  background-color: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  text-align: left;
  margin-bottom: 20px;
}

.alert {
  padding: 0.75rem 1.25rem;
}

.alert-success {
  background-color: #d8f0e2;
  border-color: #c8ead6;
  color: #1f5e39;
  font-size: 1rem;
  line-height: 1.7;
  gap: 2px;
}

u {
  text-decoration: underline;
  text-underline-offset: 2px;
}

.test-result-title {
  font-size: 1.25rem;
  font-weight: 600;
  color: #333;
  margin: 15px 0;
}

.action-buttons .btn {
  font-size: 0.85rem;
  padding: 0.25rem 0.75rem;
}

.result-summary {
  margin-top: 20px;
}

.summary-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
}

.summary-card {
  flex: 1 1 calc(20% - 15px);
  background: #f8f9fa;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 15px;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s;
}

.summary-card.accuracy-card {
  flex: 1 1 calc(40% - 15px);
}

.summary-card:nth-child(n + 5) {
  flex: 1 1 calc(33.33% - 15px);
}

.summary-card:hover {
  transform: translateY(-2px);
}

.stat-icon {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 10px;
}

.check-icon {
  background-color: #6fa8d143;
  color: #24b30e;
}

.accuracy-icon {
  background-color: #e0f7fa;
  color: #00acc1;
}

.correct-icon {
  background-color: #e8f5e9;
  color: #2e7d32;
}

.fas {
  font-size: 25px;
}

.wrong-icon {
  background-color: #ffebee;
  color: #c62828;
}

.skip-icon {
  background-color: #f5f5f5;
  color: #757575;
}

.score-icon {
  background-color: #e3f2fd;
  color: #1976d2;
}

.time-icon {
  background-color: #fff3e0;
  color: #f57c00;
}

.stat-label {
  font-size: 0.85rem;
  color: #666;
  margin-bottom: 5px;
}

.stat-value {
  font-size: 1.2rem;
  font-weight: 600;
  margin: 0;
}

.score-section {
  background-color: #f8f9fa;
  border-radius: 8px;
  padding: 15px;
  margin: 20px 0;
}

.score-card {
  text-align: center;
  padding: 15px;
  background-color: #fff;
  border-radius: 6px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.score-card h4 {
  font-size: 1.1rem;
  color: #10359c;
  margin-bottom: 10px;
}

.score-value {
  font-size: 1.4rem;
  font-weight: 600;
  margin-bottom: 7px;
}

.score-progress {
  padding: 0 15px;
}

.progress {
  height: 8px;
  margin-bottom: 5px;
}

.score-percent {
  font-size: 1rem;
  color: #666;
}

.answer-controls {
  margin: 15px 0;
}

.answer-filter {
  display: flex;
  align-items: center;
  gap: 10px;
}

.filter-input {
  padding: 0.25rem 0.5rem;
  font-size: 0.85rem;
  border: 1px solid #ced4da;
  border-radius: 4px;
  width: 200px;
  outline: none;
  background-color: #fff;
}

.filter-input:focus {
  border-color: #1877f2;
  box-shadow: 0 0 5px rgba(24, 119, 242, 0.3);
}

.alert-info {
  background-color: #e3f2fd;
  border-color: #bbdefb;
  color: #0d47a1;
  font-size: 0.9rem;
}

.fa-leaf {
  font-size: 20px;
}

.answer-grid {
  margin: 10px 0 20px;
  gap: 20px;
}

.answer-item {
  margin-left: 8px;
}

.part-answers {
  margin-bottom: 20px;
}

.part-answers h5 {
  font-weight: 600;
  margin-bottom: 0.8rem;
  margin-left: 0.3rem;
  font-size: 1rem;
}

.answer-number {
  font-weight: 600;
  margin-bottom: 5px;
  border-radius: 50%;
  background-color: #e8f2ff;
  color: #35509a;
  width: 35px;
  height: 35px;
  line-height: 35px;
  font-size: 15px;
  text-align: center;
  display: inline-block;
}

.answer-options {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  margin-left: 7px;
}

.option {
  display: inline-block;
  width: 24px;
  height: 24px;
  border-radius: 50%;
  border: 1px solid #ccc;
  text-align: center;
  line-height: 22px;
  font-size: 0.8rem;
  margin-right: 5px;
}

.selected {
  font-size: 1rem;
  border: none;
}

.correct {
  background-color: #e8f5e9;
  border-color: #4caf50;
  color: #2e7d32;
}

.answer-status {
  font-size: 0.8rem;
  margin-right: 5px;
}

.answer-status.correct {
  color: #4caf50;
}

.answer-status.wrong {
  color: #ef5350;
}

.answer-details {
  font-size: 0.8rem;
  color: #1877f2;
  text-decoration: none;
}

.answer-details:hover {
  text-decoration: underline;
}

.sidebar {
  top: 20px;
  position: sticky;
  margin-bottom: 20px;
}

.user-info {
  background-color: #fff;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: flex-start;
  gap: 10px;
  margin-bottom: 15px;
}

.avatar {
  width: 50px;
  height: 50px;
  background-color: #e0e0e0;
  border-radius: 50%;
  flex-shrink: 0;
}

.user-details {
  flex: 1;
  text-align: left;
}

.user-details h4 {
  font-size: 0.95rem;
  font-weight: 600;
  color: #333;
  margin-bottom: 10px;
}

.user-details p {
  font-size: 0.85rem;
  color: #666;
  margin: 5px 0;
}

.stats-button {
  background-color: #1877f2;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 0.8rem;
  margin-top: 10px;
  width: 100%;
}

.promo-banner,
.study-group,
.community {
  background-color: #fff;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: center;
  margin-bottom: 15px;
}

.promo-banner {
  background: linear-gradient(135deg, #7794b6, #ada6e3);
  color: white;
}

.promo-banner h3 {
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 8px;
}

.promo-banner p {
  font-size: 0.85rem;
  margin-bottom: 10px;
}

.start-button,
.extension-button,
.join-button {
  width: 100%;
  padding: 8px;
  border-radius: 4px;
  font-size: 0.9rem;
  font-weight: 600;
  border: none;
  cursor: pointer;
  transition: all 0.3s ease;
}

.start-button {
  background-color: white;
  color: #1877f2;
}

.extension-button,
.join-button {
  background-color: #1877f2;
  color: white;
}

.study-group h4,
.community h4 {
  font-size: 0.95rem;
  font-weight: 600;
  color: #333;
  margin-bottom: 8px;
}

.study-group p,
.community p {
  font-size: 0.85rem;
  color: #666;
  margin-bottom: 10px;
}

.btn-sky-custom {
  color: #1e2022;
  background-color: #e8f2ff;
  border-color: #35509a;
}

.btn-sky-custom:hover {
  color: #fff;
  background-color: #317fe6;
}

@media (min-width: 992px) {
  .left-column {
    flex: 0 0 72%;
    max-width: 74%;
  }
  .right-column {
    flex: 0 0 28%;
    max-width: 26%;
  }
}

@media (max-width: 768px) {
  .summary-card {
    flex: 1 1 calc(50% - 15px);
  }
  .summary-card.accuracy-card {
    flex: 1 1 calc(100% - 15px);
  }
  .answer-filter {
    flex-direction: column;
    align-items: flex-start;
    gap: 5px;
  }
  .filter-input {
    width: 100%;
  }
}

/* Modal styles */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: transparent;
  border: none;
  max-width: 600px;
  width: 90%;
}
</style>
