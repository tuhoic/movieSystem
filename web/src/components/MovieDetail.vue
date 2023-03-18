<template>
  <div>
    <h1>{{ movie.title }}</h1>
    <p>{{ movie.description }}</p>
    <p>导演：{{ movie.director }}</p>
    <p>主演：{{ movie.stars }}</p>
    <p>上映日期：{{ movie.releaseDate }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'MovieDetail',
  data() {
    return {
      movie: {}
    }
  },
  mounted() {
    this.loadMovie();
  },
  methods: {
    loadMovie() {
      const id = this.$route.params.id;
      axios.get('/api/movies/' + id)
          .then(response => {
            this.movie = response.data;
          })
          .catch(error => {
            console.error(error);
          });
    }
  }
}
</script>
