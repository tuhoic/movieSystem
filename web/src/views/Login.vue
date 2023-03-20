<template>
  <div class="login-container">
    <h2>用户登录</h2>
    <el-form :model="loginForm" :rules="loginRules" ref="loginForm" label-width="80px" class="login-form">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleLogin">登录</el-button>
        <el-button>
          <router-link to="/register" class="register-link">去注册</router-link>
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  name: 'LoginPage',
  data() {
    return {
      loginForm: {
        username: 'user1',
        password: 'password1',
      },
      loginRules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
      },
    };
  },
  methods: {
    ...mapActions('auth', ['login']), // 引入 login action
    handleLogin() {
      this.$refs.loginForm.validate(valid => { // 表单验证
        if (valid) {
          this.login(this.loginForm); // 调用 login action
        } else {
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>
.login-container {
  margin: 50px auto;
  width: 400px;
  text-align: center;
}
.login-form {
  margin-top: 50px;
}
.register-link {
  margin-left: 20px;
}
</style>
