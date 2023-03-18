<template>
  <div>
    <h1>电影管理</h1>
    <div class="mb-2">
      <router-link to="/admin/movie/add" class="btn btn-primary">添加电影</router-link>
    </div>
    <table class="table table-striped">
      <thead>
      <tr>
        <th>ID</th>
        <th>名称</th>
        <th>导演</th>
        <th>主演</th>
        <th>类型</th>
        <th>上映时间</th>
        <th>评分</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(movie, index) in movies" :key="index">
        <td>{{ movie.id }}</td>
        <td>{{ movie.name }}</td>
        <td>{{ movie.director }}</td>
        <td>{{ movie.actor }}</td>
        <td>{{ movie.category.name }}</td>
        <td>{{ movie.releaseDate }}</td>
        <td>{{ movie.rating }}</td>
        <td>
          <router-link :to="{ name: 'AdminEditMovie', params: { id: movie.id } }" class="btn btn-primary btn-sm">编辑</router-link>
          <button class="btn btn-danger btn-sm" @click="removeMovie(movie.id)">删除</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { reactive, onMounted } from 'vue'
import { getMovieList, deleteMovie } from '@/api/movie'

export default {
  setup() {
    const state = reactive({
      movies: [],
    })

    const loadMovies = async () => {
      state.movies = await getMovieList()
    }

    const removeMovieById = async (id) => {
      await deleteMovie(id)
      await loadMovies()
    }

    onMounted(() => {
      loadMovies()
    })

    return {
      movies: state.movies,
      removeMovie: removeMovieById,
    }
  },
}
</script>
