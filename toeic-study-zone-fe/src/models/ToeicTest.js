export default class ToeicTest {
  constructor(data = {}) {
    this.id = data.id || null;
    this.title = data.title || "";
    this.year_id = data.year_id || null; // Chỉ dùng year_id, bỏ data.year để tránh nhầm lẫn
    this.description = data.description || "";
    this.total_questions = data.totalQuestions || 0;
    this.time_limit = data.timeLimit || null;
    this.is_free = data.isFree || false;
    this.is_published = data.isPublished || false;
    this.is_placement_test = data.isPlacementTest || false;
    this.created_at = data.createdAt || new Date().toISOString();
    this.updated_at = data.updatedAt || null;
  }
}