<template>
  <top-bar></top-bar>
  <div class="profile-container">
    <div class="profile-body">
      <div class="profile-sidebar">
        <el-menu :default-active="$route.path" class="el-menu-vertical-demo" @select="handleSelect">
          <el-menu-item index="/profile">个人信息</el-menu-item>
          <el-menu-item index="/password">修改信息</el-menu-item>
        </el-menu>
      </div>
      <div class="profile-main">
        <div v-if="showInformation" class="profile-header">
          <div class="profile-avatar">
            <img :src="`/api/` + user.headPortrait" @error="setDefaultImage" alt="头像">
          </div>
          <div class="profile-info">
            <div class="profile-name">{{ user.username }}</div>
            <div class="profile-email">{{ user.email }}</div>
          </div>
        </div>
        <div v-else>
          <el-form :model="form" ref="form" :rules="rules" label-width="100px">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email"></el-input>
            </el-form-item>
            <el-form-item label="原密码" prop="oldPassword">
              <el-input type="password" v-model="form.oldPassword"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
              <el-input type="password" v-model="form.newPassword"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input type="password" v-model="form.confirmPassword"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm">保存修改</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import TopBar from "@/components/Header.vue";

export default {
  name: 'ProfilePage',
  data() {
    return {
      showInformation: true,
      form: {
        username: '',
        email: '',
        oldPassword: '',
        newPassword: '',
        confirmPassword: '',
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' },
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change' },
        ],
        oldPassword: [
          { required: true, message: '请输入原密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
        ],
        newPassword: [
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
        ],
        confirmPassword: [
          { required: true, message: '请再次确认密码', trigger: 'blur' },
          { validator: checkPassword, trigger: 'blur' },
        ],
      }
    };
  },
  methods: {
    saveChanges() {
      this.$refs.form.validate(valid => {
        if (valid) {
// 发送保存修改请求
          console.log(this.user);
// 清空密码框
          this.user.password = '';
          this.confirmPassword = '';
          this.$message.success('保存成功');
        } else {
          this.$message.error('表单填写不完整或有误');
        }
      });
    },
    checkPassword(rule, value, callback) {
      if (value !== this.user.newPassword) {
        callback(new Error('两次输入的密码不一致'));
      } else {
        callback();
      }
    },
  },
};
</script>