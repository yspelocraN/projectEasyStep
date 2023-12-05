<template>
  <div>

    <div style="width: 50%; margin: 20px auto; text-align: center;">
      <h1>搜尋結果：</h1>
    </div>

    <div style="width: 50%; margin: 0 auto; text-align: center;">
      <div class="input-group mb-3">
        <input type="text" class="form-control custom-input" aria-label="Recipient's username"
          aria-describedby="button-addon2" v-model="searchKeyword" placeholder="輸入搜尋關鍵字" @keyup.enter="search" />
        <button class="btn btn-outline-secondary custom-button" type="button" id="button-addon2"
          @click="search">搜索</button>
      </div>
      <div class="btn-group" role="group" aria-label="Basic outlined example" style="width: 100%;">
        <button type="button" class="btn btn-outline-primary" @click="filterResults('/search')">全部</button>
        <button type="button" class="btn btn-outline-primary" @click="filterResults('/search/traveler')">旅者</button>
        <button type="button" class="btn btn-outline-primary" @click="filterResults('/search/vender')">商家</button>
        <!-- <button type="button" class="btn btn-outline-primary" @click="filterResults('/search/zip')">地區</button> -->
      </div>
    </div>

    <div style="width: 50%; margin: 0 auto;">

      <div v-if="searchResults.length > 0">
        <p>共 {{ searchResults.length }} 筆結果</p>
        <ul class="search-results">
          <li v-for="(result, index) in searchResults" :key="index">
            <div class="search-result">


              <!--尋找地點-->
              <div v-if="result.zipno">
                <p>郵遞區號: {{ result.zipno }}
                  <!-- 使用 <a> 元素来创建链接 -->
                  <a :href="getExternalLink(result.zipno, result.city)" target="_blank"><i
                      class="bi bi-geo-alt-fill"></i></a>
                </p>
                <p>城市: {{ result.city }}</p>
                <p>區域: {{ result.dist }}</p>
              </div>
              <!--尋找旅者-->
              <div v-else-if="result.travelerAcct">
                <router-link :to="'/blog/' + result.travelerId" class="no-link-style">
                  <div>
                    <p>帳號: {{ result.travelerAcct }}</p>
                    <p>暱稱: {{ result.travelerName }}</p>
                    <p>Email: {{ result.email }}</p>
                  </div>
                </router-link>
              </div>
              <!--尋找廠商-->
              <div v-else-if="result.venderconame">
                <router-link :to="'/vender/' + result.venderId" class="no-link-style">
                  <div>
                    <p>帳號: {{ result.venderconame }}</p>
                    <p>聯絡電話: {{ result.vendertel }}</p>
                    <p>廠商地址: {{ result.venderzipno }}</p>
                  </div>
                </router-link>
              </div>
            </div>
          </li>
        </ul>
      </div>



    </div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';

const searchKeyword = ref('');
const searchResults = ref([]);
const filterType = ref('');
const router = useRouter();
const route = useRoute();

const handleRouteChange = (to, from) => {
  console.log('URL已变化', to, from);
  // 在这里执行你想要的操作
  search();
};
router.afterEach(handleRouteChange);

const search = async () => {
  // 检查搜索关键字是否为空
  if (searchKeyword.value) {
    try {
      if (filterType.value == '') {
        filterType.value = '/search';
      }
      // 更新 URL 中的搜索关键字参数
      // router.push({ query: { q: searchKeyword.value } });
      const response = await axios.get(`${import.meta.env.VITE_API_easystep}${filterType.value}?q=${searchKeyword.value}`);
      searchResults.value = response.data;
    } catch (error) {
      console.error('Error searching:', error);
    }
  } else {
    // 清空搜索结果
    searchResults.value = [];
  }
}

const getExternalLink = (zipno, city) => {
  const formattedCity = city.replace(/ /g, '+');
  return `https://www.google.com/maps/place/${zipno}+${formattedCity}`;
}

const filterResults = (type) => {
  filterType.value = type;
  search();
  console.log(type);
}

onMounted(() => {
  // 检查 URL 中是否包含搜索关键字参数，并更新搜索关键字
  if (route.query.q) {
    searchKeyword.value = route.query.q;
    search();
  }
})
</script>

<style scoped>
.search-results {
  list-style: none;
}

.search-result {
  border: 1px solid #ccc;
  margin: 10px;
  padding: 10px;
}

.no-link-style {
  text-decoration: none;
  color: inherit;
}
</style>