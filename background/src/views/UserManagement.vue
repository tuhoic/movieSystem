<template>
  <el-button type="primary" @click="goToDashboard">Go to Dashboard</el-button>
  <div class="user-management">
    <h1>User Management</h1>
    <div class="container">
      <el-button type="primary" class="add-user-button" @click="showCreateUserDialog">Add User</el-button>

      <el-input v-model="searchText" placeholder="请输入关键字" @keyup.enter="search" class="search-input"></el-input>

      <el-button type="primary" @click="search" class="search-button">搜索</el-button>
    </div>

    <el-table :data="userList" style="width: 100%" stripe>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="username" label="Username"></el-table-column>
      <el-table-column prop="email" label="Email"></el-table-column>
      <el-table-column prop="headPortrait" label="Head Portrait" width="120">
        <template v-slot="scope">
          <img :src="`/api/` + scope.row.headPortrait" alt="Head Portrait" @error="setDefaultHeadPortrait"
               class="user-avatar">
        </template>
      </el-table-column>
      <el-table-column label="Actions" width="180">
        <template v-slot="scope">
          <el-button type="primary" size="small" @click="showEditUserDialog(scope.row)">Edit</el-button>
          <el-button type="danger" size="small" @click="showDeleteUserDialog(scope.row)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="Create User" v-model="createUserDialogVisible" width="30%" @close="resetCreateUserDialog">
      <el-form ref="createUserForm" :model="createUserForm" :rules="createUserFormRules" label-width="100px">
        <el-form-item label="Username" prop="username">
          <el-input v-model="createUserForm.username"></el-input>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input type="password" v-model="createUserForm.password"></el-input>
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input v-model="createUserForm.email"></el-input>
        </el-form-item>
        <el-form-item label="HeadPortrait" prop="headPortrait">
          <el-input v-model="createUserForm.headPortrait"></el-input>
        </el-form-item>
      </el-form>
      <el-button @click="resetCreateUserDialog">Cancel</el-button>
      <el-button type="primary" @click="handleCreateUser">Create</el-button>
    </el-dialog>

    <el-dialog title="Edit User" v-model="editUserDialogVisible" width="30%" @close="resetEditUserDialog">
      <el-form ref="editUserForm" :model="editUserForm" :rules="editUserFormRules" label-width="100px">
        <el-form-item label="Username" prop="username">
          <el-input v-model="editUserForm.username"></el-input>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input type="password" v-model="editUserForm.password"></el-input>
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input v-model="editUserForm.email"></el-input>
        </el-form-item>
        <el-form-item label="HeadPortrait" prop="headPortrait">
          <el-input v-model="editUserForm.headPortrait"></el-input>
        </el-form-item>
      </el-form>
      <el-button @click="resetEditUserDialog">Cancel</el-button>
      <el-button type="primary" @click="handleEditUser">Edit</el-button>
    </el-dialog>

    <el-dialog title="Delete User" v-model="deleteUserDialogVisible" width="30%" @close="resetDeleteUserDialog">
      <p>Are you sure you want to delete this user?</p>
      <el-button @click="resetDeleteUserDialog">Cancel</el-button>
      <el-button type="primary" @click="handleDeleteUser">Delete</el-button>
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
  name: 'UserManagementPage',
  data() {
    return {
      current: 1,
      pageSize: 10,
      total: 1,
      userList: [],
      createUserDialogVisible: false,
      editUserDialogVisible: false,
      deleteUserDialogVisible: false,
      searchText: '',
      createUserForm: {
        username: '',
        password: '',
        email: '',
        headPortrait: ''
      },
      createUserFormRules: {
        username: [
          {required: true, message: 'Please enter username', trigger: 'blur'}
        ],
        password: [
          {required: true, message: 'Please enter password', trigger: 'blur'}
        ],
        email: [
          {required: true, message: 'Please enter email', trigger: 'blur'},
          {type: 'email', message: 'Invalid email format', trigger: ['blur', 'change']}
        ],
        headPortrait: [
          {required: true, message: 'Please enter head portrait URL', trigger: 'blur'}
        ]
      },
      editUserForm: {
        id: '',
        username: '',
        password: '',
        email: '',
        headPortrait: ''
      },
      editUserFormRules: {
        username: [
          {required: true, message: 'Please enter username', trigger: 'blur'}
        ],
        password: [
          {required: true, message: 'Please enter password', trigger: 'blur'}
        ],
        email: [
          {required: true, message: 'Please enter email', trigger: 'blur'},
          {type: 'email', message: 'Invalid email format', trigger: ['blur', 'change']}
        ],
        headPortrait: [
          {required: true, message: 'Please enter head portrait URL', trigger: 'blur'}
        ]
      },
      deleteUserForm: {
        userId: '',
      },
    };
  },
  methods: {
    showCreateUserDialog() {
      this.createUserDialogVisible = true;
    },
    showEditUserDialog(user) {
      this.editUserForm.id = user.id
      this.editUserForm.username = user.username
      this.editUserForm.password = user.password
      this.editUserForm.email = user.email
      this.editUserForm.headPortrait = user.headPortrait

      this.editUserDialogVisible = true;
    },
    showDeleteUserDialog(user) {
      this.deleteUserForm.userId = user.id
      this.deleteUserDialogVisible = true;
    },
    resetCreateUserDialog() {
      this.$refs.createUserForm.resetFields();
      this.createUserForm = {
        username: '',
        password: '',
        email: '',
        headPortrait: ''
      };
      this.createUserDialogVisible = false;
    },
    resetEditUserDialog() {
      this.$refs.editUserForm.resetFields();
      this.editUserForm = {
        id: '',
        username: '',
        password: '',
        email: '',
        headPortrait: ''
      };
      this.editUserDialogVisible = false;
    },
    resetDeleteUserDialog() {
      this.deleteUserForm.userId = '';
      this.createUserDialogVisible = false;

    },
    handleCreateUser() {
      this.$refs.createUserForm.validate((valid) => {
        if (valid) {
          axios.post(`/admin/user/add`, this.createUserForm)
              .then((res) => {
                if (res.data.code === 200) {
                  this.$message.success("创建用户成功!")
                  this.list()
                } else {
                  this.$message.error("创建用户失败!")
                }
              })
              .catch((error) => {
                console.log(error)
              })
        } else {
          console.log('Form validation failed');
          return false;
        }
      })
      this.resetCreateUserDialog()
    },
    handleEditUser() {
      axios.post(`/admin/user/update`, this.editUserForm)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success("修改用户成功!")
              this.list()
            } else {
              this.$message.error("修改用户失败!")
            }
          })
          .catch((error) => {
            console.log(error)
          })
      this.resetEditUserDialog()
    },
    handleDeleteUser() {
      axios.get(`/admin/user/delete?userId=${this.deleteUserForm.userId}`)
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

      this.resetDeleteUserDialog()
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
            .get(`/admin/userList?current=${this.current}&pageSize=${this.pageSize}`)
            .then((res) => {
              if (res.data.code === 200) {
                this.userList = res.data.data.records;
                this.total = res.data.data.total
              }
            })
            .catch((error) => {
              console.log(error)
            })
      } else {
        axios
            .post(`/admin/user/get?username=${this.searchText}&current=${this.current}&pageSize=${this.pageSize}`)
            .then((res) => {
              if (res.data.code === 200) {
                this.userList = res.data.data.records;
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
.user-management {
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

.add-user-button {
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

.user-avatar {
  width: 50px;
  height: 50px;
  object-fit: cover;
  border-radius: 50%;
}
</style>
