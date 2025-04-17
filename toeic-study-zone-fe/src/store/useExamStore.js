import { defineStore } from "pinia";

export const useExamStore = defineStore("exam", {
  state: () => ({
    questions: [],
    answers: {},
  }),
  actions: {
    setQuestions(questions) {
      this.questions = questions;
    },
    setAnswer(questionId, option) {
      this.answers[questionId] = option;
    },
  },
});
