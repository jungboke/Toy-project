<template>
  <div id="app">
    <ul class="nav">
      <li>
        <router-link to="/">Home</router-link>
      </li>
    </ul>
    <router-link to="/"><h3 class="text-muted">BAEK SHOP</h3></router-link>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
#app {
  border-bottom: 1px solid rgb(229, 229, 229);
  font-weight: 400;
  margin-bottom: 20px;
}
.nav {
  list-style: none;
  display: flex;
}
.text-muted {
  padding-bottom: 20px;
  font-weight: 400;
}
</style>
