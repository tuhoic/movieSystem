<template>
  <div>
    <h1 class="title">All Comments</h1>
    <table class="table is-fullwidth">
      <thead>
      <tr>
        <th>ID</th>
        <th>User ID</th>
        <th>Movie ID</th>
        <th>Content</th>
        <th>Action</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="comment in comments" :key="comment.id">
        <td>{{ comment.id }}</td>
        <td>{{ comment.user_id }}</td>
        <td>{{ comment.movie_id }}</td>
        <td>{{ comment.content }}</td>
        <td>
          <button class="button is-danger is-small" @click="removeComment(comment.id)">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { reactive } from 'vue'
import { getComments, deleteComment } from '@/api/comment'

export default {
  name: 'CommentList',
  setup() {
    const state = reactive({
      comments: []
    })

    const fetchData = async () => {
      const res = await getComments()
      if (res.data.code === 200) {
        state.comments = res.data.data
      }
    }

    const removeComment = async (id) => {
      const res = await deleteComment(id)
      if (res.data.code === 200) {
        fetchData()
      }
    }

    fetchData()

    return {
      comments: state.comments,
      removeComment
    }
  }
}
</script>

<style scoped>
.title {
  margin-top: 2rem;
  margin-bottom: 2rem;
  text-align: center;
}
</style>
