<template>
  <div class="register-container">
    <h2>用户注册</h2>
    <el-form :model="registerForm" :rules="registerRules" ref="registerForm" label-width="80px" class="register-form">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="registerForm.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="registerForm.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="registerForm.password" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleRegister">注册</el-button>
        <el-button type="primary" @click="toLogin">已有账号？</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { mapActions } from 'vuex';
import router from "@/router";

export default {
  name: 'RegisterPage',
  data() {
    const validateUsername = (rule, value, callback) => {
      const reg = /^.{2,}$/; // 至少2个字符
      if (!reg.test(value)) {
        callback(new Error('用户名至少为2个字符'));
      } else {
        callback();
      }
    };
    const validateEmail = (rule, value, callback) => {
      const reg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/; // 邮箱正则表达式
      if (!reg.test(value)) {
        callback(new Error('请输入正确的邮箱地址'));
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
      registerForm: {
        username: '',
        email: '',
        password: ''
      },
      registerRules: {
        username: [{ required: true, validator: validateUsername, trigger: 'blur' }],
        email: [{ required: true, validator: validateEmail, trigger: 'blur'}],
        password: [{ required: true, validator: validatePassword, trigger: 'blur' }],
      },
    };
  },
  methods: {
    ...mapActions('auth', ['register']), // 引入 register action
    handleRegister() {
      this.$refs.registerForm.validate(valid => { // 表单验证
        if (valid) {
          this.register(this.registerForm)
        } else {
          return false;
        }
      });
    },
    toLogin() {
      router.push("/login")
    }
  },
};
</script>
<style scoped>
.register-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  /*background-image: url("image.jpg");*/
  background-repeat: no-repeat;
  background-size: cover;
}

.register-form {
  margin-top: 50px;
  max-width: 400px;
  width: 100%;
  background-color: rgba(255, 255, 255, 0.8);
  padding: 20px;
  border-radius: 5px;
}
</style>