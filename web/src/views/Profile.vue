<template>
  <top-bar></top-bar>
  <div class="profile-container">
    <div class="profile-header">
      <div class="profile-avatar">
        <img :src="`/api/` + user.headPortrait" @error="setDefaultImage" alt="头像">
      </div>
      <div class="profile-info">
        <div class="profile-name">{{ user.username }}</div>
        <div class="profile-email">{{ user.email }}</div>
      </div>
    </div>
    <div class="profile-body">
      <div class="profile-sidebar">
        <el-menu :default-active="$route.path" class="el-menu-vertical-demo" @select="handleSelect">
          <el-menu-item index="/profile">个人信息</el-menu-item>
          <el-menu-item index="/password">修改信息</el-menu-item>
        </el-menu>
      </div>
      <div class="profile-main">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import TopBar from "@/components/Header.vue";

export default {
  name: 'ProfilePage',
  components: {TopBar},
  computed: {
    ...mapGetters('auth', ['user']),
  },
  methods: {
    setDefaultImage(event) {
        event.target.src = require("@/assets/headPortrait.jpg");
    },
    handleSelect(key) {
      this.$router.push({ path: key })
    }
  }
}
</script>

<style scoped>
.profile-container {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.profile-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.profile-avatar img {
  border-radius: 50%;
  width: 100px;
  height: 100px;
}

.profile-info {
  margin-left: 20px;
}

.profile-name {
  font-size: 24px;
}

.profile-email {
  font-size: 16px;
  color: #666;
}

.profile-location {
  font-size: 16px;
  color: #666;
}

.profile-body {
  display: flex;
  flex-direction: row;
  width: 100%;
  max-width: 1200px;
  margin-top: 20px;
}

.profile-sidebar {
  width: 200px;
  margin-right: 20px;
}

.profile-main {
  flex-grow: 1;
}
</style>
