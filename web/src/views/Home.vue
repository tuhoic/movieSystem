<template>
  <div>
    <h1>Movie List</h1>
    <ul>
      <li v-for="movie in movies" :key="movie.id">
        {{ movie.title }}
      </li>
    </ul>
    <button @click="loadNextPage">Load more movies</button>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'HomePage',
  computed: {
    ...mapGetters('movies', ['movies'])
  },
  mounted() {
    this.fetchMovies()
  },
  methods: {
    ...mapActions('movies', ['fetchMovies']),
    loadNextPage() {
      const currentPage = this.movies.length / 10 + 1 // 每页 10 条数据
      this.fetchMovies(currentPage)
    }
  }
}
</script>
