<template>
  <div>
    <h1>用户列表</h1>
    <el-table :data="userList" style="width: 100%">
      <el-table-column prop="id" label="ID"></el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="head_portrait" label="头像">
        <template v-slot="scope">
          <img :src="scope.row.head_portrait" style="width: 50px; height: 50px;">
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button @click="editUser(scope.row)">编辑</el-button>
          <el-button type="danger" @click="deleteUser(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <h1>电影列表</h1>
    <el-table :data="movieList" style="width: 100%">
      <el-table-column prop="id" label="ID"></el-table-column>
      <el-table-column prop="title" label="电影名"></el-table-column>
      <el-table-column prop="director" label="导演"></el-table-column>
      <el-table-column prop="cast" label="演员"></el-table-column>
      <el-table-column prop="duration" label="时长"></el-table-column>
      <el-table-column prop="genre" label="类型"></el-table-column>
      <el-table-column prop="releas_date" label="上映日期"></el-table-column>
      <el-table-column prop="rating" label="评分"></el-table-column>
      <el-table-column prop="cover_url" label="封面">
        <template v-slot="scope">
          <img :src="scope.row.cover_url" style="width: 50px; height: 50px;">
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button @click="editMovie(scope.row)">编辑</el-button>
          <el-button type="danger" @click="deleteMovie(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <h1>用户查询</h1>
    <el-input v-model="userQuery" placeholder="请输入用户名或邮箱"></el-input>
    <el-button type="primary" @click="searchUser">查询</el-button>

    <h1>电影查询</h1>
    <el-input v-model="movieQuery" placeholder="请输入电影名或导演"></el-input>
    <el-button type="primary" @click="searchMovie">查询</el-button>

    <el-dialog :visible="userDialogVisible" @update:visible="val => userDialogVisible = val">
      <h2>编辑用户</h2>
      <el-form :model="userForm" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="userForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="userForm.password"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="userForm.email"></el-input>
        </el-form-item>
        <el-form-item label="头像">
          <el-input v-model="userForm.head_portrait"></el-input>
        </el-form-item>
      </el-form>
      <template v-slot:footer>
        <el-button @click="userDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveUser">保存</el-button>
      </template>
    </el-dialog>
    <el-dialog :visible="movieDialogVisible" @update:visible="val => movieDialogVisible = val">
      <h2>编辑电影</h2>
      <el-form :model="movieForm" label-width="80px">
        <el-form-item label="电影名">
          <el-input v-model="movieForm.title"></el-input>
        </el-form-item>
        <el-form-item label="导演">
          <el-input v-model="movieForm.director"></el-input>
        </el-form-item>
        <el-form-item label="演员">
          <el-input v-model="movieForm.cast"></el-input>
        </el-form-item>
        <el-form-item label="时长">
          <el-input v-model="movieForm.duration"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-input v-model="movieForm.genre"></el-input>
        </el-form-item>
        <el-form-item label="上映日期">
          <el-date-picker v-model="movieForm.releas_date" type="date"></el-date-picker>
        </el-form-item>
        <el-form-item label="评分">
          <el-input v-model="movieForm.rating"></el-input>
        </el-form-item>
        <el-form-item label="封面">
          <el-input v-model="movieForm.cover_url"></el-input>
        </el-form-item>
      </el-form>
      <template v-slot:footer>
        <el-button @click="userDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveUser">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  name: "TestPage",
  data () {
    return {
      userList: [],
      movieList: [],
      userQuery: '',
      movieQuery: '',
      userDialogVisible: false,
      movieDialogVisible: false,
      userForm: {},
      movieForm: {}
    }
  },
  created () {
    this.getUserList()
    this.getMovieList()
  },
  methods: {
    getUserList () {
      axios.get('/api/users')
          .then(res => {
            this.userList = res.data
          })
    },
    getMovieList () {
      axios.get('/api/movies')
          .then(res => {
            this.movieList = res.data
          })
    },
    editUser (user) {
      this.userForm = Object.assign({}, user)
      this.userDialogVisible = true
    },
    editMovie (movie) {
      this.movieForm = Object.assign({}, movie)
      this.movieDialogVisible = true
    },
    saveUser () {
      axios.put('/api/users/' + this.userForm.id, this.userForm)
          .then(res => {
            this.$message.success(res)
            this.getUserList()
            this.userDialogVisible = false
          })
    },
    saveMovie () {
      axios.put('/api/movies/' + this.movieForm.id, this.movieForm)
          .then(res => {
            this.$message.success(res)

            this.getMovieList()
            this.movieDialogVisible = false
          })
    },
    deleteUsers () {
      const ids = this.multipleSelection.map(item => item.id).join(',')
      axios.delete('/api/users/' + ids)
          .then(res => {
            this.$message.success(res)

            this.getUserList()
            this.multipleSelection = []
          })
    },
    deleteMovies () {
      const ids = this.multipleSelection.map(item => item.id).join(',')
      axios.delete('/api/movies/' + ids)
          .then(res => {
            this.$message.success(res)

            this.getMovieList()
            this.multipleSelection = []
          })
    }
  }
}
</script>

<style>
/* 重置默认样式 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* 主体内容区域 */
.content {
  margin: 20px;
}

/* 表格样式 */
.table-wrapper {
  margin-top: 20px;
}

/* 编辑对话框样式 */
.form-wrapper {
  margin: 20px 0;
}

/* 操作按钮样式 */
.button-group {
  margin-top: 20px;
}
</style>