<script setup>
import axios from "axios";
import { onMounted, ref } from "vue";

// 往上傳ID
const emit = defineEmits(["childTraClick"])
const clickHandler = travelerId => {
    emit("childTraClick", travelerId)
}

const Travelers = ref([])
const trdatas = ref([])


//取得全部旅者
const trall = async () => {
    const URL = `${import.meta.env.VITE_API_easystep}/blog/list`;
    try {
        const response = await axios.get(URL, {
            withCredentials: true,
            cache: false,
        });
        Travelers.value = response.data;
        console.log(response.data.infoDTo);
    } catch (error) {

    }
    trlist();
};

const trlist = () => {

    // 獲取每個旅者的資料
    for (let i = 0; i < Travelers.value.length; i++) {
        const traveler = Travelers.value[i]; // 取得當前旅者物件
        const travelerId = traveler.travelerid; // 取得 travelerid 的值
        trdownload(travelerId); // 使用 travelerid 呼叫 trdownload 函數
    }

}




// 取得旅者資料，接受 travelerid 參數
const trdownload = async (travelerid) => {
    const URL = `${import.meta.env.VITE_API_easystep}/blog/${travelerid}`;
    try {
        const response = await axios.get(URL, {
            withCredentials: true,
            cache: false,
        });
        // 在每次呼叫時將資料添加到 trdatas 陣列中
        trdatas.value.push(response.data);
        console.log(response.data.infoDTo);
        travelername.value = response.data.username;
    } catch (error) {
        // 處理錯誤
    }
};

onMounted(() => {
    trall();

})


</script>

<template>
    <div>
        <br>
        <div class="row mb-2">
            <!-- 做迴圈的地方 -->
            <div class="col-md-12" v-for="Travrler in trdatas" :key="Travrler.travelerid">
                <div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
                    <!-- 放圖片的地方 -->
                    <div class="col-auto d-none d-lg-block">
                        <div class="circular-container">
                            <div class="circular-container" style="width: 250px; height: 250px;">
                                <img :src="Travrler.photoURL" alt="Profile Image"
                                    style="width: 100%; height: 100%; object-fit: cover;">
                            </div>
                        </div>
                    </div>
                    <div class="col p-4 d-flex flex-column position-static">
                        <router-link :to="'/blog/' + Travrler.travelerid" class="no-link-style">
                            <h3 class="mb-0">{{ Travrler.username }}</h3>
                        </router-link>

                        <div class="mb-1 text-body-secondary">{{ Travrler.email }}</div>

                        <h5 class="card-text mb-auto" v-if="Travrler.about">{{ Travrler.about }}</h5>
                        <h5 class="card-text mb-auto" v-else>每一次旅行，都像是一次新的人生</h5>
                        <button class="button-like " @click="clickHandler(Travrler.travelerid)">
                            跟我聊聊
                        </button>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>
<style scoped>
.circular-container {
    width: 230px;
    height: 230px;
    border-radius: 50%;
    overflow: hidden;
    position: relative;
}

.circular-container img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    /* 保持圖片的寬高比例並填滿容器 */
}

.no-link-style {
    text-decoration: none;
    color: inherit;
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