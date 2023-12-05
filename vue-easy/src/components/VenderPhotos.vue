<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";

const venderId = ref(Number)
const route = useRoute()
const photos = ref([])

const loadData = async () => {
    const id = route.params.id
    venderId.value = id
    const API = `${import.meta.env.VITE_API_easystep}/vender/showAllphoto/${id}`
    const response = await axios.get(API)
    photos.value = response.data
}

onMounted(() => {
    loadData()
})
</script>

<template>
    <div id="svg-container" style="float: left;" v-for="value in photos">
        <svg width="400" height="400" xmlns="http://www.w3.org/2000/svg" style="margin: 15px;">
            <rect x="40" y="40" width="120" height="120" fill="white" />
            <image :xlink:href="value" width="100%" height="100%" />
        </svg>
    </div>
    <div style="clear: both;"></div>
</template>
<style scoped>
#svg-container {
    max-width: 100%;
    max-height: 100%;
}
</style>