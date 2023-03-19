<template>
  <div>
    <h1>Movie List</h1>
    <ul>
      <li v-for="movie in movies" :key="movie.id">
        {{ movie.title }}
      </li>
    </ul>
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
    ...mapActions('movies', ['fetchMovies'])
  }
}
</script>
