<template>
  <div class="top-bar">
    <div class="logo">
      <router-link to="/" @click="handleClick">首页</router-link>
    </div>
    <div v-if="isAuthenticated" class="logo">
      <router-link to="/" @click="recommend">电影推荐</router-link>
    </div>
    <div class="search-box">
      <input type="text" :placeholder=placeholder v-model="title" @keyup.enter="search">
      <button @click="search">搜索</button>
    </div>
    <div class="login">
      <router-link v-if="!isAuthenticated" to="/login">登录</router-link>
      <div v-else>
        <el-dropdown>
          <span class="el-dropdown-link" @mouseover="showMenu = true" @mouseleave="showMenu = false">
            <img class="avatar" :src="`/api/` + user.headPortrait" @error="setDefaultImage" alt="用户头像">
          </span>
          <template v-slot:dropdown>
            <el-dropdown-menu class="el-dropdown-menu">
              <el-dropdown-item @click="toProfile" divided>个人信息</el-dropdown-item>
              <el-dropdown-item @click="Logout" divided>退出</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import router from "@/router";

export default {
  name: 'top-bar',
  data() {
    return {
      title: "",
      showMenu: false,
      defaultImage: "api/headPortrait.jpg"
    }
  },
  computed: {
    ...mapGetters('auth', ['isAuthenticated', 'user']),
    ...mapGetters('movies', ['searchTitle', 'currentPage', 'currentMovie']),
    placeholder() {
      return this.title ? this.title : "Search movie title";
    }
  },
  methods: {
    ...mapActions('auth', ['logout',]),
    ...mapActions('movies', ["fetchMovies", 'searchMovies', 'recommendations', 'handleCurrentChange']),
    search() {
      this.searchMovies(this.title);
      this.fetchMovies();
    },
    toProfile() {
      router.push("/profile")
    },
    Logout() {
      this.logout()
    },
    setDefaultImage(event) {
      event.target.src = require("@/assets/headPortrait.jpg")
    },
    handleClick() {
      this.title = ''
      this.search()
    },
    recommend() {
      this.handleCurrentChange(1)
      this.recommendations(this.user.id)
    }
  },
  mounted() {
    this.title = this.searchTitle
  }
}
</script>

<style scoped>
.top-bar {
  position: fixed; /* 固定在页面顶部 */
  top: 0; /* 距离页面顶部0像素 */
  left: 0; /* 距离页面左侧0像素 */
  right: 0; /* 距离页面右侧0像素 */
  z-index: 9999; /* 使其显示在最上层 */
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  background-color: #f1f1f1;
  border-bottom: 1px solid #ddd;
  height: 30px; /* 增加导航栏高度 */
}


.logo {
  font-size: 32px; /* 调整 logo 大小 */
  font-weight: bold;
  margin-right: 20px; /* 调整 logo 与搜索框之间的距离 */
}

.search-box {
  display: flex;
  align-items: center;
}

.search-box input[type="text"] {
  width: 300px; /* 调整搜索框宽度 */
  height: 40px; /* 调整搜索框高度 */
  border-radius: 20px; /* 调整搜索框圆角 */
  border: none;
  padding: 0 20px;
  font-size: 16px;
  background-color: #fff;
  box-shadow: 0 0 5px #ddd;
}

.search-box button {
  width: 80px;
  height: 40px;
  border-radius: 20px;
  border: none;
  background-color: #007bff;
  color: #fff;
  font-size: 16px;
  margin-left: 10px; /* 调整搜索按钮与搜索框之间的距离 */
}

.login {
  display: flex;
  align-items: center;
}

.avatar {
  width: 40px; /* 调整头像大小 */
  height: 40px;
  margin-right: 10px;
  border-radius: 50%;
}

.el-dropdown-menu {
  width: 150px;
  height: auto; /* 调整下拉菜单高度 */
  color: #007bff;
  background-color: #fff; /* 增加下拉菜单的背景色 */
  border: none; /* 去掉下拉菜单的边框 */
  box-shadow: 0 0 5px #ddd;
}

el-dropdown-item {
  padding: 10px; /* 调整下拉菜单项的内边距 */
}

el-dropdown-item:hover {
  background-color: #f1f1f1; /* 增加鼠标悬停时的背景色 */
}

</style>