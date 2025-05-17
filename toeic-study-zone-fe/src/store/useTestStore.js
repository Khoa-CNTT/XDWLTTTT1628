import { defineStore } from "pinia";
import adminService from "../services/adminService";

export const useTestStore = defineStore("test", {
  state: () => ({
    tests: [],
    loading: false,
    error: null,
  }),
  actions: {
    async fetchTests() {
      this.loading = true;
      try {
        const response = await adminService.getTests();
        this.tests = response.data.map((test) => ({
          ...test,
          year_id: test.year,
        }));
        this.error = null;
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    },
    async addTest(testData) {
      this.loading = true;
      try {
        const response = await adminService.createTest(testData);
        this.tests.push({ ...response.data, year_id: testData.year_id });
        this.error = null;
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    },
    async updateTest(testData) {
      this.loading = true;
      try {
        const response = await adminService.updateTest(testData.id, testData);
        this.tests = this.tests.map((test) =>
          test.id === testData.id
            ? { ...response.data, year_id: testData.year_id }
            : test
        );
        this.error = null;
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    },
    async deleteTest(testId) {
      this.loading = true;
      try {
        await adminService.deleteTest(testId);
        this.tests = this.tests.filter((test) => test.id !== testId);
        this.error = null;
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    },
  },
});
