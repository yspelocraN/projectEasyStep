<template>
  <div>
    <topPhoto></topPhoto>
    <!-- <h1>首頁</h1>
    <div th:text="${session.loginuser}?'您好'+${session.loginuser.username}"></div>
    <div id="loginStatus"></div>

    <div>{{ travelername }}{{ vendername }}歡迎登入Easystep</div> -->

    <!-- 地圖推薦 -->

    <div class="card mt-5 mb-5 mx-auto mb-3" style="max-width: 1300px; min-width: 600px;">
      <div class="card-header">
        <div class="d-flex justify-content-center">
          <h1>推薦打工換宿廠商</h1>
        </div>
        <div class="mt-3 d-flex justify-content-center">
          <h5>
            <span id="other" :style="{ color: otherColor }" @click="changeVender2('other')"
              class=" badge ms-2 me-2 bg-danger-subtle text-emphasis-danger">全台</span>
            <span id="north" :style="{ color: northColor }" @click="changeVender('north')"
              class="badge me-2 bg-danger-subtle text-emphasis-danger ">北部</span>
            <span id="center" :style="{ color: centerColor }" @click="changeVender('center')"
              class="badge me-2 bg-danger-subtle text-emphasis-danger ">中部</span>
            <span id="south" :style="{ color: southColor }" @click="changeVender('south')"
              class="badge me-2 bg-danger-subtle text-emphasis-danger ">南部</span>
            <span id="east" :style="{ color: eastColor }" @click="changeVender('east')"
              class="badge me-2 bg-danger-subtle text-emphasis-danger ">東部</span>
          </h5>
        </div>
      </div>
      <div class="">
        <div class="card " style="max-width: 1300px;min-width: 600px;" id="venderinfo">
          <div class="row g-0 ">
            <div class="col d-flex justify-content-center">
              <!-- <div class="circular-container2  d-flex justify-content-center ms-5 " > -->
              <img :src="imagePath" usemap="#image-map" class="mt-2 ms-5 ">
              <map name="image-map">
                <area id="north" @click.prevent="changeVender('north')" @mouseout="resetImage" target="_self" alt="north"
                  title="north" href=""
                  coords="236,10,214,22,211,38,182,50,160,66,154,83,146,98,140,117,123,131,113,140,112,156,124,169,141,178,157,179,174,178,211,155,229,114,250,94,267,80,287,67,302,62,293,49,286,38,261,37"
                  shape="poly">
                <area id="center" @click.prevent="changeVender('center')" @mouseout="resetImage" target="_self"
                  alt="center" title="center" href=""
                  coords="107,157,93,186,67,239,49,280,50,334,65,339,81,326,99,323,116,341,127,345,135,334,167,306,183,305,193,287,205,279,211,188,235,163,212,154,179,176,148,182,125,174"
                  shape="poly">
                <area id="south" @click.prevent="changeVender('south')" @mouseout="resetImage" target="_self" alt="south"
                  title="south" href=""
                  coords="51,337,42,362,47,385,57,397,72,429,80,452,104,471,118,475,142,504,150,535,151,551,176,558,174,535,177,501,157,487,150,473,153,434,168,420,163,407,157,385,164,370,171,340,184,328,185,306,167,306,127,346,116,344,100,327,78,330,71,340"
                  shape="poly">
                <area id="east" @click.prevent="changeVender('east')" @mouseout="resetImage" target="_self" alt="east"
                  title="east" href=""
                  coords="284,73,254,90,227,119,214,151,235,162,212,189,208,276,186,303,187,328,171,342,159,385,169,416,155,439,152,473,173,495,190,435,219,406,233,375,248,313,253,275,264,217,260,196,278,164,283,148,289,125,280,86"
                  shape="poly">
                <area id="other" @click.prevent="changeVender2('other')" target="" alt="other" title="other" href=""
                  coords="196,448,182,504,185,553,189,568,341,564,345,383,348,259,349,4,269,0,1,4,1,385,2,566,182,569,175,561,148,557,142,510,125,483,79,453,41,368,49,333,45,292,62,244,92,180,128,118,142,103,169,51,207,34,216,14,243,7,264,35,286,30,299,45,312,63,296,77,292,89,296,106,300,123,274,201,263,269,251,336,236,387,211,428"
                  shape="poly">
              </map>
              <!-- </div> -->
            </div>
            <div class="col" style="min-width:700px;">
              <div class="card-body overflow-auto " id="box">
                <div class="d-flex mb-3 flex-wrap justify-content-center">
                  <div class="p-2" v-for="(value, key) in venderinfo" :key="key">
                    <div class="card mb-3 " style="width: 19rem;">
                      <div class="d-flex justify-content-end">
                        <span class="fs-4 z-0 position-absolute p-2 rounded-1"><i class="bi bi-suit-heart-fill" id="heart"
                            :style="{ color: heartColor[value.venderid] }"
                            @click="changeheart(travelerid, value.venderid)" v-if="trVisible"></i></span>
                      </div>
                      <a :href="venderUrl[value.venderid]">
                        <div class="circular-container">
                          <div v-if="value.url == ''">
                            <img src="../assets/sphoto.png" class="card-img-top " id="photo" alt="...">
                          </div>
                          <div v-else>
                            <img :src="value.url" class="card-img-top " id="photo" alt="">
                          </div>
                        </div>
                        <div class="card-body">
                          <span v-if="value == null">訊息加載中...</span>
                          <span v-if="value.vendername"
                            class="badge  me-5 bg-danger-subtle text-emphasis-danger text-primary-emphasis"
                            style="width: 12rem;"><i class="bi bi-info-circle me-2"></i>{{ value.vendername }}</span>
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
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
    
<script setup>

import { ref, onMounted, onBeforeMount } from 'vue'
import axios from 'axios';
import topPhoto from '../components/topPhoto.vue';
const venderUrl = ref({});
const vendername = ref('');
const travelername = ref('');
const otherColor = ref('');
const northColor = ref('');
const centerColor = ref('');
const southColor = ref('');
const eastColor = ref('');

const imagePath = ref('/src/assets/new00.png');
const venderinfo = ref('');
const Allvenderid = ref([]);
const travelerid = ref('')
const heartColor = ref({});



//取得所有收藏廠商id
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
      setHeartRed(item.venderid);
    }
  }
}

//收藏廠商變紅色
const setHeartRed = (venderid) => {
  heartColor.value[venderid] = 'red';
};

//取消收藏廠商變回白色
const setHeartWhite = (venderid) => {
  heartColor.value[venderid] = '';
};

//收藏廠商和取消收藏廠商
const changeheart = async (travelerid, venderid) => {
  if (heartColor.value[venderid] == 'red') {
    const URL = `http://localhost:8084/easystep/Traveler/deletelike?travelerid=${travelerid}&venderid=${venderid}`
    const response = await axios.delete(URL, {
      withCredentials: true,
    })
    console.log(response.data)
    setHeartWhite(venderid)
  } else {
    const URL = `http://localhost:8084/easystep/Traveler/addlike?travelerid=${travelerid}&venderid=${venderid}`
    const response = await axios.post(URL, {
      withCredentials: true,
    })
    console.log(response.data)
    setHeartRed(venderid)
  }
}

const download = async () => {
  const URL = `http://localhost:8084/easystep/Vender/getlogin`
  const response = await axios.get(URL, {
    withCredentials: true,
    cache: false
  })
  console.log(response.data.infoDTo)
  vendername.value = response.data.username;
}

const trdownload = async () => {
  const URL = `http://localhost:8084/easystep/Traveler/getlogin`
  const response = await axios.get(URL, {
    withCredentials: true,
    cache: false
  })
  console.log(response.data.infoDTo)
  const userId = response.data.travelerid; // 登入後取得ID
  travelerid.value = response.data.travelerid;
  sessionStorage.setItem('travelerid', userId);//設置一個session辨認會員

  travelername.value = response.data.username;

  likeVender();
}

//台灣全部廠商 
const changeVender2 = async () => {
  const area = "全部"
  const URL = `http://localhost:8084/easystep/Vender/getArea?json=${area}`
  const response = await axios.get(URL, {
    withCredentials: true,
  })
  venderinfo.value = '';
  venderinfo.value = response.data
  console.log(response.data)
  imagePath.value = "/src/assets/new00.png";
  otherColor.value = 'black';
  northColor.value = '';
  centerColor.value = '';
  southColor.value = '';
  eastColor.value = '';

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

//台灣廠商分地區（北中南東）
const changeVender = async (areaId) => {
  if (areaId == "north") {
    const area = "北部"
    const URL = `http://localhost:8084/easystep/Vender/getArea?json=${area}`
    const response = await axios.get(URL, {
      withCredentials: true,
    })
    venderinfo.value = '';
    venderinfo.value = response.data
    console.log(response.data)
    imagePath.value = "/src/assets/north.png";

    otherColor.value = '';
    northColor.value = 'black';
    centerColor.value = '';
    southColor.value = '';
    eastColor.value = '';
  } else if (areaId == "center") {
    const area = "中部"
    const URL = `http://localhost:8084/easystep/Vender/getArea?json=${area}`
    const response = await axios.get(URL, {
      withCredentials: true,
    })
    venderinfo.value = '';
    venderinfo.value = response.data
    console.log(response.data)
    imagePath.value = "/src/assets/center.png";
    otherColor.value = '';
    northColor.value = '';
    centerColor.value = 'black';
    southColor.value = '';
    eastColor.value = '';
  } else if (areaId == "south") {
    const area = "南部"
    const URL = `http://localhost:8084/easystep/Vender/getArea?json=${area}`
    const response = await axios.get(URL, {
      withCredentials: true,
    })
    venderinfo.value = '';
    venderinfo.value = response.data
    console.log(response.data)
    imagePath.value = "/src/assets/south.png";
    otherColor.value = '';
    northColor.value = '';
    centerColor.value = '';
    southColor.value = 'black';
    eastColor.value = '';
  } else if (areaId == "east") {
    const area = "東部"
    const URL = `http://localhost:8084/easystep/Vender/getArea?json=${area}`
    const response = await axios.get(URL, {
      withCredentials: true,
    })
    venderinfo.value = '';
    venderinfo.value = response.data
    console.log(response.data)
    imagePath.value = "/src/assets/east.png";
    otherColor.value = '';
    northColor.value = '';
    centerColor.value = '';
    southColor.value = '';
    eastColor.value = 'black';
  }
};

// onMounted(() => {
//   download();
//   trdownload();
// })

onBeforeMount(() => {
  download();
  trdownload();
  changeVender2();
})




</script>
<style scoped>
.circular-container2 {
  height: 600px;
  width: 450px;
  /* background-color: antiquewhite; */
}

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

#info {
  background-color: bisque;
}

#venderinfo {
  background-color: rgb(255, 249, 229);
}

#heart {
  color: rgb(255, 255, 255);
}
</style>