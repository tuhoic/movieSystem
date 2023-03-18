<template>
  <div>
    <h1>Welcome to Movie System</h1>
    <p>
      <router-link to="/login">login</router-link>
    </p>
    <p>
      <router-link to="/register">register</router-link>
    </p>
    <p>
      <router-link to="/movies">Browse Movies</router-link>
    </p>
  </div>
</template>

<script>
export default {
  name: 'HomePage',
}
</script>

<style>
/* 样式省略 */
</style>
