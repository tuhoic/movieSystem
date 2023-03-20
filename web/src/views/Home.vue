<template>
  <div>
    <h1>Movie List</h1>
    <ul>
      <li v-for="movie in movies" :key="movie.id">
        <div>
          {{ movie.title }}
          {{ movie.director }}
          {{ movie.cast }}
          {{ movie.duration }}
          {{ movie.genre }}
          {{ movie.release_date }}
          <img src= "`${movie.coverUrl}`">
        </div>
      </li>
    </ul>
    <button @click="loadNextPage">Load more movies</button>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import store from "@/store";

export default {
  name: 'HomePage',
  data() {
    return {
      page: {
        current: 1,
        size: 20
      }
    }
  },
  computed: {
    ...mapGetters('movies', ['movies'])
  },
  mounted() {
    this.fetchMovies(this.page)
  },
  methods: {
    ...mapActions('movies', ['fetchMovies']),
    loadNextPage() {
      this.page.current ++
      store.dispatch("movies/fetchMovies", this.page)
    }
  }
}
</script>
