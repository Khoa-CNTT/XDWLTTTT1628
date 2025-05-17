import api from "./api";

const commentService = {
  postComment(commentData) {
    // commentData = { userId, testId, commentText, parentId }
    return api.post("/api/comments", commentData);
  },

  getCommentsByTestId(testId) {
    return api.get(`/api/comments/test/${testId}`);
  },
};

export default commentService;
