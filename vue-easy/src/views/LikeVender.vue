<template>
  <div class="card mt-5 mx-auto mb-5" style="max-width: 800px;">
    <div class="card-header">
      <h1 class="col-md-4  float-start">收藏廠商</h1>
      <button class="btn btn-lg btn-outline-secondary float-end" @click="clearAllLike()">刪除所有收藏</button>
    </div>
    <div class="card-body overflow-auto ms-5" id="box">

      <div class="d-flex mb-3 flex-wrap">

        <div class="p-2" v-for="(value, key) in venderinfo" :key="key">
          <div class="card mb-3" style="width: 19rem;">
            <div class="d-flex justify-content-end">
              <span class="fs-4 z-0 position-absolute p-2 rounded-1"><i class="bi bi-suit-heart-fill" id="heart"
                  :style="{ color: heartColor[value.venderid] }"
                  @click="changeheart(travelerid, value.venderid)"></i></span>
            </div>
            <a :href="venderUrl[value.venderid]">
              <div class="circular-container">

                <div v-if="value.url == ''">
                  <img src="../../src/assets/sphoto.png" class="card-img-top " id="photo" alt="...">
                </div>
                <div v-else>
                  <img :src="value.url" class="card-img-top " id="photo" alt="">
                </div>
              </div>
              <div class="card-body">
                <span v-if="value == null">訊息加載中...</span>
                <span v-if="value.vendername"
                  class="badge  me-5 bg-danger-subtle text-emphasis-danger text-primary-emphasis" style="width: 12rem;"><i
                    class="bi bi-info-circle me-2"></i>{{ value.vendername }}</span>
                <span v-if="value.city"
                  class="badge text-bg-primary me-1 bg-danger-subtle text-emphasis-danger text-primary-emphasis"
                  style="width: 4rem;">{{ value.city }}</span>
                <span v-if="value.dist"
                  class="badge bg-primary me-1 bg-danger-subtle text-emphasis-danger text-primary-emphasis"
                  style="width: 4rem;">{{ value.dist }}</span>
                <span v-if="value.tel"
                  class="badge text-bg-primary me-1 bg-danger-subtle text-emphasis-danger text-primary-emphasis"
                  style="width: 8rem;"><i class="bi bi-telephone-inbound-fill me-2"></i>{{ value.tel }}</span>
              </div>
            </a>
          </div>
        </div>


      </div>








    </div>
  </div>
</template>
  
<script setup >
import { ref, onMounted, onBeforeMount, onUpdated } from 'vue'
import axios from 'axios';
const venderinfo = ref('');
const heartColor = ref({});
const travelerid = ref('');
const venderUrl = ref({});
const Allvenderid = ref([]);

//取消收藏廠商
const changeheart = async (travelerid, venderid) => {
  const URL = `http://localhost:8084/easystep/Traveler/deletelike?travelerid=${travelerid}&venderid=${venderid}`
  const response = await axios.delete(URL, {
    withCredentials: true,
  })
  console.log(response.data)
  likeVender();
}

//取得所有廠商
const likeVender = async () => {
  const travelerid = sessionStorage.getItem('travelerid')
  const URL = `http://localhost:8084/easystep/Traveler/getAllLike?travelerid=${travelerid}`
  const response = await axios.get(URL, {
    withCredentials: true,
  })
  venderinfo.value = '';
  venderinfo.value = response.data
  console.log(response.data)

  if (Array.isArray(venderinfo.value)) {
    for (const item of venderinfo.value) {
      Allvenderid.value.push(item.venderid);
      setVenderUrl(item.venderid);
    }
  }
}
//安插連結
const setVenderUrl = (venderid) => {
  venderUrl.value[venderid] = `http://localhost:5173/vender/${venderid}`;
};


onBeforeMount(() => {
  likeVender();
  travelerid.value = sessionStorage.getItem('travelerid')
})

//刪除所有廠商
const clearAllLike = async () => {
  const travelerid = sessionStorage.getItem('travelerid')

  const URL = `http://localhost:8084/easystep/Traveler/deleteAllLike?travelerid=${travelerid}`
  const response = await axios.delete(URL, {
    withCredentials: true,
  })
  console.log(response.data)
  likeVender();
}


</script>
  
<style scoped>
.circular-container {
  height: 190px;
  overflow: hidden;
  /* 隱藏超出圓形容器的內容 */
  /* background-color: antiquewhite; */
}

#photo {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

#box {
  height: 580px;
}

#heart {
  color: red;
}
</style>