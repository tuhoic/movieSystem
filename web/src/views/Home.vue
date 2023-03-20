<template>
  <div>
    <h1>Movie List</h1>
    <ul class="movie-list">
      <li v-for="movie in displayedMovies" :key="movie.id" class="movie-item">
        <div>
          <router-link :to="{ name: 'MovieDetails', params: { id: movie.id }}">
            <img :src="movie.coverUrl" alt="movie cover">
          </router-link>
          <div class="movie-details">
            <p>{{ movie.title }}</p>
            <p>{{ movie.director }}</p>
            <p>{{ movie.cast }}</p>
            <p>{{ movie.duration }}</p>
            <p>{{ movie.genre }}</p>
            <p>{{ movie.release_date }}</p>
          </div>
        </div>
      </li>
    </ul>
    <div class="pagination">
      <button @click="loadPreviousPage" :disabled="isFirstPage">Previous</button>
      <span>Page {{ currentPage }} of {{ totalPages }}</span>
      <button @click="loadNextPage" :disabled="isLastPage">Next</button>
    </div>
  </div>
</template>

<script>
export default {
  name:"HomePage",
  data() {
    return {
      movies: [], // 所有电影
      currentPage: 1, // 当前页码
      pageSize: 10 // 每页显示的电影数量
    };
  },
  computed: {
    displayedMovies() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.movies.slice(startIndex, endIndex);
    },
    totalPages() {
      return Math.ceil(this.movies.length / this.pageSize);
    },
    isFirstPage() {
      return this.currentPage === 1;
    },
    isLastPage() {
      return this.currentPage === this.totalPages;
    }
  },
  methods: {
    loadPreviousPage() {
      this.currentPage -= 1;
    },
    loadNextPage() {
      this.currentPage += 1;
    }
  }
};
</script>

<style>
.movie-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.movie-item {
  width: 45%;
  margin-bottom: 20px;
}

.movie-details {
  margin-top: 10px;
}

.pagination {
  margin-top: 20px;
}
</style>
