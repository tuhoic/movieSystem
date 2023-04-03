<template>
  <div>
    <h1>Title: {{ movie.title }}</h1>
    <img v-if="movie.fullSizeCoverUrl" :src="`/api/${movie.fullSizeCoverUrl}`" :style="{ width: '300px', height: '450px' }" @error="setDefaultImage" alt="Movie Cover" />
    <p>Director: {{ movie.director }}</p>
    <p v-if="movie.cast">Cast:
      <span v-for="(actor, index) in movie.cast.split(',')" :key="index">
        <span v-if="index < 3">{{ actor }}{{ index === 2 ? '' : ', ' }}</span>
        <span v-else-if="index === 3" class="more-text" :style="showMore ? '' : 'display: none;'">{{ actor }}...</span>
        <span v-else class="more-text" :style="showMore ? '' : 'display: none;'">{{ actor }}{{ index === movie.cast.split(',').length - 1 ? '' : ', ' }}</span>
      </span>
      <button class="btn-more" @click="showMore = !showMore">
        {{ showMore ? 'Hide' : 'Show More' }}
      </button>
    </p>
    <p>Genre: {{ movie.genre }}</p>
    <p>Duration: {{ movie.duration }} mins</p>
    <p>Release Date: {{ movie.releaseDate }}</p>
    <p>Rating: {{ movie.rating }}/10</p>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      movie: {},
      showMore: false,
    }
  },
  methods: {
    setDefaultImage(event) {
      event.target.src = require('../assets/default_movie_cover.jpg')
    },
  },
  mounted() {
    const movieId = this.$route.params.id
    axios.get(`/movie/${movieId}`).then((response) => {
      this.movie = response.data.data
    })
  },
}
</script>

<style>
.more-text {
  display: none;
}
.btn-more {
  margin-left: 10px;
  font-size: 14px;
  color: blue;
  text-decoration: underline;
  cursor: pointer;
}
</style>
