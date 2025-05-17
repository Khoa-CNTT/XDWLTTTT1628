<template>
  <div class="testhome-container">
    <!-- Main content -->
    <div class="main-content">
      <div class="filter-section">
        <h1 class="page-title">Thư viện đề thi</h1>
        <div class="tabs">
          <button
            v-for="tab in tabs"
            :key="tab"
            :class="{ active: activeTab === tab }"
            @click="activeTab = tab"
            class="tab-button"
          >
            {{ tab }}
          </button>
        </div>

        <div class="filter-section">
          <div class="filter-tags-container" v-if="activeTab !== 'Tất cả'">
            <button
              v-for="tag in filterTags"
              :key="tag"
              :class="{ active: activeFilter === tag }"
              @click="activeFilter = tag"
              class="filter-tag"
            >
              {{ tag }}
            </button>
          </div>

          <div class="search-input-container">
            <input
              type="text"
              v-model="searchQuery"
              placeholder="Nhập từ khóa: tên bài thi, năm..."
              class="search-input-main"
            />
            <span class="search-icon-main">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
                fill="currentColor"
                viewBox="0 0 16 16"
              >
                <path
                  d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85z"
                />
              </svg>
            </span>
          </div>
          <button class="search-submit-button" @click="performSearch">
            Tìm kiếm
          </button>
        </div>
      </div>

      <!-- Danh sách đề thi -->
      <div class="tests-section">
        <div class="row">
          <div
            class="col-md-3"
            v-for="(test, index) in displayedTests"
            :key="'test-' + index"
          >
            <div class="test-card">
              <h4 class="test-title">{{ test.title }}</h4>
              <p class="test-meta">
                <span class="icon">⏰</span> {{ test.timeLimit }} phút |
                <span class="icon">📝</span> {{ test.participants }}
              </p>
              <p class="test-meta test-comments">
                <span class="icon">💬</span> {{ test.comments }}
              </p>
              <p class="test-description">
                {{ test.totalQuestions }} câu hỏi | {{ test.description }}
              </p>
              <button
                class="btn btn-outline-primary"
                @click="goToTestDetail(test.id)"
              >
                Chi tiết
              </button>
            </div>
          </div>
        </div>

        <!-- Phân trang -->
        <div class="pagination">
          <button
            @click="currentPage--"
            :disabled="currentPage === 1"
            class="prev-button"
          >
            <span class="icon">◀</span>
          </button>
          <button
            v-for="page in totalPages"
            :key="page"
            :class="{ active: currentPage === page }"
            @click="currentPage = page"
            class="page-button"
          >
            {{ page }}
          </button>
          <button
            @click="currentPage++"
            :disabled="currentPage === totalPages"
            class="next-button"
          >
            <span class="icon">▶</span>
          </button>
        </div>
      </div>
    </div>

    <!-- Sidebar Thông tin người dùng-->
    <div class="sidebar">
      <div class="user-info">
        <div class="avatar">
          <img
            v-if="user.avatar"
            :src="user.avatar"
            alt="avatar"
            style="
              width: 50px;
              height: 50px;
              border-radius: 50%;
              object-fit: cover;
            "
          />
          <div
            v-else
            style="
              width: 50px;
              height: 50px;
              background: #ccc;
              border-radius: 50%;
            "
          ></div>
        </div>
        <div class="user-details">
          <h4>{{ user.username || "Chưa có tên" }}</h4>
          <p>Ngày dự thi: {{ formatDate(user.targetDate) }}</p>
          <p>Ngành dự thi: {{ user.examType || "Chưa có" }}</p>
          <p>Điểm mục tiêu: {{ user.targetScore || "Chưa có" }}</p>
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
</template>

<script>
import examService from "@/services/examService";
import userService from "@/services/userService";

export default {
  name: "Testhome",
  data() {
    return {
      activeTab: "Tất cả",
      activeFilter: "2024",
      currentPage: 1,
      testsPerPage: 12,
      searchQuery: "",
      searchTerm: "",
      tabs: ["Tất cả", "TOEIC"],
      filterTags: ["2024", "2023", "2022", "2021", "2020", "2019", "2018"],
      tests: [],
      user: {
        username: "",
        avatar: null,
        targetDate: null,
        targetScore: null,
        examType: null,
      },
    };
  },
  computed: {
    filteredTests() {
      let filtered = this.tests;

      if (this.activeTab === "TOEIC") {
        filtered = filtered.filter((test) =>
          test.title.toLowerCase().includes("toeic")
        );
      }

      if (this.activeTab !== "Tất cả") {
        filtered = filtered.filter((test) =>
          String(test.yearId).includes(this.activeFilter)
        );
      }

      if (this.searchTerm) {
        filtered = filtered.filter((test) =>
          test.title.toLowerCase().includes(this.searchTerm.toLowerCase())
        );
      }

      return filtered;
    },
    totalPages() {
      return Math.ceil(this.filteredTests.length / this.testsPerPage);
    },
    displayedTests() {
      const start = (this.currentPage - 1) * this.testsPerPage;
      return this.filteredTests.slice(start, start + this.testsPerPage);
    },
  },
  methods: {
    performSearch() {
      this.searchTerm = this.searchQuery;
      this.currentPage = 1;
    },

    async loadAllTests() {
      try {
        const res = await examService.getAllTests();
        this.tests = res.data.map((test) => ({
          id: test.id,
          title: test.title,
          yearId: test.yearId,
          timeLimit: test.timeLimit || 0,
          totalQuestions: test.totalQuestions || 0,
          description: test.description || "",
          participants: `${(
            test.participantsCount || 0
          ).toLocaleString()} người thi`,
          comments: `${(test.commentsCount || 0).toLocaleString()} bình luận`,
        }));
      } catch (error) {
        console.error("Lỗi khi tải danh sách đề thi:", error);
      }
    },

    async loadUserInfo() {
      try {
        const res = await userService.getCurrentUser();
        const userId = res.data.id;

        this.user.username = res.data.username;
        this.user.avatar = res.data.avatarUrl || null;

        const goalRes = await userService.getLearningGoals(userId);
        if (goalRes.data.length > 0) {
          const goal = goalRes.data[0];
          this.user.targetDate = goal.targetDate;
          this.user.targetScore = goal.targetScore;
          this.user.examType = goal.examType || "TOEIC";
        }
      } catch (error) {
        console.error("Lỗi khi tải thông tin người dùng:", error);
      }
    },

    formatDate(dateStr) {
      if (!dateStr) return "Chưa có";
      return new Date(dateStr).toLocaleDateString("vi-VN");
    },

    goToTestDetail(testId) {
      this.$router.push({ name: "Test", params: { id: testId } });
    },
  },
  async mounted() {
    await this.loadAllTests();
    await this.loadUserInfo();
  },
};
</script>

<style scoped>
.filter-tags-container {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

/* Form chứa search input */
.search-input-container {
  position: relative;
  width: 100%;
  margin-top: 10px;
}

/* Input search */
.search-input-main {
  width: 100%;
  padding: 10px 40px 10px 15px;
  border: 1px solid #ced4da;
  border-radius: 6px;
  font-size: 14px;
  box-sizing: border-box;
  background-color: white;
  color: #000;
  transition: border-color 0.3s ease;
}

.search-input-main:focus {
  outline: none;
  border-color: #1877f2;
  box-shadow: 0 0 0 2px rgba(24, 119, 242, 0.2);
}

.search-input-main::placeholder {
  color: #6c757d;
  opacity: 1;
}

/* Search Icon */
.search-icon-main {
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  color: #495057;
  pointer-events: none;
  display: flex;
  align-items: center;
}

.search-icon-main svg {
  display: block;
}

/* Search Submit Button */
.search-submit-button {
  background-color: #4267b2;
  color: white;
  border: none;
  padding: 10px 20px;
  margin-top: 10px;
  border-radius: 6px;
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.search-submit-button:hover {
  background-color: #062868;
}

.testhome-container {
  max-width: 1400px;
  margin: 2.6rem auto;
  padding: 15px;
  font-family: "Inter", sans-serif;
  display: flex;
  gap: 15px;
}

/* Phần chính */
.main-content {
  flex: 4;
}

/* Phần bên phải (Sidebar) */
.sidebar {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

/* Tiêu đề trang */
.page-title {
  font-size: 1.8rem;
  font-weight: 600;
  color: #1c2526;
  margin-bottom: 20px;
  text-align: left;
}

/* Phần lọc đề thi */
.filter-section {
  margin-bottom: 20px;
}

.tabs {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 10px;
  overflow-x: auto;
  white-space: nowrap;
}

.tab-button {
  background-color: #f1f3f5;
  border: none;
  padding: 6px 12px;
  border-radius: 7px;
  border: 1px solid #e0e0e0;
  font-size: 13px;
  color: #606770;
  cursor: pointer;
  transition: all 0.3s ease;
}

.tab-button.active,
.tab-button:hover {
  background-color: #1877f2;
  color: white;
  border-color: #0a121c;
}

.filter-options {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 15px;
  align-items: center;
  justify-content: space-between;
}

.filter-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  flex-grow: 1;
  max-width: calc(100% - 260px); /* Dành chỗ cho ô search */
}

.filter-tag {
  background: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 20px;
  padding: 6px 12px;
  font-size: 13px;
  color: #24282be7;
  cursor: pointer;
  transition: all 0.2s;
  white-space: nowrap;
}

.filter-tag.active {
  background: #222527c6;
  color: white;
  border-color: #0a121c;
  box-shadow: 0 2px 4px rgba(3, 119, 235, 0.2);
}

.search-container {
  width: 250px;
  flex-shrink: 0;
}

.search-input::placeholder {
  color: #6c757d;
  opacity: 0.8;
}

/* Danh sách đề thi */
.tests-section {
  margin-bottom: 20px;
}

.test-card {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 15px;
  text-align: left;
  margin-bottom: 15px;
  transition: all 0.3s ease;
  /* gap: 0.18rem; */
  min-width: 250px;
}

.test-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.test-title {
  font-size: 1rem;
  font-weight: 600;
  color: #1c2526;
  margin-bottom: 15px;
}

.test-meta {
  display: flex;
  align-items: center;
  font-size: 0.8rem;
  color: #5d5a5a;
  font-weight: 500;
  line-height: 1.5;
  margin-bottom: 5px;
}

.test-meta .icon {
  font-size: 0.8rem;
  margin-right: 4px;
}

.test-comments {
  display: flex;
  align-items: center;
  font-size: 0.8rem;
  color: #5d5a5a;
  font-weight: 500;
  line-height: 1.5;
  margin-bottom: 1rem;
}

.test-comments .icon {
  font-size: 0.8rem;
  margin-right: 4px;
}

.test-description {
  font-size: 0.9rem;
  color: #030303;
  margin-bottom: 1rem;
}

.btn-outline-primary {
  width: 100%;
  border-color: #1877f2;
  color: #1877f2;
  padding: 6px;
  border-radius: 4px;
  font-size: 13px;
  transition: all 0.3s ease;
}

.btn-outline-primary:hover {
  background-color: #1877f2;
  color: #fff;
}

/* Phân trang */
.pagination {
  display: flex;
  justify-content: center;
  gap: 5px;
  margin-top: 15px;
}

.page-button,
.prev-button,
.next-button {
  background-color: #f1f3f5;
  border: none;
  padding: 6px 10px;
  border-radius: 4px;
  font-size: 13px;
  color: #606770;
  cursor: pointer;
  transition: all 0.3s ease;
}

.page-button.active,
.page-button:hover,
.prev-button:hover,
.next-button:hover {
  background-color: #1877f2;
  color: white;
}

.prev-button:disabled,
.next-button:disabled {
  background-color: #e0e0e0;
  cursor: not-allowed;
}

/* Sidebar: Thông tin người dùng */
.user-info {
  background-color: #fff;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: flex-start;
  gap: 10px;
  margin-bottom: 40px;
}

.avatar {
  width: 50px;
  height: 50px;
  background-color: #e0e0e0;
  border-radius: 50%;
  flex-shrink: 0;
  margin-top: 10px;
}

.user-details {
  flex: 1;
  padding: 10px 0;
}

.user-details h4 {
  font-size: 15px;
  font-weight: 600;
  color: #1c2526;
  margin-bottom: 12px;
}

.user-details p {
  font-size: 14px;
  color: #606770;
  margin: 6px 0;
  text-align: left;
}

.stats-button {
  background-color: #1877f2;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 13px;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 3px;
  margin-top: 20px;
  transition: all 0.3s ease;
}

.stats-button:hover {
  background-color: #145dbf;
}

/* Sidebar: Banner quảng cáo */
.promo-banner {
  background: linear-gradient(135deg, #7794b6, #ada6e3);
  border-radius: 8px;
  padding: 15px;
  text-align: center;
  color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.promo-banner h3 {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 8px;
}

.promo-banner p {
  font-size: 13px;
  margin-bottom: 10px;
}

.start-button {
  background-color: white;
  color: #3681dd;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  font-weight: 600;
  font-size: 13px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.start-button:hover {
  background-color: #4fd36b;
  color: white;
  box-shadow: 0 0 10px rgba(40, 167, 69, 0.8);
}

/* Sidebar: Nhóm học tập */
.study-group {
  background-color: #fff;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.study-group h4 {
  font-size: 14px;
  font-weight: 600;
  color: #1c2526;
  margin-bottom: 8px;
}

.study-group p {
  font-size: 12px;
  color: #606770;
  margin-bottom: 10px;
}

.extension-button {
  background-color: #1877f2;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 13px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.extension-button:hover {
  background-color: #145dbf;
}

/* Sidebar: Cộng đồng */
.community {
  background-color: #fff;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.community h4 {
  font-size: 14px;
  font-weight: 600;
  color: #1c2526;
  margin-bottom: 8px;
}

.community p {
  font-size: 12px;
  color: #606770;
  margin-bottom: 10px;
}

.join-button {
  background-color: #1877f2;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 13px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.join-button:hover {
  background-color: #145dbf;
}

/* Responsive */
@media (max-width: 1024px) {
  .testhome-container {
    flex-direction: column;
  }

  .sidebar {
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: space-between;
  }

  .user-info,
  .promo-banner,
  .study-group,
  .community {
    flex: 1 1 45%;
  }
}

@media (max-width: 768px) {
  .sidebar {
    flex-direction: column;
  }

  .user-info,
  .promo-banner,
  .study-group,
  .community {
    flex: 1 1 100%;
  }
}
</style>
