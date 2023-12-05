<script setup>
import axios from "axios";
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";

const venderId = ref(Number)
const route = useRoute()
const vender = ref({})
const loadData = async () => {
    const id = route.params.id
    venderId.value = id
    const API = `${import.meta.env.VITE_API_easystep}/vender/showVenderInfo/${id}`
    const response = await axios.get(API)
    vender.value = response.data
}
const other = ref([])
const otherVenders = async () => {
    const id = route.params.id
    const API = `${import.meta.env.VITE_API_easystep}/vender/showotherinfo/${id}`
    const responses = await axios.get(API)
    other.value = responses.data
}
onMounted(() => {
    loadData()
    otherVenders()
})
</script>

<template>
    <div class="row g-5">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header"> <span>可以工作的月份</span></div>
                <div class="card-body">
                    <span>{{ vender.venderWorkmonth }}</span>
                </div>
            </div>
            <br>
            <div class="card">
                <div class="card-header"> <span>廠商敘述</span></div>
                <div class="card-body">
                    <span>{{ vender.venderIntroduce }}</span>
                </div>
            </div>
            <br>
            <div class="card">
                <div class="card-header"><span>供宿環境簡介</span></div>
                <div class="card-body">
                    <span>{{ vender.venderStay }}</span>
                </div>
            </div>
            <br>
            <div class="card">
                <div class="card-header"><span>需要人數</span></div>
                <div class="card-body">
                    <span>{{ vender.workMans }}</span>
                </div>
            </div>
            <br>
            <div class="card">
                <div class="card-header"><span>每日工作時長</span></div>
                <div class="card-body">
                    <span>{{ vender.venderWorktime }}</span>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="col">
                <!-- 放其他廠商資訊 -->
                <h3>其他廠商</h3>
                <div class="card shadow-sm" v-for="{ venderId, venderName, showphoto, writeOrUpdate } in other">
                    <svg class="bd-placeholder-img card-img-top" width="100%" height="225"
                        xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail"
                        preserveAspectRatio="xMidYMid slice" focusable="false">
                        <rect width="100%" height="100%" fill="#55595c" />
                        <!-- 塞圖網址 -->
                        <image :xlink:href="showphoto" width="100%" height="100%" />
                    </svg>
                    <!-- 塞廠商名 跟更新時間 -->
                    <div class="card-body">
                        <a :href="'/vender/' + venderId">
                            <p class="card-text">{{ venderName }}</p>
                        </a>
                        <div class="d-flex justify-content-between align-items-center">
                            <small class="text-body-secondary">更新時間 : {{ writeOrUpdate }}</small>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</template>
<style scoped></style>