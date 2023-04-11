<template>
  <top-bar></top-bar>
  <div class="movies">
    <div class="movies-list">
      <div v-for="movie in movies" :key="movie.id" class="movies-list-item">
        <router-link :to="'/movie/' + movie.id">
          <img :src="`/api/` + movie.fullSizeCoverUrl" @error=setDefaultImage alt="movie cover" />
          <p style="color: #333333">{{ movie.title }}</p>
        </router-link>
      </div>
    </div>
    <div class="movies-pagination">
      <el-pagination
          :current-page="currentPage"
          :page-size="pageSize"
          :total="total"
          @current-change="handleCurrentPage"
      />
    </div>
  </div>
</template>

<script>
import { ElPagination} from "element-plus";
import TopBar from "@/components/Header.vue";
import {mapActions, mapGetters} from "vuex";

export default {
  name: "HomePage",
  components: {
    TopBar,
    ElPagination: ElPagination,
  },
  computed: {
    ...mapGetters("movies", ["movies", "currentPage", "pageSize", "total"])
  },
  methods: {
    ...mapActions("movies", ["fetchMovies", "handleCurrentChange", "searchMovies"]),
    handleCurrentPage(currentPage) {
      this.handleCurrentChange(currentPage)
      this.fetchMovies();
    },
    setDefaultImage(event) {
      event.target.src = require('../assets/default_movie_cover.jpg')
    }
  },
  mounted() {
    this.fetchMovies();
  }
};
</script>

<style scoped>
.movies {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 30px;
}

.movies-list {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 10px;
}

.movies-list-item {
  text-align: center;
  height: 600px;
}

.movies-list-item img {
  width: 100%;
  height: 90%;
  object-fit: cover;
}

.movies-list-item p {
  margin-top: 10px;
}

</style>