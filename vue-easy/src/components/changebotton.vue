<script setup>
import { ref } from 'vue';
import { useRoute } from 'vue-router';
const user = ref('');
const route = useRoute();
const path = user.value = route.params.travelerId;

</script>

<template>
  <div>

    <ul class="nav nav-tabs">
      <li class="nav-item">
        <router-link :to="'/blog/' + path" class="nav-link">About Me</router-link>
      </li>
      <li class="nav-item">
        <router-link :to="'/Photo/' + path" class="nav-link">Photo</router-link>
      </li>
      <li class="nav-item">
        <router-link :to="'/Article/' + path" class="nav-link">Article</router-link>
      </li>
    </ul>
  </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Caveat&display=swap');

.nav-link {
  font-family: 'Caveat', cursive;
  font-size: 30px;
  text-decoration: none;
  color: inherit;
}
</style>
