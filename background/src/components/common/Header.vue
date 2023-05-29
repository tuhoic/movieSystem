<template>
  <header class="app-header">
    <el-row>
      <el-col :span="12">
        <div class="logo">
          <img src="../../assets/images/logo.png" alt="Logo" />
        </div>
      </el-col>
      <el-col :span="12">
        <div class="user-info">
          <el-dropdown trigger="click" @command="handleCommand">
            <span class="user-avatar">
              <img :src="userInfo.avatar" alt="User Avatar" />
            </span>
            <span class="user-name">{{ userInfo.name }}</span>

            <el-dropdown-menu>
              <template #dropdown>
                <el-dropdown-item command="profile">Profile</el-dropdown-item>
                <el-dropdown-item command="settings">Settings</el-dropdown-item>
                <el-dropdown-item command="logout">Logout</el-dropdown-item>
              </template>
            </el-dropdown-menu>

          </el-dropdown>
        </div>
      </el-col>
    </el-row>
  </header>
</template>

<script>
export default {
  name: 'HeaderPage',
  data() {
    return {
      userInfo: {
        name: 'John Doe',
        avatar: '../assets/images/avatar.jpg'
      }
    };
  },
  methods: {
    handleCommand(command) {
      switch (command) {
        case 'profile':
          // 处理个人资料点击事件
          break;
        case 'settings':
          // 处理设置点击事件
          break;
        case 'logout':
          // 处理退出登录点击事件
          break;
        default:
          break;
      }
    }
  }
};
</script>

<style scoped>
.app-header {
  background-color: #fff;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.logo img {
  width: 120px;
  height: auto;
}

.user-info {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.user-avatar {
  width: 40px;
  height: 40px;
  overflow: hidden;
  border-radius: 50%;
}

.user-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.user-name {
  margin-left: 8px;
}
</style>
