<template>
  <div class="app">
    <top-bar></top-bar>
    <router-view></router-view>
  </div>
</template>

<script>
import TopBar from "@/components/Header.vue";

export default {
  name: 'App',
  components: {
    TopBar,
  },
}
</script>

<style>
.app {
  height: 100%;
}
</style>
