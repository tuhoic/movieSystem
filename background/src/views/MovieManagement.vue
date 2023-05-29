<template>
  <el-button type="primary" @click="goToDashboard">Go to Dashboard</el-button>
  <div class="movie-management">
    <h1>Movie Management</h1>
    <div class="container">
      <el-button type="primary" class="add-movie-button" @click="showCreateMovieDialog">Add Movie</el-button>

      <el-input v-model="searchText" placeholder="请输入关键字" @keyup.enter="search" class="search-input"></el-input>

      <el-button type="primary" @click="search" class="search-button">搜索</el-button>
    </div>
    <el-table :data="movieList" style="width: 100%" stripe>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="title" label="Title"></el-table-column>
      <el-table-column prop="director" label="Director"></el-table-column>
      <el-table-column prop="cast" label="Cast"></el-table-column>
      <el-table-column prop="duration" label="Duration"></el-table-column>
      <el-table-column label="Actions" width="180">
        <template v-slot="scope">
          <el-button type="primary" size="small" @click="showEdieMovieDialog(scope.row)">Edit</el-button>
          <el-button type="danger" size="small" @click="showDeleteMovieDialog(scope.row)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="Create Movie" v-model="createMovieDialogVisible" width="30%" @close="resetCreateMovieDialog">
      <el-form ref="createMovieForm" :model="createMovieForm" :rules="createMovieFormRules" label-width="100px">
        <el-form-item label="Title" prop="title">
          <el-input v-model="createMovieForm.title"></el-input>
        </el-form-item>
        <el-form-item label="Director" prop="director">
          <el-input v-model="createMovieForm.director"></el-input>
        </el-form-item>
        <el-form-item label="Cast" prop="cast">
          <el-input v-model="createMovieForm.cast"></el-input>
        </el-form-item>
        <el-form-item label="Duration" prop="duration">
          <el-input v-model="createMovieForm.duration"></el-input>
        </el-form-item>
      </el-form>

      <template v-slot:footer>
        <div class="dialog-footer">
          <el-button @click="resetCreateMovieDialog">Cancel</el-button>
          <el-button type="primary" @click="handleCreateMovie">Create</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog title="Edit Movie" v-model="editMovieDialogVisible" width="30%" @close="resetEditMovieDialog">
      <el-form ref="edieMovieForm" :model="edieMovieForm" :rules="edieMovieFormRules" label-width="100px">
        <el-form-item label="Title" prop="title">
          <el-input v-model="edieMovieForm.title"></el-input>
        </el-form-item>
        <el-form-item label="Director" prop="director">
          <el-input v-model="edieMovieForm.director"></el-input>
        </el-form-item>
        <el-form-item label="Cast" prop="cast">
          <el-input v-model="edieMovieForm.cast"></el-input>
        </el-form-item>
        <el-form-item label="Duration" prop="duration">
          <el-input v-model="edieMovieForm.duration"></el-input>
        </el-form-item>
      </el-form>

      <el-button @click="resetEditMovieDialog">Cancel</el-button>
      <el-button type="primary" @click="handleEditMovie">Edit</el-button>

    </el-dialog>

    <el-dialog title="Delete Movie" v-model="deleteMovieDialogVisible" width="30%" @close="resetDeleteMovieDialog">
      <p>Are you sure you want to delete this movie?</p>

      <el-button @click="resetDeleteMovieDialog">Cancel</el-button>
      <el-button type="primary" @click="handleDeleteMovie">Delete</el-button>


    </el-dialog>

    <div class="movies-pagination">
      <el-pagination
          :current-page="current"
          :page-size="pageSize"
          :total="total"
          @current-change="handleCurrentPage"
      />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import router from "@/router";

export default {
  name: 'MovieManagementPage',
  data() {
    return {
      current: 1,
      pageSize: 10,
      total: 1,
      movieList: [],
      createMovieDialogVisible: false,
      editMovieDialogVisible: false,
      deleteMovieDialogVisible: false,
      searchText: '',
      createMovieForm: {
        title: '',
        director: '',
        cast: '',
        duration: ''
      },
      createMovieFormRules: {
        title: [{required: true, message: 'Please enter title', trigger: 'blur'}],
        director: [{required: true, message: 'Please enter director', trigger: 'blur'}],
        cast: [{required: true, message: 'Please enter cast', trigger: 'blur'}],
        duration: [{required: true, message: 'Please enter duration', trigger: 'blur'}]
      },
      edieMovieForm: {
        id: '',
        title: '',
        director: '',
        cast: '',
        duration: ''
      },
      edieMovieFormRules: {
        title: [{required: true, message: 'Please enter title', trigger: 'blur'}],
        director: [{required: true, message: 'Please enter director', trigger: 'blur'}],
        cast: [{required: true, message: 'Please enter cast', trigger: 'blur'}],
        duration: [{required: true, message: 'Please enter duration', trigger: 'blur'}]
      },
      deleteMovieForm: {
        movieId: ''
      },
    };
  },
  methods: {
    showCreateMovieDialog() {
      this.createMovieDialogVisible = true;
    },
    showEdieMovieDialog(movie) {
      this.edieMovieForm.id = movie.id
      this.edieMovieForm.title = movie.title
      this.edieMovieForm.director = movie.director
      this.edieMovieForm.cast = movie.cast
      this.edieMovieForm.duration = movie.duration

      this.editMovieDialogVisible = true;
    },
    showDeleteMovieDialog(movie) {
      this.deleteMovieForm.movieId = movie.id
      this.deleteMovieDialogVisible = true;
    },
    resetCreateMovieDialog() {
      this.$refs.createMovieForm.resetFields();
      this.createMovieForm = {
        title: '',
        director: '',
        cast: '',
        duration: ''
      };
      this.createMovieDialogVisible = false;
    },
    resetEditMovieDialog() {
      this.$refs.edieMovieForm.resetFields();
      this.edieMovieForm = {
        id: '',
        title: '',
        director: '',
        cast: '',
        duration: ''
      };
      this.editMovieDialogVisible = false;
    },
    resetDeleteMovieDialog() {
      this.createMovieForm = {
        movieId: ''
      };
      this.deleteMovieDialogVisible = false;
    },
    handleCreateMovie() {
      this.$refs.createMovieForm.validate(valid => {
        if (valid) {
          axios.post(`/admin/movie/add`, this.createMovieForm)
              .then((res) => {
                if (res.data.code === 200) {
                  this.$message.success("创建电影成功!")
                  this.list()
                } else {
                  this.$message.error("创建电影失败!")
                }
              })
              .catch((error) => {
                console.log(error)
              })
        } else {
          console.log('Form validation failed');
        }
      });
      this.resetCreateMovieDialog();
    },
    handleEditMovie() {
      axios.post(`/admin/movie/update`, this.edieMovieForm)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success("修改电影成功!")
              this.list()
            } else {
              this.$message.error("修改电影失败!")
            }
          })
          .catch((error) => {
            console.log(error)
          })
      this.resetEditMovieDialog()
    },
    handleDeleteMovie() {
      axios.get(`/admin/movie/delete?movieId=${this.deleteMovieForm.movieId}`)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success("删除电影成功!")
              this.list()
            } else {
              this.$message.error("删除电影失败!")
            }
          })
          .catch((error) => {
            console.log(error)
          })

      this.resetDeleteMovieDialog()
    },
    handleCurrentPage(current) {
      this.current = current
      this.list()
    },
    list() {
        if (this.searchText === '') {
          axios.get(`/admin/movieList?current=${this.current}&pageSize=${this.pageSize}`)
              .then((res) => {
                if (res.data.code === 200) {
                  this.movieList = res.data.data.records
                  this.total = res.data.data.total
                }
              })
              .catch((error) => {
                console.log(error)
              })
        } else {
          axios
              .post(`/admin/movie/get?title=${this.searchText}&current=${this.current}&pageSize=${this.pageSize}`)
              .then((res) => {
                if (res.data.code === 200) {
                  this.movieList = res.data.data.records;
                  this.total = res.data.data.total
                }
              })
              .catch((error) => {
                console.log(error)
              })
        }
    },
    goToDashboard() {
      router.push("/dashboard")
    },
    search() {
      this.current = 1;
      this.list()
    },

  },
  mounted() {
    this.list()
  }
};
</script>

<style scoped>
.movie-management {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.add-movie-button {
  padding: 10px 20px;
  background-color: #409eff;
  color: #fff;
  border: none;
}

.search-input {
  width: 200px;
  border-radius: 4px;
}

.search-button {
  padding: 10px 20px;
  background-color: #67c23a;
  color: #fff;
  border: none;
}


h1 {
  font-size: 24px;
  margin-bottom: 20px;
}

</style>
