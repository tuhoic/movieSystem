<template>
  <div>
    <h1>Title: {{ movie.title }}</h1>
    <img v-if="movie.fullSizeCoverUrl" :src="`/api/${movie.fullSizeCoverUrl}`" :style="{ width: '300px', height: '450px' }" @error="setDefaultImage" alt="Movie Cover" />
    <p>Director: {{ movie.director }}</p>
    <p v-if="movie.cast">Cast:
      <span v-for="(actor, index) in displayedCast" :key="index" v-show="showMore || index < 3">
        {{ actor }}{{ index === displayedCast.length - 1 ? '' : ', ' }}
      </span>
      <button class="btn-more" @click="toggleShowMore">
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
    toggleShowMore() {
      this.showMore = !this.showMore
    },
  },
  computed: {
    displayedCast() {
      return this.movie.cast ? this.movie.cast.split(',') : []
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
.btn-more {
  margin-left: 10px;
  font-size: 14px;
  color: blue;
  text-decoration: underline;
  cursor: pointer;
}
</style>