<template>
  <aside class="app-sidebar">
    <el-menu :default-active="activeMenu" class="menu" :collapse="isCollapse" router>
      <el-menu-item index="/" @click="navigateTo('/')">
        <i class="el-icon-house"></i>
        <template v-slot:title>
          <span>Dashboard</span>
        </template>
      </el-menu-item>
      <el-submenu index="/user" @click="navigateTo('/user')">
        <template v-slot:title>
          <i class="el-icon-user"></i>
          <span>User Management</span>
        </template>
        <el-menu-item index="/user/list">
          <i class="el-icon-user-solid"></i>
          <span>User List</span>
        </el-menu-item>
        <el-menu-item index="/user/create">
          <i class="el-icon-plus"></i>
          <span>Create User</span>
        </el-menu-item>
      </el-submenu>
      <el-submenu index="/movie" @click="navigateTo('/movie')">
        <template v-slot:title>
          <i class="el-icon-video-camera-solid"></i>
          <span>Movie Management</span>
        </template>
        <el-menu-item index="/movie/list">
          <i class="el-icon-tickets"></i>
          <span>Movie List</span>
        </el-menu-item>
        <el-menu-item index="/movie/create">
          <i class="el-icon-circle-plus"></i>
          <span>Create Movie</span>
        </el-menu-item>
      </el-submenu>
    </el-menu>
    <div class="collapse-btn" @click="toggleCollapse">
      <i :class="isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'"></i>
    </div>
  </aside>
</template>

<script>
export default {
  name: 'SidebarPage',
  data() {
    return {
      isCollapse: false, // 是否折叠侧边栏
      activeMenu: '/' // 当前激活的菜单项
    };
  },
  methods: {
    navigateTo(route) {
      this.$router.push(route); // 导航到指定路由
    },
    toggleCollapse() {
      this.isCollapse = !this.isCollapse; // 切换侧边栏折叠状态
    }
  }
};
</script>

<style scoped>
.app-sidebar {
  width: 200px;
  background-color: #f5f7fa;
  transition: width 0.3s;
  overflow: hidden;
}

.menu {
  height: 100%;
}

.collapse-btn {
  position: absolute;
  right: 0;
  bottom: 0;
  width: 40px;
  height: 40px;
  background-color: #f5f7fa;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: right 0.3s;
}

.collapse-btn i {
  font-size: 20px;
}
</style>
