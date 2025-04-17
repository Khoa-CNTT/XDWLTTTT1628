<template>
  <div class="comments">
    <!-- Comment Input -->
    <div class="comment-input mb-4">
      <textarea
        class="form-control"
        rows="3"
        placeholder="Chia sẻ cảm nghĩ của bạn..."
        v-model="newComment"
      ></textarea>
      <button class="btn btn-primary mt-2" @click="submitComment">Gửi</button>
    </div>

    <!-- Comment List -->
    <div class="comment-list">
      <div
        class="comment-item"
        v-for="(comment, index) in comments"
        :key="index"
      >
        <div class="d-flex">
          <div class="avatar-wrapper me-2">
            <div class="avatar">{{ comment.initial }}</div>
          </div>
          <div class="comment-content">
            <div class="comment-header">
              <span class="username">{{ comment.author }}</span>
              <span class="date">· {{ comment.date }}</span>
            </div>
            <div class="comment-text">{{ comment.content }}</div>
            <a href="#" class="reply-link">Trả lời</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";

export default {
  name: "Comments",
  setup() {
    const newComment = ref("");
    const comments = ref([
      {
        author: "22002845",
        date: "Tháng tư 05, 2023",
        content:
          "Cho mình hỏi lần đầu thi thì nên luyện trên đề ETS hay này rất khó",
        initial: "2",
      },
      {
        author: "has9294",
        date: "Tháng tư 02, 2023",
        content: "old format khó hơn phải không?",
        initial: "H",
      },
      {
        author: "study4",
        date: "Tháng tư 02, 2023",
        content: "old format là các bạc học thi có thời",
        initial: "S",
      },
      {
        author: "47vs.snow",
        date: "Tháng 8, 21, 2024",
        content: "Đây là đề cũ 2024 pk a",
        initial: "4",
      },
      {
        author: "PhuctheHero8c391074ef1ca_38t323",
        date: "Tháng 8, 15, 2024",
        content: "e vừa thử không vào máy bộ để 20/1? sau hết rồi",
        initial: "P",
      },
      {
        author: "vietnaodi123",
        date: "Tháng 8, 04, 2024",
        content: "cày từ sáng tới tối phết",
        initial: "V",
      },
      {
        author: "vietnaodi123",
        date: "Tháng 8, 04, 2024",
        content: "lại là t-chu minh nhân",
        initial: "V",
      },
      {
        author: "ThanhDiemn_15625",
        date: "Tháng 8, 04, 2024",
        content: "format mới có giống như đề thi toeic ko a",
        initial: "T",
      },
      {
        author: "lehieu24nguyen",
        date: "Tháng 8, 02, 2024",
        content: "format cũ khó hơn đề mới?",
        initial: "L",
      },
      {
        author: "hau9024",
        date: "Tháng 8, 02, 2024",
        content:
          "Mô hình thây của mình ngày phải du vưỡng khó hơn format mới nhiều.",
        initial: "H",
      },
      {
        author: "tarhoa.tran.5",
        date: "Tháng 10, 19, 2023",
        content: "có 2 người phụ nữ trông rất giống “the hat” nhỉ?",
        initial: "T",
      },
      {
        author: "nqaha.duck",
        date: "Tháng 10, 19, 2023",
        content: "hay là chị người phụ nữ thay nhật mức gì đó nhỉ",
        initial: "N",
      },
      {
        author: "phamthitu0219960",
        date: "Tháng bảy 25, 2024",
        content: "4 là cái 9 mà",
        initial: "P",
      },
    ]);

    const submitComment = () => {
      if (newComment.value.trim()) {
        comments.value.unshift({
          author: "Current User",
          date: new Date().toLocaleDateString("vi-VN", {
            month: "long",
            day: "numeric",
            year: "numeric",
          }),
          content: newComment.value,
          initial: "C",
        });
        newComment.value = "";
      }
    };

    return {
      newComment,
      comments,
      submitComment,
    };
  },
};
</script>

<style scoped>
.comment-input textarea {
  border-radius: 6px;
  resize: none;
  font-size: 0.9rem;
  padding: 0.75rem;
  border-color: #e0e0e0;
}

.comment-input button {
  font-size: 0.9rem;
  padding: 0.35rem 1.25rem;
}

.comment-item {
  margin-bottom: 1rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid #e0e0e0;
}

.avatar-wrapper {
  flex-shrink: 0;
  margin-top: 4px;
}

.avatar {
  width: 26px;
  height: 26px;
  background-color: #e9e9e9;
  color: #555;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.75rem;
  font-weight: 600;
}

.comment-content {
  flex: 1;
  text-align: left;
}

.comment-header {
  display: flex;
  align-items: center;
  margin-bottom: 0.25rem;
}

.username {
  font-weight: 600;
  font-size: 0.85rem;
  color: #333;
}

.date {
  font-size: 0.75rem;
  color: #777;
  margin-left: 0.25rem;
}

.comment-text {
  font-size: 0.85rem;
  margin-bottom: 0.35rem;
  color: #333;
  line-height: 1.4;
}

.reply-link {
  font-size: 0.75rem;
  color: #1877f2;
  text-decoration: none;
}

.reply-link:hover {
  text-decoration: underline;
}
</style>
