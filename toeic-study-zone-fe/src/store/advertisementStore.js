// src/store/advertisement.js
import { defineStore } from 'pinia';
import advertisementService from '@/services/advertisementService';

export const useAdvertisementStore = defineStore('advertisement', {
  state: () => ({
    advertisements: [],
    activeAdvertisements: [],
  }),
  actions: {
    async fetchAll() {
      const res = await advertisementService.getAll();
      this.advertisements = res.data;
    },
    async fetchActive() {
      const res = await advertisementService.getActive();
      this.activeAdvertisements = res.data;
    },
    async create(ad) {
      await advertisementService.create(ad);
      await this.fetchAll();
    },
    async delete(id) {
      await advertisementService.delete(id);
      await this.fetchAll();
    },
  },
});
