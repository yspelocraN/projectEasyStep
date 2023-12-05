<script setup>
import { ref, onMounted } from "vue";
import Swal from 'sweetalert2/dist/sweetalert2.js'
import { useRouter, useRoute } from 'vue-router'
import axios from "axios";
import Score from '@/models/Score.js'

const router = useRouter();
const route = useRoute();
const score = ref(Score);
const venderName = ref("");
// 確認是否為登入狀態
const logincheck = async () => {
    const URL = `${import.meta.env.VITE_API_easystep}/traveler/checklogin`
    const response = await axios.get(URL, { withCredentials: true })
    if (response.data.success) {
    } else {
        await Swal.fire('請先登入')
        await router.push("/Traveler/TravelerLog")
    }
}
// 傳出廠商名字
const showName = async () => {
    const URL = `${import.meta.env.VITE_API_easystep}/vender/findName/${route.params.id}`
    const response = await axios.get(URL)
    venderName.value = response.data
}

// 送出評分
const sendto = async () => {
    await logincheck()
    score.value.score = 5;
    const API = `${import.meta.env.VITE_API_easystep}/users/addFeedback/${route.params.id}`
    const response = await axios.post(API, score.value, { withCredentials: true })
    if (response.data == 'Y') {
        await Swal.fire('新增成功')
        await router.push(`/vender/${route.params.id}/feedbacks`)
    }

}

onMounted(() => {
    logincheck()
    showName()
    score.value = ref(Score)
})
</script>

<template>
    <!-- @submit.prevent 不會刷新畫面 -->
    <form @submit.prevent="sendto">
        <div class="card">
            <div class="card-header"><span>給 {{ venderName }} 的意見</span></div>
            <br>
            <div class="card-header"><span>建議與意見</span></div>
            <div class="card-body">
                <div class="form-floating">
                    <textarea class="form-control" required v-model="score.feedback"></textarea>
                </div>
            </div>
            <!-- <div class="card-header"><span>評分</span></div>
            <div class="card-body">

                <input type="text" required v-model="score.score">

            </div> -->
        </div>
        <br>
        <button class="btn btn-outline-primary" type="submit">送出</button>
    </form>
</template>
<style scoped></style>