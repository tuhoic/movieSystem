<template>
  <div class="top-bar">
    <div class="logo">
      <router-link to="/">电影系统</router-link>
    </div>
    <div class="search-box">
      <input type="text" placeholder="Search movie title" v-model="title" @keyup.enter="search">
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
  },
  methods: {
    ...mapActions('auth', ['logout', 'init']),
    ...mapActions('movies', ["fetchMovies", 'searchMovies']),
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
      event.target.src = this.defaultImage
    }
  },
  mounted() {

  }
}
</script>
<style scoped>
.top-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  background-color: #f1f1f1;
  border-bottom: 1px solid #ddd;
}

.logo {
  font-size: 24px;
  font-weight: bold;
}


.nav a {
  margin-left: 20px;
  color: #333;
  text-decoration: none;
}

.nav a:hover {
  color: #007bff;
}

.login {
  display: flex;
  align-items: center;
}

.avatar {
  width: 30px;
  height: 30px;
  margin-right: 10px;
  border-radius: 50%;
}

.user-panel a {
  color: #333;
  text-decoration: none;
  padding: 5px 10px;
}

.user-panel a:hover {
  background-color: #f1f1f1;
}

.user-panel a:last-child {
  border-top: 1px solid #ddd;
}


.el-dropdown-menu {
  width: 150px;
  height: 200px;
  color: #007bff;
}
</style>