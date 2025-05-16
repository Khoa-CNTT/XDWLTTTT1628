<template>
  <div v-if="show && ad" class="modal-overlay">
    <div class="modal-container">
      <button class="close-button" @click="closeModal">×</button>
      <img :src="ad.imageUrl" alt="Ad Banner" class="modal-image" />
      <h2 class="modal-title">{{ ad.title }}</h2>
      <p class="modal-description">{{ ad.description }}</p>
      <div class="modal-buttons">
        <button class="btn btn-primary" @click="viewDetail(ad.linkUrl)">Xem chi tiết</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useAdvertisementStore } from '@/store/advertisementStore';

// Biến show để điều khiển hiển thị modal
const show = ref(true);

// Lấy store quảng cáo
const store = useAdvertisementStore();

const ad = ref(null);

onMounted(async () => {
  try {
    await store.fetchActive();
    if (store.activeAdvertisements.length > 0) {
      ad.value = store.activeAdvertisements[0]; 
    } else {
      show.value = false; l
    }
  } catch (error) {
    console.error('Lỗi khi tải quảng cáo:', error);
    show.value = false;
  }
});

function closeModal() {
  show.value = false;
}

function viewDetail(url) {
  if (url) {
    window.open(url, '_blank');
  }
}
</script>


<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.4);
  z-index: 9999;
  display: flex;
  align-items: center;
  justify-content: center;
}
.modal-container {
  position: relative;
  background-color: white;
  padding: 30px;
  border-radius: 16px;
  width: 90%;
  max-width: 480px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.25);
  text-align: center;
}
.close-button {
  position: absolute;
  top: 10px;
  right: 14px;
  background: transparent;
  border: none;
  font-size: 22px;
  font-weight: bold;
  color: #666;
  cursor: pointer;
}
.modal-image {
  max-width: 80px;
  margin-bottom: 10px;
}
.modal-title {
  font-size: 22px;
  font-weight: bold;
  margin-bottom: 8px;
  color: #1c1e21;
}
.modal-description {
  font-size: 15px;
  margin-bottom: 20px;
  color: #444;
}
.modal-buttons {
  display: flex;
  justify-content: center;
}
.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 999px;
  font-weight: bold;
  cursor: pointer;
  min-width: 200px;
}
.btn-primary {
  background-color: #1877f2;
  color: white;
  font-size: 15px;
}
</style>