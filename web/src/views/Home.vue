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
import axios from "axios";
import { ElPagination} from "element-plus";
import TopBar from "@/components/Header.vue";
import {mapActions, mapGetters} from "vuex";

export default {
  name: "HomePage",
  components: {
    TopBar,
    ElPagination: ElPagination,
  },
  data() {
    return {
      // movies: [],
      // currentPage: 1,
      // pageSize: 20,
      // total: 0,
      defaultImage: "api/default.jpg",
    };
  },
  computed: {
    ...mapGetters("movies", ["movies", "currentPage", "pageSize", "total"])
  },
  methods: {
    ...mapActions("movies", ["fetchMovies", "handleCurrentChange", "searchMovies"]),
    fetchData() {
      axios.get("/movie/list", {
            params: {
              current: this.currentPage,
              size: this.pageSize,
            },
          })
          .then((response) => {
            this.movies = response.data.data.records;
            this.total = response.data.data.total;
          })
          .catch((error) => {
            console.error(error);
          });
    },
    handleCurrentPage(currentPage) {
      this.handleCurrentChange(currentPage)
      this.fetchMovies();
      // this.fetchData();
    },
    setDefaultImage(event) {
      event.target.src = this.defaultImage
    }
  },
  created() {
    // this.searchMovies()
    this.fetchMovies();
    // this.fetchData();
  },
};
</script>

<style scoped>
.movies {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.movies-list {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 10px;
}

.movies-list-item {
  text-align: center;
  height: 500px;
}

.movies-list-item img {
  width: 100%;
  height: 80%;
  object-fit: cover;
}

.movies-list-item p {
  margin-top: 10px;
}

</style>
