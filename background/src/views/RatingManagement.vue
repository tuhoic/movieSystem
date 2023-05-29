<template>
  <el-button type="primary" @click="goToDashboard">Go to Dashboard</el-button>
  <div class="rating-management">
    <h1>Rating Management</h1>

    <div class="container">
      <el-input v-model="searchText" placeholder="请输入关键字" @keyup.enter="search" class="search-input"></el-input>
      <el-button type="primary" @click="search" class="search-button">搜索</el-button>
    </div>

    <el-table :data="ratingList" style="width: 100%" stripe>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="username" label="Username"></el-table-column>
      <el-table-column prop="movieName" label="MovieName"></el-table-column>
      <el-table-column prop="rating" label="Rating"></el-table-column>
      <el-table-column prop="createTime" label="CreateTime" width="120">
      </el-table-column>
      <el-table-column label="Actions" width="180">
        <template v-slot="scope">
          <el-button type="danger" size="small" @click="showDeleteRatingDialog(scope.row)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="Delete Rating" v-model="deleteRatingDialogVisible" width="30%" @close="resetDeleteRatingDialog">
      <p>Are you sure you want to delete this rating?</p>
      <el-button @click="resetDeleteRatingDialog">Cancel</el-button>
      <el-button type="primary" @click="handleDeleteRating">Delete</el-button>
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
  name: 'RatingManagementPage',
  data() {
    return {
      current: 1,
      pageSize: 10,
      total: 1,
      ratingList: [],
      deleteRatingDialogVisible: false,
      searchText: '',
      deleteRatingForm: {
        ratingId: '',
      },
    };
  },
  methods: {
    showDeleteRatingDialog(rating) {
      this.deleteRatingForm.ratingId = rating.id
      this.deleteRatingDialogVisible = true;
    },
    resetDeleteRatingDialog() {
      this.deleteRatingForm.RatingId = '';

    },

    handleDeleteRating() {
      console.log(this.deleteRatingForm.ratingId)
      axios.get(`/admin/rating/delete?ratingId=${this.deleteRatingForm.ratingId}`)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success("删除用户成功!")
              this.list()
            } else {
              this.$message.error("删除用户失败!")
            }
          })
          .catch((error) => {
            console.log(error)
          })
      this.resetDeleteRatingDialog()
    },
    setDefaultHeadPortrait(event) {
      event.target.src = require('@/assets/images/headPortrait.jpg');
    },
    handleCurrentPage(current) {
      this.current = current;
      this.list()
    },
    list() {
      if (this.searchText === '') {
        axios
            .get(`/admin/ratingList?current=${this.current}&pageSize=${this.pageSize}`)
            .then((res) => {
              if (res.data.code === 200) {
                this.ratingList = res.data.data.records;
              }
            })
            .catch((error) => {
              console.log(error)
            })
      } else {
        axios
            .post(`/admin/rating/get?username=${this.searchText}&current=${this.current}&pageSize=${this.pageSize}`)
            .then((res) => {
              if (res.data.code === 200) {
                this.ratingList = res.data.data.records;
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
      this.current = 1
      this.list()
    },

  },
  mounted() {
    this.list()
  }
};

</script>

<style scoped>
.rating-management {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

h1 {
  font-size: 24px;
  margin-bottom: 20px;
}

.container {
  display: flex;
  align-items: center;
  gap: 10px;
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

</style>
