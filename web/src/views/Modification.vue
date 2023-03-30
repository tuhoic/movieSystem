<template>
  <div class="modification-container">
    <h1 style="color: #007bff">修改密码</h1>
    <el-form :model="modificationForm" :rules="modificationRules" ref="modificationForm" label-width="80px" class="login-form">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="modificationForm.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="password">
        <el-input type="password" v-model="modificationForm.email" placeholder="请输入注册时的邮箱"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="modificationForm.password" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="password">
        <el-input type="password" v-model="modificationForm.confirmPassword" placeholder="请确认密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleModification">修改密码</el-button>
        <el-button type="primary" @click="toRegister">没有账号？</el-button>
        <el-button type="primary" @click="toLogin">想起密码?</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapActions } from 'vuex';
import router from "@/router";

export default {
  name: 'ModificationPage',
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
    const validConfirmPassword = (rule, value, callback) => {
      if (value !== this.modificationForm.password) {
        callback(new Error('两次密码输入不一致'));
      } else {
        callback();
      }
    };
    return {
      modificationForm: {
        username: 'user1',
        email: "user1@example.com",
        password: 'password1',
        confirmPassword: "password1",
      },
      modificationRules: {
        username: [{ required: true, validator: validateUsername, trigger: 'blur' }],
        email: [{ required: true, validator: validateEmail, trigger: 'blur' }],
        password: [{ required: true, validator: validatePassword, trigger: 'blur' }],
        confirmPassword: [{ required: true, validator: validConfirmPassword, trigger: 'blur'}]
      },
    };
  },
  methods: {
    ...mapActions('auth', ['login', 'modification']), // 引入 login、modification action
    handleModification() {
      this.$refs.modificationForm.validate(valid => { // 表单验证
        if (valid) {
          this.modification(this.modificationForm); // 调用 modification action
        } else {
          return false;
        }
      });
    },
    toRegister() {
      router.push("/register")
    },
    toLogin() {
      router.push("/login")
    }
  },
};
</script>

<style scoped>
.modification-container {
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
