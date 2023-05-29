<template>
<!--    <header-page></header-page>-->
<!--    <sidebar-page></sidebar-page>-->
  <div class="dashboard">
    <h1>Welcome to Dashboard</h1>
    <p>Here, you can manage users and movies.</p>
    <el-card>
      <template v-slot:header>
        <p>User Management</p>
      </template>
      <el-button type="primary" @click="goToUserManagement">Go to User Management</el-button>
    </el-card>
    <el-card>
      <template v-slot:header>
        <p>Movie Management</p>
      </template>
      <el-button type="primary" @click="goToMovieManagement">Go to Movie Management</el-button>
    </el-card>
    <el-card>
      <template v-slot:header>
        <p>Rating Management</p>
      </template>
      <el-button type="primary" @click="goToRatingManagement">Go to Rating Management</el-button>
    </el-card>
    <el-card>
      <template v-slot:header>
        <p>Comment Management</p>
      </template>
      <el-button type="primary" @click="goToCommentManagement">Go to Comment Management</el-button>
    </el-card>

  </div>
</template>

<script>
// import HeaderPage from "@/components/common/Header.vue";
// import SidebarPage from "@/components/common/Sidebar.vue";

export default {
  name: 'DashboardPage',
  // components: {SidebarPage, HeaderPage},
  methods: {
    goToUserManagement() {
      this.$router.push('/user-management');
    },
    goToMovieManagement() {
      this.$router.push('/movie-management');
    },
    goToRatingManagement() {
      this.$router.push('/rating-management');
    },
    goToCommentManagement() {
      this.$router.push('/comment-management');
    },
  }
};
</script>

<style scoped>
.dashboard {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

h1 {
  font-size: 24px;
  margin-bottom: 20px;
}

p {
  margin-bottom: 20px;
}
</style>
