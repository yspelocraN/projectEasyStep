<script setup>
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.js'
import 'bootstrap-icons/font/bootstrap-icons.css'
import Navbar from './components/Navbar.vue'

import ChatRoom from '@/components/ChatRoom.vue'
import ChatVenderRoom from '@/components/ChatVenderRoom.vue'
import TravelersChat from '@//components/TravelersChat.vue'
import { ref } from 'vue';



const id = ref()

const showRoom = ref(false);
const toggleChatRoomV = (travelerId) => {
  id.value = travelerId
  showRoom.value = true;
};
const closeRoom = () => {
  showRoom.value = false;
};

const showChatRoom = ref(false);
const toggleChatRoom = (venderId) => {
  id.value = venderId
  showChatRoom.value = true;
};
const closeChatRoom = () => {
  showChatRoom.value = false;
};

const showTRoom = ref(false);
const toggleChatRoomT = (travelerId) => {
  id.value = travelerId
  showTRoom.value = true;
};
const closeTRoom = () => {
  showTRoom.value = false;
}


</script>

<template>
  <div class="container">

    <Navbar @childVenderClick="toggleChatRoomV"></Navbar>
    <router-view @childClick="toggleChatRoom" @childTraClick="toggleChatRoomT"></router-view>

    <!-- FOOTER-->
    <div class="container ">
      <footer class="d-flex flex-wrap justify-content-between align-items-center py-3 my-4 border-top">
        <p class="col-md-4 mb-0 text-body-secondary">&copy; 2023 Company, Inc</p>

        <RouterLink to="/"><img src="./assets/about_version.png" alt="" id="logo"></RouterLink>

        <ul class="nav col-md-4 justify-content-end">
          <li class="nav-item"><a href="#" class="nav-link px-2 text-body-secondary">FAQs</a></li>
          <li class="nav-item"><a href="#" class="nav-link px-2 text-body-secondary">About</a></li>
        </ul>
      </footer>
    </div>
    <div>
      <TravelersChat v-if="showTRoom" :travelerId="id" @close="closeTRoom" />
      <ChatVenderRoom v-if="showRoom" :travelerId="id" @close="closeRoom" />
      <ChatRoom v-if="showChatRoom" :venderId="id" @close="closeChatRoom" />
    </div>

  </div>
</template>

<style scoped>
#logo {
  width: 50%;
  height: 50%;
  object-fit: cover;
}
</style>
