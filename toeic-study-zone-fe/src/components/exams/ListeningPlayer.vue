<template>
  <div class="listening-player">
    <audio ref="audioPlayer" controls @play="onPlay" @pause="onPause">
      <source :src="audioSource" type="audio/mpeg" />
      Trình duyệt của bạn không hỗ trợ phát audio.
    </audio>
  </div>
</template>

<script>
export default {
  props: {
    audioSource: String,
  },
  methods: {
    onPlay() {
      // Dừng tất cả các audio khác
      const allAudio = document.querySelectorAll("audio");
      allAudio.forEach((audio) => {
        if (audio !== this.$refs.audioPlayer) {
          audio.pause();
          audio.currentTime = 0;
        }
      });
      this.$emit("play");
    },
    onPause() {
      this.$emit("pause");
    },
    play() {
      this.$refs.audioPlayer.play();
    },
    pause() {
      this.$refs.audioPlayer.pause();
    },
  },
};
</script>

<style scoped>
.listening-player {
  margin-bottom: 20px;
}

.listening-player audio {
  width: 100%;
  max-width: 500px;
}
</style>
