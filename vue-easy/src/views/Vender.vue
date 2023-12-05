<script setup>
import axios from "axios";
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";

const venderId = ref(Number)
const route = useRoute()
const venderName = ref("")
const photos = ref([])
const loadData = async () => {
    const id = route.params.id
    venderId.value = id
    const API = `${import.meta.env.VITE_API_easystep}/vender/showSimpleVenderInfo/${id}`
    const response = await axios.get(API)
    venderName.value = response.data.venderName
    const URL = `${import.meta.env.VITE_API_easystep}/vender/showfivephoto/${id}`
    const responses = await axios.get(URL)
    photos.value = responses.data
}
// 秀最多五張照片
onMounted(() => {
    loadData()
})
const emit = defineEmits(["childClick"])
const clickHandler = venderId => {
    emit("childClick", venderId)
}
</script>
<template>
    <div class="p-4 p-md-5 mb-4 rounded text-body-emphasis bg-body-secondary">
        <p class="display-6">{{ venderName }}</p>

        <div class="container">
            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                <div class="card" style="width: 18%; height: 80%; margin-left: 15px;" v-for="value in photos">

                    <svg width="100%" height="100%" xmlns="http://www.w3.org/2000/svg">
                        <rect x="40" y="40" width="120" height="120" fill="white" />
                        <image :xlink:href="value" width="100%" height="100%" />
                    </svg>

                </div>
            </div>
        </div>


    </div>
    <div class="nav-scroller py-1 mb-3 border-bottom">
        <nav class="navbar navbar-expand-lg bg-light">
            <div class="nav-links">

                <span class="nav-space"></span>
                <router-link :to="'/vender/' + venderId + '/infomation'" class="nav-item nav-link link-body-emphasis">
                    工作相關資訊
                </router-link>
                <span class="nav-space"></span>
                <router-link :to="'/vender/' + venderId + '/photos'" class="nav-item nav-link link-body-emphasis">
                    環境圖片
                </router-link>
                <span class="nav-space"></span>
                <router-link :to="'/vender/' + venderId + '/feedbacks'" class="nav-item nav-link link-body-emphasis">
                    旅者回饋
                </router-link>
                <span class="nav-space"></span>
                <button class="button-like nav-item nav-link link-body-emphasis" @click="clickHandler(venderId)">
                    跟我聊聊
                </button>
            </div>
        </nav>
    </div>
    <p>
        <router-view />
    </p>
</template>
<style scoped>
.nav-space {
    margin-right: 64px;
}

.nav-links {
    display: flex;
    align-items: center;
}

.button-like {
    padding: 0;
    background-color: transparent;
    border: none;
    text-align: left;
    /* 文字置左 */
    font-size: 16px;
    cursor: pointer;
    max-width: 64px;
    /* 設置最大寬度 */
    color: blue;
}
</style>