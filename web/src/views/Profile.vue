<template>
  <div class="profile-page">
    <top-bar></top-bar>
    <div class="container">
      <h1 class="title">个人信息</h1>
      <div class="user-profile">
        <div class="user-avatar">
          <img :src="avatarUrl" alt="avatar">
          <input type="file" accept="image/*" ref="avatarInput" @change="uploadAvatar">
        </div>
        <div class="user-info">
          <div class="form-group">
            <label for="username">用户名</label>
            <input type="text" id="username" v-model="user.username" disabled>
          </div>
          <div class="form-group">
            <label for="email">邮箱</label>
            <input type="email" id="email" v-model="user.email" disabled>
          </div>
          <div class="form-group">
            <label for="password">密码</label>
            <input type="password" id="password" v-model="password">
          </div>
          <div class="form-group">
            <label for="confirm-password">确认密码</label>
            <input type="password" id="confirm-password" v-model="confirmPassword">
          </div>
          <button class="btn btn-primary" @click="updateProfile" :disabled="isUpdating">保存</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";
import TopBar from "@/components/Header.vue";

export default {
  name: "ProfilePage",
  components: {TopBar},
  data() {
    return {
      avatarUrl: "",
      password: "",
      confirmPassword: "",
      isUpdating: false,
      errorMessage: null,
    };
  },
  computed: {
    ...mapGetters("auth", ["user"]),
  },
  methods: {
    async uploadAvatar(event) {
      const file = event.target.files[0];
      const formData = new FormData();
      formData.append("avatar", file);
      try {
        const response = await axios.post("/api/user/avatar", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });
        this.avatarUrl = response.data.avatarUrl;
      } catch (error) {
        console.error(error);
      }
    },
    async updateProfile() {
      if (this.password !== this.confirmPassword) {
        this.errorMessage = "密码不一致，请重新输入。";
        return;
      }
      this.isUpdating = true;
      try {
        const response = await axios.patch(
            "/api/user",
            {
              password: this.password,
            },
            {
              headers: {
                Authorization: `Bearer ${this.$store.state.auth.accessToken}`,
              },
            }
        );
        if (response.status === 200) {
          1
        }
        this.isUpdating = false;
        this.errorMessage = null;
      } catch (error) {
        this.isUpdating = false;
        console.error(error);
        this.errorMessage = "更新用户信息失败，请重试。";
      }
    },
  },
};
</script>

<style scoped>
.profile-page {
    max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

.title {
    font-size: 2.5rem;
  margin-bottom: 50px;
  text-align: center;
}

.user-profile {
  display: flex;
  flex-direction: row;
  align-items: center;
}

.user-avatar {
  width: 200px;
  height: 200px;
  position: relative;
  margin-right: 50px;
}

.user-avatar img {
  max-width: 100%;
  max-height: 100%;
  object-fit: cover;
  border-radius: 50%;
}

.user-avatar input[type="file"] {
  position: absolute;
  bottom: 0;
  right: 0;
  opacity: 0;
  cursor: pointer;
}

.user-info {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.form-group {
  margin-bottom: 20px;
}

label {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 5px;
}

input[type="text"],
input[type="email"],
input[type="password"] {
  font-size: 1.2rem;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: 100%;
  box-sizing: border-box;
}

button.btn-primary {
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  font-size: 1.2rem;
  cursor: pointer;
}

button.btn-primary:hover {
  background-color: #0069d9;
}

.error-message {
  color: red;
  margin-top: 10px;
}
</style>
