<template>
  <div class="chat-room">
    <div class="chat-header">
      <h5>{{ traName }}</h5>
      <button @click="closeChatRoom">關閉</button>
    </div>
    <div class="chat-messages">
      <div v-for="{ travelerAcct, sender, msg } in messages" class="message">
        <div v-if="travelerAcct == sender" class="text-start">{{ msg }}</div>
        <div v-else class="text-end">{{ msg }}</div>
      </div>
    </div>
    <div class="chat-input">
      <input v-model="newMessage" @keyup.enter="sendMessage" placeholder="輸入訊息..." />
    </div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import Swal from 'sweetalert2/dist/sweetalert2.js'
import { useRouter } from 'vue-router'


const router = useRouter();
const traName = ref('')
const messages = ref([]);
const newMessage = ref('');
const check = ref(true)
const props = defineProps({
  travelerId: Number
})

// 看有沒有登入
const logincheck = async () => {
  const URL = `${import.meta.env.VITE_API_easystep}/vender/checklogin`
  const response = await axios.get(URL, { withCredentials: true })
  if (response.data.success) {
  } else {
    await Swal.fire('請先登入')
    await router.push("/Vender/VenderLog")
    check.value = false
    closeChatRoom()
  }
}
// 送出訊息 廠商的
const sendMessage = async () => {
  if (newMessage.value) {
    const API = `${import.meta.env.VITE_API_easystep}/chatroom/startChatToTra/${props.travelerId}`
    await axios.post(API, { msg: newMessage.value }, { withCredentials: true })
    await findAllMessage()
    newMessage.value = ''
  }
};
// 找之前所有聊天內容
const findAllMessage = async () => {
  const API = `${import.meta.env.VITE_API_easystep}/chatroom/showVenchats/${props.travelerId}`
  const response = await axios.get(API, { withCredentials: true })
  messages.value = response.data
}
// 找 旅者名字
const findvendername = async () => {
  const API = `${import.meta.env.VITE_API_easystep}/user/findName/${props.travelerId}`
  const response = await axios.get(API)
  traName.value = response.data
}
onMounted(async () => {
  await logincheck()
  if (check.value) {
    await findvendername()
    await findAllMessage()
  }
})
const emit = defineEmits(['close'])
const closeChatRoom = () => {
  clearInterval(time)
  emit('close');
};
const time = setInterval(() => {
  findAllMessage()
}, 2000)

</script>

<style scoped>
.chat-room {
  border: 1px solid #ccc;
  width: 300px;
  position: fixed;
  bottom: 20px;
  right: 20px;
  background-color: white;
}

.chat-header {
  background-color: #005AB5;
  color: white;
  padding: 10px;
  display: flex;
  justify-content: space-between;
}

.chat-messages {
  padding: 10px;
  max-height: 200px;
  overflow-y: auto;
}

.chat-input {
  padding: 10px;
  display: flex;
  justify-content: space-between;
  border-top: 1px solid #ccc;
}

input {
  width: 100%;
  padding: 5px;
}
</style>