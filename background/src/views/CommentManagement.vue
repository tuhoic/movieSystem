<template>
  <el-button type="primary" @click="goToDashboard">Go to Dashboard</el-button>
  <div class="comment-management">
    <h1>Comment Management</h1>

    <div class="container">
      <el-input v-model="searchText" placeholder="请输入关键字" @keyup.enter="search" class="search-input"></el-input>
      <el-button type="primary" @click="search" class="search-button">搜索</el-button>
    </div>

    <el-table :data="commentList" style="width: 100%" stripe>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="username" label="Username"></el-table-column>
      <el-table-column prop="movieName" label="MovieName"></el-table-column>
      <el-table-column prop="content" label="MovieName"></el-table-column>
      <el-table-column prop="createTime" label="CreateTime" width="120">
      </el-table-column>
      <el-table-column label="Actions" width="180">
        <template v-slot="scope">
          <el-button type="danger" size="small" @click="showDeleteCommentDialog(scope.row)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="Delete Comment" v-model="deleteCommentDialogVisible" width="30%"
               @close="resetDeleteCommentDialog">
      <p>Are you sure you want to delete this comment?</p>
      <el-button @click="resetDeleteCommentDialog">Cancel</el-button>
      <el-button type="primary" @click="handleDeleteComment">Delete</el-button>
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
  name: 'CommentManagementPage',
  data() {
    return {
      current: 1,
      pageSize: 10,
      total: 1,
      commentList: [],
      deleteCommentDialogVisible: false,
      searchText: '',
      deleteCommentForm: {
        commentId: '',
      },
    };
  },
  methods: {
    showDeleteCommentDialog(comment) {
      this.deleteCommentForm.commentId = comment.id
    },
    resetDeleteCommentDialog() {
      this.deleteCommentForm.commentId = '';

    },

    handleDeleteComment() {
      console.log(this.deleteCommentForm.commentId)
      axios.get(`/admin/comment/delete?commentId=${this.deleteCommentForm.commentId}`)
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
      this.resetDeleteCommentDialog()
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
        axios.get(`/admin/commentList?current=${this.current}&pageSize=${this.pageSize}`)
            .then((res) => {
              if (res.data.code === 200) {
                this.commentList = res.data.data.records;
                this.total = res.data.data.total;
              }
            })
            .catch((error) => {
              console.log(error);
              // 在这里处理错误，并提供适当的反馈
            });
      } else {
        axios.post(`/admin/comment/get?username=${this.searchText}&current=${this.current}&pageSize=${this.pageSize}`)
            .then((res) => {
              if (res.data.code === 200) {
                this.commentList = res.data.data.records;
                this.total = res.data.data.total;
              }
            })
            .catch((error) => {
              console.log(error);
              // 在这里处理错误，并提供适当的反馈
            });
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

.comment-management {
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
