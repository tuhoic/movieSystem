<template>
  <div class="login-form">
    <h2>登录</h2>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="username">用户名:</label>
        <input type="text" id="username" v-model="form.username" :class="{ 'is-invalid': errors.username }">
        <div class="invalid-feedback">{{ errors.username }}</div>
      </div>
      <div class="form-group">
        <label for="password">密码:</label>
        <input type="password" id="password" v-model="form.password" :class="{ 'is-invalid': errors.password }">
        <div class="invalid-feedback">{{ errors.password }}</div>
      </div>
      <button type="submit" class="btn btn-primary">登录</button>
    </form>
    <p class="mt-3">没有账号？<router-link to="/register">注册</router-link></p>
  </div>
</template>

<script>
import axios from '@/api/user';

export default {
  data() {
    return {
      form: {
        username: '',
        password: '',
        confirmPassword: '',
        email: '',
      },
      errors: {},
    };
  },
  methods: {
    async submitForm() {
      try {
        const response = await axios.post('/login', this.form);
        console.log(response.data);
        // 处理注册成功后的逻辑
      } catch (error) {
        console.log(error.response.data);
        this.errors = error.response.data.errors;
      }
    },
  },
};
</script>

<style>
.login-form{
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.5);
  border-radius: 8px;
  width: 400px;
  max-width: 100%;
  padding: 0px 35px 0;
  position:absolute;
  top:30%;
  left:50%;
  margin:-160px 0 0 -200px;
}

.form-group {
  margin-bottom: 20px;
}

label {
  font-weight: bold;
}

input[type="text"],
input[type="email"],
input[type="password"] {
  width: 100%;
  height: 50px;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

button[type="submit"] {
  width: 100%;
  height: 50px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
}

button[type="submit"]:hover {
  background-color: #0069d9;
}

.invalid-feedback {
  color: red;
  margin-top: 5px;
}

.is-invalid {
  border-color: red;
}
</style>
