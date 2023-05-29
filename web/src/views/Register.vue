<template>
  <top-bar class="new" v-if="showTopBar"></top-bar>
  <div class="register-container">
    <h1 style="color: #007bff">用户注册</h1>
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
      <el-form-item label="确认密码" prop="password">
        <el-input type="password" v-model="registerForm.confirmPassword" placeholder="请确认密码"></el-input>
      </el-form-item>
      <el-form-item>
        <slide-verify
            ref="slider"
            :slider-text="'Slide to verify'"
            :imgs="
          [
              require('@/assets/default_1.jpeg'),
              require('@/assets/default_2.jpeg'),
              require('@/assets/default_3.jpeg'),
              require('@/assets/default_4.jpeg'),
              require('@/assets/default_5.jpeg'),
              require('@/assets/default_6.jpeg'),
              require('@/assets/default_7.jpeg'),
              require('@/assets/default_8.jpeg'),
              require('@/assets/default_9.jpeg'),
              require('@/assets/default_10.jpeg'),
              require('@/assets/default_11.jpeg'),
              require('@/assets/default_12.jpeg'),
              require('@/assets/default_13.jpeg'),
              require('@/assets/default_14.jpeg'),
              require('@/assets/default_15.jpeg'),
              require('@/assets/default_16.jpeg'),
              require('@/assets/default_17.jpeg'),
              require('@/assets/default_18.jpeg'),
              require('@/assets/default_19.jpeg'),
              require('@/assets/default_20.jpeg'),
              require('@/assets/default_21.jpeg'),
              require('@/assets/default_22.jpeg'),
              require('@/assets/default_23.jpeg'),
              require('@/assets/default_24.jpeg'),
              require('@/assets/default_25.jpeg'),
              require('@/assets/default_26.jpeg'),
              require('@/assets/default_27.jpeg'),
              require('@/assets/default_28.jpeg'),
              require('@/assets/default_29.jpeg'),
              require('@/assets/default_30.jpeg'),
              require('@/assets/default_31.jpeg'),
              require('@/assets/default_32.jpeg'),
              require('@/assets/default_33.jpeg'),
              require('@/assets/default_34.jpeg'),
              require('@/assets/default_35.jpeg'),
              require('@/assets/default_36.jpeg'),
              require('@/assets/default_37.jpeg'),
              require('@/assets/default_38.jpeg'),
              require('@/assets/default_39.jpeg'),
              require('@/assets/default_40.jpeg'),
              require('@/assets/default_41.jpeg'),
              require('@/assets/default_42.jpeg'),
              require('@/assets/default_43.jpeg'),
              require('@/assets/default_44.jpeg'),
              require('@/assets/default_45.jpeg'),
              require('@/assets/default_46.jpeg'),
              require('@/assets/default_47.jpeg'),
              require('@/assets/default_48.jpeg'),
              require('@/assets/default_49.jpeg'),
              require('@/assets/default_50.jpeg'),
              require('@/assets/default_51.jpeg'),
              require('@/assets/default_52.jpeg'),
              require('@/assets/default_53.jpeg'),
              require('@/assets/default_54.jpeg'),
              require('@/assets/default_55.jpeg'),
              require('@/assets/default_56.jpeg'),
              require('@/assets/default_57.jpeg'),
              require('@/assets/default_58.jpeg'),
              require('@/assets/default_59.jpeg'),
              require('@/assets/default_60.jpeg'),
              require('@/assets/default_61.jpeg'),
              require('@/assets/default_62.jpeg'),
              require('@/assets/default_63.jpeg'),
              require('@/assets/default_64.jpeg'),
              require('@/assets/default_65.jpeg'),
              require('@/assets/default_66.jpeg'),
              require('@/assets/default_67.jpeg'),
              require('@/assets/default_68.jpeg'),
              require('@/assets/default_69.jpeg'),
              require('@/assets/default_70.jpeg'),
              require('@/assets/default_71.jpeg'),
              require('@/assets/default_72.jpeg'),
              require('@/assets/default_73.jpeg'),
              require('@/assets/default_74.jpeg'),
              require('@/assets/default_75.jpeg'),
              require('@/assets/default_76.jpeg'),
              require('@/assets/default_77.jpeg'),
              require('@/assets/default_78.jpeg'),
              require('@/assets/default_79.jpeg'),
              require('@/assets/default_80.jpeg'),
              require('@/assets/default_81.jpeg'),
              require('@/assets/default_82.jpeg'),
              require('@/assets/default_83.jpeg'),
              require('@/assets/default_84.jpeg'),
              require('@/assets/default_85.jpeg'),
              require('@/assets/default_86.jpeg'),
              require('@/assets/default_87.jpeg'),
              require('@/assets/default_88.jpeg'),
              require('@/assets/default_89.jpeg'),
              require('@/assets/default_90.jpeg'),
              require('@/assets/default_91.jpeg'),
              require('@/assets/default_92.jpeg'),
              require('@/assets/default_93.jpeg'),
              require('@/assets/default_94.jpeg'),
              require('@/assets/default_95.jpeg'),
              require('@/assets/default_96.jpeg'),
              require('@/assets/default_97.jpeg'),
              require('@/assets/default_98.jpeg'),
              require('@/assets/default_99.jpeg'),
              require('@/assets/default_100.jpeg'),
          ]"
            :accuracy="0.8"
            @success="onSliderSuccess"
            @fail="onSliderFail"
        ></slide-verify>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :disabled="!isVerified" @click="handleRegister">注册</el-button>
        <el-button type="primary" @click="toLogin">已有账号？</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapActions } from 'vuex';
import router from "@/router";
import TopBar from "@/components/Header.vue";
import SlideVerify from "vue3-slide-verify";
import "vue3-slide-verify/dist/style.css";


export default {
  name: 'RegisterPage',
  components: {TopBar, SlideVerify},
  data() {
    const validateUsername = (rule, value, callback) => {
      const reg = /^.{6,}$/; // 至少2个字符
      if (!reg.test(value)) {
        callback(new Error('用户名至少为6个字符'));
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
      if (value !== this.registerForm.password) {
        callback(new Error('两次密码输入不一致'));
      } else {
        callback();
      }
    };
    return {
      registerForm: {
        username: 'username',
        email: 'username@qq.com',
        password: 'password',
        confirmPassword: 'password'
      },
      registerRules: {
        username: [{ required: true, validator: validateUsername, trigger: 'blur' }],
        email: [{ required: true, validator: validateEmail, trigger: 'blur'}],
        password: [{ required: true, validator: validatePassword, trigger: 'blur' }],
        confirmPassword: [{ required: true, validator: validConfirmPassword, trigger: 'blur' }],
      },
      showTopBar: false,
      isVerified: false,
    };
  },
  created() {
  },
  mounted() {
    window.addEventListener('mousemove', this.handleMouseMove)
  },
  methods: {
    ...mapActions('auth', ['register']), // 引入 register action
    handleMouseMove(event) {
      const y = event.clientY
      this.showTopBar = y <= 200;
    },
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
    },
    onSliderSuccess() {
      console.log("Slider verification successful!");
      this.isVerified = true;
    },
    onSliderFail() {
      console.log("Slider verification failed!");
      this.isVerified = false;
    },
  },
  beforeUnmount() {
    window.removeEventListener('mousemove', this.handleMouseMove);
  }

};

</script>

<style scoped>
.new {
  position: fixed; /* 固定在页面顶部 */
  top: 0; /* 距离页面顶部0像素 */
  left: 0; /* 距离页面左侧0像素 */
  right: 0; /* 距离页面右侧0像素 */
  z-index: 9999; /* 使其显示在最上层 */
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  background-color: #f1f1f1;
  border-bottom: 1px solid #ddd;
  height: 30px; /* 增加导航栏高度 */
}
.register-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-image: url("@/assets/register_background.png");
  background-repeat: no-repeat;
  background-size: cover;
}

.register-form {
  margin-top: 15px;
  max-width: 400px;
  width: 100%;
  background-color: rgba(255, 255, 255, 0.8);
  padding: 20px;
  border-radius: 20px;
  border: 1px solid #ccc;
}
</style>