<template>
  <div class="top-bar">
    <div class="logo">
      <router-link to="/">Movie System</router-link>
    </div>
    <div class="search-box">
      <input type="text" placeholder="Search movie title" v-model="searchText">
      <button @click="search">Search</button>
    </div>
    <div class="login">
      <router-link v-if="!isAuthenticated" to="/login">Login</router-link>
      <div v-else>
        <el-dropdown>
          <span class="el-dropdown-link" @mouseover="showMenu = true" @mouseleave="showMenu = false">
            <img class="avatar" :src="user.headPortrait" alt="用户头像">
          </span>
          <template v-slot:dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="profile">
                <router-link to="/profile">Profile</router-link>
              </el-dropdown-item>
              <el-dropdown-item @click="Logout" divided>Logout</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'top-bar',
  data() {
    return {
      searchText: "",
      showMenu: false
    }
  },
  computed: {
    ...mapGetters('auth', ['isAuthenticated', 'user']),
    ...mapGetters('auth', ['user', 'user']),
  },
  methods: {
    ...mapActions('auth', ['logout']),
    ...mapActions('movies', ['searchMovies']),
    search() {
      this.searchMovies(this.searchText);
    },
    Logout() {
      this.logout()
    }
  }
}
</script>
<style scoped>
.top-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
  background-color: #f1f1f1;
  border-bottom: 1px solid #ddd;
}

.logo {
  font-size: 24px;
  font-weight: bold;
  color: #007bff;
}

.nav {
  display: flex;
  align-items: center;
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

.user-panel {
  position: absolute;
  top: 100%;
  right: 0;
  z-index: 1;
  display: flex;
  flex-direction: column;
  background-color: #fff;
  border: 1px solid #ddd;
  border-top: none;
  padding: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
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

.user-panel.show {
  display: flex;
}

.el-dropdown-menu {
  width: 150px;
  height: 200px;
  color: #007bff;
}
</style>