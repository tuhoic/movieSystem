<template>
  <div class="login-page">
    <div class="login">
      <el-form ref="loginForm" :model="loginForm" :rules="loginFormRules" label-width="80px" class="login-form">
        <h1>Login</h1>
        <el-form-item label="Username" prop="username">
          <el-input v-model="loginForm.username"></el-input>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input type="password" v-model="loginForm.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin">Login</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import router from "@/router";

export default {
  name: 'LoginPage',
  data() {
    return {
      loginForm: {
        username: 'admin',
        password: 'password'
      },
      loginFormRules: {
        username: [{required: true, message: 'Please enter username', trigger: 'blur'}],
        password: [{required: true, message: 'Please enter password', trigger: 'blur'}]
      }
    };
  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          // 登录逻辑，可以发送登录请求到后端
          axios.post(`/admin/login`, this.loginForm)
              .then((res) => {
                if (res.data.code === 200) {
                  const token = res.data.data;
                  localStorage.setItem('token', token);
                  axios.defaults.headers.common['Authorization'] = token; // 设置axios默认请求头
                  axios.get('/admin/info') // 根据token获取用户信息
                      .then(adminRes => {
                        if (adminRes.data.code === 200) {
                          const admin = adminRes.data.data;
                          localStorage.setItem('admin', JSON.stringify(admin));
                          router.push('/dashboard');
                        }
                      })
                      .catch(error => {
                        this.$message.error('获取用户信息失败');
                        console.error(error);
                      });
                } else {
                  console.log('Form validation failed');
                  return false;
                }
              })
              .catch(error => {
                this.$message.error('登录失败');
                console.error(error);
              });
        }
      });
    }

  }
};

</script>

<style scoped>
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image: url("@/assets/images/login_background.png");
}

.login {
  max-width: 400px;
  padding: 50px 20px;
  text-align: center;
  background-color: rgba(255, 255, 255, 0.8);
}

h1 {
  font-size: 24px;
  margin-bottom: 20px;
}

.login-form {
  margin-top: 20px;
}
</style>