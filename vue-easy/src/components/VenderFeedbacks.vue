<script setup>
import axios from "axios";
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";

const venderId = ref(Number)
const route = useRoute()
const feedbacks = ref([])

const loadData = async () => {
    const id = route.params.id
    venderId.value = id
    const API = `${import.meta.env.VITE_API_easystep}/venders/showfeedback/${id}`
    const response = await axios.get(API)
    feedbacks.value = response.data
    console.log(feedbacks.value);
}
onMounted(() => {
    loadData()
})
</script>

<template>
    <h1>回饋</h1>
    <div v-for="{ travelerName, writeDate, score, travelerPhoto, feedback } in feedbacks">
        <div class="card">
            <div class="card-body">
                <div class="row featurette">
                    <div class="col-md-11 order-md-2">
                        <h5 class="featurette-heading fw-normal lh-1">{{ travelerName }}</h5>
                        <p class="lead">{{ feedback }}</p>
                    </div>
                    <div class="col-md-1 order-md-1">
                        <svg class="bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto rounded-circle"
                            width="140" height="140" xmlns="http://www.w3.org/2000/svg" role="img"
                            aria-label="Placeholder: 500x500" preserveAspectRatio="xMidYMid slice" focusable="false">
                            <rect width="100%" height="100%" fill="var(--bs-secondary-bg)" />
                            <image :xlink:href="travelerPhoto" width="100%" height="100%" />
                        </svg>
                    </div>
                </div>
            </div>
        </div>
        <br>
    </div>
    <div class="card">
        <div class="card-body">
            也來給點評論 ~ <router-link :to="'/addFeedback/' + venderId">評論</router-link>
        </div>
    </div>
</template>
<style scoped></style>