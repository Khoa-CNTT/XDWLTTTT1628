<template>
  <div class="testhome-container">
    <!-- Phần chính -->
    <div class="main-content">
      <!-- Phần lọc đề thi -->
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

        <!-- Phần lọc đề thi -->
        <div class="filter-section">
          <!-- Thẻ năm (Buttons) - Chỉ hiển thị khi activeTab không phải là "Tất cả" -->
          <div
            class="filter-tags-container"
            style="margin-bottom: 15px"
            v-if="activeTab !== 'Tất cả'"
          >
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

          <!-- Search Input -->
          <div class="search-input-container" style="margin-bottom: 10px">
            <input
              type="text"
              v-model="searchQuery"
              placeholder="Nhập từ khóa bạn muốn tìm kiếm: tên bài thi, số năm bài thi ..."
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
                  d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"
                />
              </svg>
            </span>
          </div>

          <!-- Search Button -->
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
                <span class="icon">⏰</span> {{ test.questions }} |
                <span class="icon">📝</span> {{ test.participants }}
              </p>
              <p class="test-meta test-comments">
                <span class="icon">💬</span> {{ test.comments }}
              </p>
              <p class="test-description">2 phần thi | 200 câu hỏi</p>
              <button class="btn btn-outline-primary">Chi tiết</button>
            </div>
          </div>
        </div>

        <!-- Phân trang -->
        <div class="pagination">
          <button
            @click="currentPage = currentPage - 1"
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
            @click="currentPage = currentPage + 1"
            :disabled="currentPage === totalPages"
            class="next-button"
          >
            <span class="icon">▶</span>
          </button>
        </div>
      </div>
    </div>

    <!-- Phần bên phải (Sidebar) -->
    <div class="sidebar">
      <!-- Thông tin người dùng -->
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

      <!-- Banner quảng cáo -->
      <div class="promo-banner">
        <h3>Kiểm tra trình độ miễn phí</h3>
        <p>Nhanh chóng - Chính xác - Hiệu quả</p>
        <button class="start-button">START</button>
      </div>

      <!-- Nhóm học tập -->
      <div class="study-group">
        <h4>Trao đổi, học tập, Flashcards</h4>
        <p>StudyZone Extension</p>
        <button class="extension-button">Cài đặt ngay</button>
      </div>

      <!-- Cộng đồng -->
      <div class="community">
        <h4>Cộng đồng luyện thi TOEIC</h4>
        <p>Tham gia nhóm Facebook StudyZone</p>
        <button class="join-button">Tham gia ngay</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Testhome",
  data() {
    return {
      activeTab: "Tất cả",
      activeFilter: "2024",
      currentPage: 1,
      testsPerPage: 12,
      searchQuery: "", // Lưu giá trị nhập liệu tạm thời
      searchTerm: "", // Lưu từ khóa tìm kiếm thực sự
      tabs: ["Tất cả", "TOEIC"],
      filterTags: ["2024", "2023", "2022", "2021", "2020", "2019", "2018"],
      tests: [
        {
          title: "2024 Practice Set TOEIC Test 1",
          questions: "120 phút",
          participants: "1,024 người thi",
          comments: "636 bình luận",
        },
        {
          title: "2024 Practice Set TOEIC Test 2",
          questions: "120 phút",
          participants: "1,729 người thi",
          comments: "236 bình luận",
        },
        {
          title: "2024 Practice Set TOEIC Test 3",
          questions: "120 phút",
          participants: "1,491 người thi",
          comments: "636 bình luận",
        },
        {
          title: "2024 Practice Set TOEIC Test 4",
          questions: "120 phút",
          participants: "36,444 người thi",
          comments: "236 bình luận",
        },
        {
          title: "2024 Practice Set TOEIC Test 5",
          questions: "120 phút",
          participants: "12,897 người thi",
          comments: "636 bình luận",
        },
        {
          title: "2024 Practice Set TOEIC Test 6",
          questions: "120 phút",
          participants: "10,200 người thi",
          comments: "236 bình luận",
        },
        {
          title: "2024 Practice Set TOEIC Test 7",
          questions: "120 phút",
          participants: "2,191 người thi",
          comments: "636 bình luận",
        },
        {
          title: "2024 Practice Set TOEIC Test 8",
          questions: "120 phút",
          participants: "2,309 người thi",
          comments: "236 bình luận",
        },
        {
          title: "2024 Practice Set TOEIC Test 9",
          questions: "120 phút",
          participants: "1,286 người thi",
          comments: "636 bình luận",
        },
        {
          title: "2024 Practice Set TOEIC Test 10",
          questions: "120 phút",
          participants: "3,936 người thi",
          comments: "236 bình luận",
        },
        {
          title: "2024 Practice Set TOEIC Test 11",
          questions: "120 phút",
          participants: "3,771 người thi",
          comments: "636 bình luận",
        },
        {
          title: "2024 Practice Set TOEIC Test 12",
          questions: "120 phút",
          participants: "10,812 người thi",
          comments: "236 bình luận",
        },
        {
          title: "2024 Practice Set TOEIC Test 13",
          questions: "120 phút",
          participants: "2,286 người thi",
          comments: "636 bình luận",
        },
        {
          title: "2024 Practice Set TOEIC Test 14",
          questions: "120 phút",
          participants: "4,590 người thi",
          comments: "236 bình luận",
        },
        {
          title: "2024 Practice Set TOEIC Test 15",
          questions: "120 phút",
          participants: "5,451 người thi",
          comments: "636 bình luận",
        },
        {
          title: "2024 Practice Set TOEIC Test 16",
          questions: "120 phút",
          participants: "104,482 người thi",
          comments: "236 bình luận",
        },
        {
          title: "2024 Practice Set TOEIC Test 17",
          questions: "120 phút",
          participants: "48 người thi",
          comments: "15 bình luận",
        },
        {
          title: "2024 Practice Set TOEIC Test 18",
          questions: "120 phút",
          participants: "15 người thi",
          comments: "5 bình luận",
        },
        {
          title: "2024 Practice Set TOEIC Test 19",
          questions: "120 phút",
          participants: "2,222 người thi",
          comments: "10 bình luận",
        },
      ],
    };
  },
  computed: {
    filteredTests() {
      let filtered = this.tests;

      // Lọc theo activeTab
      if (this.activeTab === "TOEIC") {
        filtered = filtered.filter((test) =>
          test.title.toLowerCase().includes("toeic")
        );
      }

      // Lọc theo activeFilter (nếu activeTab không phải "Tất cả")
      if (this.activeTab !== "Tất cả") {
        filtered = filtered.filter((test) =>
          test.title.includes(this.activeFilter)
        );
      }

      // Lọc theo searchTerm (nếu có)
      if (this.searchTerm) {
        filtered = filtered.filter((test) =>
          test.title.toLowerCase().includes(this.searchTerm.toLowerCase())
        );
      }

      return filtered;
    },
    totalPages() {
      // Tính tổng số trang dựa trên danh sách đã lọc
      return Math.ceil(this.filteredTests.length / this.testsPerPage);
    },
    displayedTests() {
      // Phân trang dựa trên danh sách đã lọc
      const start = (this.currentPage - 1) * this.testsPerPage;
      const end = start + this.testsPerPage;
      return this.filteredTests.slice(start, end);
    },
  },

  methods: {
    performSearch() {
      // Gán giá trị từ searchQuery sang searchTerm để kích hoạt lọc
      this.searchTerm = this.searchQuery;
      // Đặt lại trang về 1 khi tìm kiếm để hiển thị kết quả từ đầu
      this.currentPage = 1;
    },
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
