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
            <img
              v-if="comment.avatarUrl"
              :src="comment.avatarUrl"
              alt="avatar"
              class="avatar-img"
            />
            <div v-else class="avatar">
              {{ comment.userName?.charAt(0).toUpperCase() }}
            </div>
          </div>

          <div class="comment-content">
            <div class="comment-header">
              <span class="username">{{ comment.userName }}</span>
              <span class="date">· {{ formatDate(comment.createdAt) }}</span>
            </div>
            <div class="comment-text">{{ comment.commentText }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, toRefs } from "vue";
import commentService from "@/services/commentService";

const props = defineProps({
  testId: Number,
  user: Object,
});

const { testId, user } = toRefs(props);

const newComment = ref("");
const comments = ref([]);

const loadComments = async () => {
  try {
    const res = await commentService.getCommentsByTestId(testId.value);
    comments.value = res.data;
  } catch (err) {
    console.error("Lỗi khi tải bình luận:", err);
  }
};

const submitComment = async () => {
  if (!newComment.value.trim()) return;

  try {
    const res = await commentService.postComment({
      userId: user.value.id,
      testId: testId.value,
      commentText: newComment.value,
      parentId: null,
    });

    comments.value.unshift(res.data);
    await loadComments();
    newComment.value = "";
  } catch (err) {
    console.error("Lỗi khi gửi bình luận:", err);
    alert("Gửi bình luận thất bại!");
  }
};

const formatDate = (iso) => {
  return new Date(iso).toLocaleDateString("vi-VN", {
    day: "numeric",
    month: "long",
    year: "numeric",
  });
};

onMounted(() => {
  loadComments();
});
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
  width: 35px;
  height: 35px;
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

.avatar-img {
  width: 35px;
  height: 35px;
  border-radius: 50%;
  object-fit: cover;
}

</style>
