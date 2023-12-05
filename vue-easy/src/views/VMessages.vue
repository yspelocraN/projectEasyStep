<script setup>
import axios from "axios";
import { ref, onMounted, watch } from "vue";
import Swal from 'sweetalert2/dist/sweetalert2.js'
import { useRouter } from 'vue-router'


const router = useRouter();
// 確認登入狀態
const logincheck = async () => {
    const URL = `${import.meta.env.VITE_API_easystep}/vender/checklogin`
    const response = await axios.get(URL, { withCredentials: true })
    if (response.data.success) {
    } else {
        await Swal.fire('請先登入')
        await router.push("/Vender/VenderLog")
    }
}

// 載入聊過天的 旅者

const talkMans = ref([])

const onceTalk = async () => {
    const URL = `${import.meta.env.VITE_API_easystep}/vender/checklogin`
    const response = await axios.get(URL, { withCredentials: true })
    if (response.data.success) {

        const API = `${import.meta.env.VITE_API_easystep}/chatroom/findtras`
        const responses = await axios.get(API, { withCredentials: true })
        talkMans.value = responses.data
    }
}

// 找之前所有聊天內容
const other = ref('旅者')
const messages = ref([])
const trid = ref()
const findAllMessage = async (travelerId, travelerName) => {
    trid.value = travelerId
    const API = `${import.meta.env.VITE_API_easystep}/chatroom/showVenchats/${travelerId}`
    const response = await axios.get(API, { withCredentials: true })
    messages.value = response.data
    other.value = travelerName
}

// 送出訊息 廠商的
const newMessage = ref("")
const sendMessage = async () => {
    if (newMessage.value) {
        const API = `${import.meta.env.VITE_API_easystep}/chatroom/startChatToTra/${trid.value}`
        await axios.post(API, { msg: newMessage.value }, { withCredentials: true })
        await findAllMessage(trid.value, other.value)
        newMessage.value = ''
    }
};
onMounted(() => {
    logincheck()
    onceTalk()
})

axios
</script>
<template>
    <div class="row g-5">
        <div class="col-md-4">
            <div class="card" style="height: 250px;">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">曾聊過的旅者</li>
                </ul>
                <ul class="list-group list-group-flush" v-for="{ travelerId, travelerName } in talkMans ">
                    <li class="list-group-item" @click="findAllMessage(travelerId, travelerName)">{{ travelerName }}</li>
                </ul>
            </div>
        </div>
        <div class="col-md-8">
            <div class="card" style="max-height: 500px overflow: auto">
                <div class="card-header"><span>{{ other }}</span></div>
                <div class="card-body" v-for="{ travelerAcct, sender, msg } in messages">
                    <div v-if="travelerAcct == sender" class="text-start">{{ msg }}</div>
                    <div v-else class="text-end">{{ msg }}</div>
                </div>
            </div>
            <br>
            <div class="card">

                <input v-model="newMessage" @keyup.enter="sendMessage" placeholder="輸入訊息..." />

            </div>
        </div>
    </div>
</template>
<style scoped></style>