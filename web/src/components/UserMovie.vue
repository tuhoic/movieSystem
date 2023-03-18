<template>
  <div>
    <h1 class="title">Movies</h1>
    <div class="columns is-multiline">
      <div class="column is-4" v-for="movie in movies" :key="movie.id">
        <div class="card">
          <div class="card-image">
            <figure class="image is-4by3">
              <img :src="movie.coverUrl" alt="movie cover">
            </figure>
          </div>
          <div class="card-content">
            <p class="title is-4">{{ movie.title }}</p>
            <p class="subtitle is-6">{{ movie.releaseYear }}</p>
            <div class="content">
              <p>{{ movie.description }}</p>
            </div>
          </div>
          <footer class="card-footer">
            <a href="#" class="card-footer-item" @click.prevent="deleteMovie(movie.id)">Delete</a>
          </footer>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { getUserMovies, deleteMovie } from '../api/movie'

export default {
  setup() {
    const movies = ref([])

    const loadUserMovies = async () => {
      movies.value = await getUserMovies()
    }

    const deleteUserMovie = async (movieId) => {
      await deleteMovie(movieId)
      await loadUserMovies()
    }

    onMounted(loadUserMovies)

    return {
      movies,
      deleteUserMovie,
    }
  },
}
</script>

<style scoped>
.title {
  margin-bottom: 2rem;
}
</style>
