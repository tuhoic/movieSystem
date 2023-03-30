<template>
  <top-bar></top-bar>
  <div class="user-profile">
    <div class="user-avatar">
      <img :src="`/api/${user.headPortrait}`" alt="avatar">
      <input type="file" accept="image/*" ref="avatarInput" @change="uploadAvatar">
    </div>
    <div class="user-info">
      <div class="user-name">{{ user.username }}</div>
      <div class="user-email">{{ user.email }}</div>
    </div>
  </div>
</template>

<script>

import TopBar from "@/components/Header.vue";
import axios from "axios";
import {mapGetters} from "vuex";

export default {
  name: "ProfilePage",
  components: {TopBar},

  computed: {
    ...mapGetters('auth', ["user"])
  },
  methods: {
    async uploadAvatar(event) {
      const file = event.target.files[0];
      const formData = new FormData();
      formData.append('avatar', file);
      try {
        const response = await axios.post('/api/user/avatar', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        this.avatarUrl = response.data.avatarUrl;
      } catch (error) {
        console.error(error);
      }
    }
  }
}
</script>

<style scoped>
.user-profile {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 20px;
  padding: 20px;
  border: 1px solid #ccc;
}

.user-avatar {
  position: relative;
  width: 120px;
  height: 120px;
  margin-right: 20px;
  overflow: hidden;
}

.user-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.user-avatar input[type=file] {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  cursor: pointer;
}

.user-info {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.user-name {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
}

.user-email {
  font-size: 16px;
  color: #666;
}
</style>
