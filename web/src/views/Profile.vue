<template>
  <top-bar></top-bar>
  <div class="profile-container">
    <div class="profile-body">
      <div class="profile-sidebar">
        <el-menu :default-active="$route.path" class="el-menu-vertical-demo" @select="handleSelect">
          <el-menu-item index="/profile">个人信息</el-menu-item>
          <el-menu-item index="/password">修改信息</el-menu-item>
          <el-menu-item index="/history">历史记录</el-menu-item>
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
        <div v-else-if="showModification">
          <el-form enctype="multipart/form-data" :rules="formRules" ref="form">
            <el-form-item>
              <el-upload
                  action="http://localhost:8000/user/upload"
                  :show-file-list="false"
                  :on-success="handleUploadSuccess"
                  :on-error="handleUploadError"
              >
                <img :src="`/api/` + headPortrait" @error="setDefaultImage" alt="头像" class="avatar">
                <template v-slot:tip>
                  <div class="el-upload__tip">只能上传jpg/png文件，且不超过2MB</div>
                </template>

              </el-upload>
            </el-form-item>

            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
              <el-input type="password" v-model="form.newPassword"></el-input>
            </el-form-item>
            <!--            <el-form-item label="确认密码" prop="confirmPassword">-->
            <!--              <el-input type="password" v-model="form.confirmPassword"></el-input>-->
            <!--            </el-form-item>-->
            <el-form-item>
              <el-button type="primary" @click="saveChanges">保存修改</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div v-else-if="showHistory">
          <el-radio-group v-model="selectedOption" @change="loadHistory">
            <el-radio-button label="comments">评论</el-radio-button>
            <el-radio-button label="ratings">评分</el-radio-button>
            <el-radio-button label="favorites">收藏</el-radio-button>
          </el-radio-group>


          <el-table v-if="this.selectedOption === 'comments'" :data="historyData" stripe>
            <el-table-column prop="id" label="ID"></el-table-column>
            <el-table-column prop="username" label="Username"></el-table-column>
            <el-table-column prop="movieName" label="MovieName"></el-table-column>
            <el-table-column prop="content" label="内容"></el-table-column>
            <el-table-column prop="createTime" label="CreateTime"></el-table-column>
          </el-table>
          <el-table v-else-if="this.selectedOption === 'ratings'" :data="historyData" stripe>
            <el-table-column prop="id" label="ID" width="80"></el-table-column>
            <el-table-column prop="username" label="Username"></el-table-column>
            <el-table-column prop="movieName" label="MovieName"></el-table-column>
            <el-table-column prop="rating" label="Rating"></el-table-column>
            <el-table-column prop="createTime" label="CreateTime"></el-table-column>
          </el-table>
          <el-table v-else-if="this.selectedOption === 'favorites'" :data="historyData" stripe>
            <el-table-column prop="id" label="ID" width="80"></el-table-column>
            <el-table-column prop="username" label="Username"></el-table-column>
            <el-table-column prop="movieName" label="MovieName"></el-table-column>
            <el-table-column prop="createTime" label="CreateTime"></el-table-column>
          </el-table>


          <el-pagination
              @size-change="handlePageSizeChange"
              @current-change="handleCurrentPageChange"
              :current-page="currentPage"
              :page-sizes="[10, 20, 30, 50]"
              :page-size="pageSize"
              :total="totalRecords"
              layout="prev, pager, next, sizes, total"
          ></el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import TopBar from "@/components/Header.vue";
import axios from "axios";
import store from "@/store";
import router from "@/router";

export default {
  name: 'ProfilePage',
  data() {
    const validateUsername = (rule, value, callback) => {
      const reg = /^.{6,}$/; // 至少2个字符
      if (!reg.test(value)) {
        callback(new Error('用户名至少为6个字符'));
      } else {
        callback();
      }
    };
    // const validateEmail = (rule, value, callback) => {
    //   const reg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/; // 邮箱正则表达式
    //   if (!reg.test(value)) {
    //     callback(new Error('请输入正确的邮箱地址'));
    //   } else {
    //     callback();
    //   }
    // };
    const validateNewPassword = (rule, value, callback) => {
      const reg = /^.{8,}$/; // 至少8个字符
      if (!reg.test(value)) {
        callback(new Error('密码至少为8个字符'));
      } else {
        callback();
      }
    };
    // const validConfirmPassword = (rule, value, callback) => {
    //   console.log(value)
    //   console.log(this.form.newPassword)
    //   if (value !== this.form.newPassword) {
    //     callback(new Error('两次密码输入不一致'));
    //   } else {
    //     callback();
    //   }
    // };
    return {
      showInformation: true,
      showModification: false,
      showHistory: false,
      file: "",
      headPortrait: "",
      selectedOption: 'comments',
      historyData: [],
      currentPage: 1,
      pageSize: 10,
      totalRecords: 0,
      form: {
        username: '',
        email: '',
        newPassword: '',
        confirmPassword: ''
      },
      formRules: {
        username: [{required: true, validator: validateUsername, trigger: 'blur'}],
        // email: [{ required: true, validator: validateEmail, trigger: 'blur'}],
        newPassword: [{required: true, validator: validateNewPassword, trigger: 'blur'}],
        // confirmPassword: [{ required: true, validator: validConfirmPassword, trigger: 'blur' }],
      },
    };
  },
  components: {TopBar},
  computed: {
    ...mapGetters('auth', ['user']),
  },
  methods: {
    setDefaultImage(event) {
      event.target.src = require("@/assets/headPortrait.jpg");
    },
    handleSelect(index) {
      if (index === '/profile') {
        this.showInformation = true;
        this.showModification = false;
        this.showHistory = false;
      } else if (index === '/password') {
        this.showInformation = false;
        this.showModification = true;
        this.showHistory = false;
      } else if (index === '/history') {
        this.showInformation = false;
        this.showModification = false;
        this.showHistory = true;
        this.loadHistory()
      }
    },
    saveChanges() {
      // 发送表单数据到服务器保存
      axios.post('/user/update', {
        id: this.user.id,
        username: this.form.username,
        email: this.form.email,
        password: this.form.newPassword,
        headPortrait: this.headPortrait,
      }).then((() => {
        axios.get('/user/info').then((res => {
          localStorage.setItem('user', JSON.stringify(res.data.data));
          store.commit('auth/INIT_USER')
          router.go(0)
        }))
      })).catch((error => {
        console.log(error)
      }))
    },
    handleUploadSuccess(res, file) {
      file
      if (res.code === 200) {
        this.$message.success('上传成功')
        this.headPortrait = res.data
      } else {
        this.$message.error('上传失败')
      }
    },
    handleUploadError(err) {
      this.$message.error('上传失败')
      console.error(err)
    },
    loadHistory() {
      // 根据选项加载不同类型的历史记录数据
      if (this.selectedOption === 'comments') {
        this.loadCommentHistory();
      } else if (this.selectedOption === 'ratings') {
        this.loadRatingHistory();
      } else if (this.selectedOption === 'favorites') {
        this.loadFavoriteHistory();
      }
    },
    loadCommentHistory() {
      axios.get(`/comment/get?userId=${this.user.id}&current=${this.currentPage}&pageSize=${this.pageSize}`)
          .then((res => {
            this.historyData = res.data.data.records
            this.totalRecords = res.data.data.total
          }))
          .catch((error => {
            console.log(error)
          }))
    },
    loadRatingHistory() {
      axios.get(`/rating/getRating?userId=${this.user.id}&current=${this.currentPage}&pageSize=${this.pageSize}`)
          .then((res => {
            this.historyData = res.data.data.records
            this.totalRecords = res.data.data.total
          }))
          .catch((error => {
            console.log(error)
          }))
    },
    loadFavoriteHistory() {
      axios.get(`/favorites/getFavorite?userId=${this.user.id}&current=${this.currentPage}&pageSize=${this.pageSize}`)
          .then((res => {
            this.historyData = res.data.data.records
            this.totalRecords = res.data.data.total
          }))
          .catch((error => {
            console.log(error)
          }))
    },
    handlePageSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.currentPage = 1;
      this.loadHistory();
    },
    handleCurrentPageChange(page) {
      this.currentPage = page;
      this.loadHistory();
    }
  }
  ,
  mounted() {
    this.headPortrait = this.user.headPortrait
  }
}

</script>

<style scoped>
.profile-container {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.profile-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.profile-avatar img {
  border-radius: 50%;
  width: 100px;
  height: 100px;
}

.avatar {
  border-radius: 50%;
  width: 100px;
  height: 100px;
}

.profile-info {
  margin-left: 20px;
}

.profile-name {
  font-size: 24px;
}

.profile-email {
  font-size: 16px;
  color: #666;
}

.profile-location {
  font-size: 16px;
  color: #666;
}

.profile-body {
  display: flex;
  flex-direction: row;
  width: 100%;
  max-width: 1200px;
  margin-top: 20px;
}

.profile-sidebar {
  width: 200px;
  margin-right: 20px;
}

.profile-main {
  flex-grow: 1;
}

.el-form-item {
  margin-bottom: 20px;
}

.profile-form {
  max-width: 500px;
}

.profile-form__title {
  font-size: 20px;
  margin-bottom: 20px;
}

.profile-form__button {
  margin-top: 20px;
}

.profile-form__input {
  width: 100%;
}

.profile-form__input-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 10px;
}

.profile-form__input-group:last-child {
  margin-bottom: 0;
}
</style>
