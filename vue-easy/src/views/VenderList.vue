<script setup>
import axios from "axios";
import { onMounted, ref } from "vue";

const emit = defineEmits(["childClick"])
const clickHandler = venderId => {
    emit("childClick", venderId)
}

//計算文章長度給省略號
const truncateText = (text, maxLength) => {
    if (text.length > maxLength) {
        return text.slice(0, maxLength) + '...';
    }
    return text;
};

const venders = ref([])
const pageNumber = ref(1)
const totalPages = ref(Number)
const loadVenders = async () => {
    const URLAPI = `${import.meta.env.VITE_API_easystep}/venders/showAllVendersInfo/${pageNumber.value}`;
    const response = await axios.get(URLAPI);
    venders.value = response.data;
    const API = `${import.meta.env.VITE_API_easystep}/venders/showVendersInfoPage`;
    const pages = await axios.get(API);
    totalPages.value = pages.data;
}
const changepage = (number) => {
    pageNumber.value = number;
    loadVenders()
}
onMounted(() => {
    loadVenders()
})
</script>

<template>
    <br>
    <div class="row mb-2">
        <!-- 做迴圈的地方 -->
        <div class="col-md-12" v-for="{ venderName, venderIntroduce, writeOrUpdate, venderId, showphoto } in venders">
            <div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
                <!-- 放圖片的地方 -->
                <div class="col-auto d-none d-lg-block">

                    <svg class="bd-placeholder-img" width="200" height="250" xmlns="http://www.w3.org/2000/svg" role="img"
                        aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false">
                        <title>Placeholder</title>
                        <rect width="100%" height="100%" />
                        <image :xlink:href="showphoto" width="100%" height="100%" />
                    </svg>

                </div>
                <!-- 資訊 -->
                <div class="col p-4 d-flex flex-column position-static">
                    <!-- 傳到廠商頁面 -->

                    <router-link :to="'/vender/' + venderId" class="no-link-style">
                        <h3 class="mb-0">{{ venderName }}</h3>
                    </router-link>

                    <div class="mb-1 text-body-secondary">{{ writeOrUpdate }}</div>
                    <p class="card-text mb-auto">{{ truncateText(venderIntroduce, 50) }}</p>
                    <button class="button-like" @click="clickHandler(venderId)">
                        跟我聊聊
                    </button>

                </div>
            </div>
        </div>
    </div>
    <nav>
        <ul class="pagination pagination-sm">

            <li class="page-item" v-for="value, index in totalPages">
                <button @click="changepage(index + 1)">{{ index + 1 }}</button>
            </li>
        </ul>
    </nav>
</template>
<style scoped>
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

.no-link-style {
    text-decoration: none;
    color: inherit;
}
</style>