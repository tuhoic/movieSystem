<template>
  <div class="login-container">
    <h1 style="color: #007bff">用户登录</h1>
    <el-form :model="loginForm" :rules="loginRules" ref="loginForm" label-width="80px" class="login-form">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleLogin">登录</el-button>
        <el-button type="primary" @click="toRegister">没有账号？</el-button>
        <el-button type="primary" @click="toModification">忘记密码?</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapActions } from 'vuex';
import router from "@/router";

export default {
  name: 'LoginPage',
  data() {
    const validateUsername = (rule, value, callback) => {
      const reg = /^.{2,}$/; // 至少2个字符
      if (!reg.test(value)) {
        callback(new Error('用户名至少为2个字符'));
      } else {
        callback();
      }
    };
    const validatePassword = (rule, value, callback) => {
      const reg = /^.{8,}$/; // 至少8个字符
      if (!reg.test(value)) {
        callback(new Error('密码至少为8个字符'));
      } else {
        callback();
      }
    };
    return {
      loginForm: {
        username: 'user1',
        password: 'password1',
      },
      loginRules: {
        username: [{ required: true, validator: validateUsername, trigger: 'blur' }],
        password: [{ required: true, validator: validatePassword, trigger: 'blur' }],
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
    toRegister() {
      router.push("/register")
    },
    toModification() {
      router.push("/modification")
    }
  },
};
</script>

<style scoped>
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 80vh;
  /*background-image: url("image.jpg");*/
  background-repeat: no-repeat;
  background-size: cover;
}

.login-form {
  margin-top: 15px;
  max-width: 400px;
  width: 100%;
  background-color: rgba(255, 255, 255, 0.8);
  padding: 25px;
  border-radius: 20px;
  border: 1px solid #ccc;
}

el-input {
  border-radius: 3px;
}

el-button {
  border-radius: 3px;
}

el-form-item__label {
  font-weight: bold;
}
</style>
