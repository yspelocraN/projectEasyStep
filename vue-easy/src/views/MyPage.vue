<script setup>
import changebotton from '../components/changebotton.vue';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';

const Album = ref('');
const trdatas = ref('');
const travelername = ref('');
const user = ref('');
const route = useRoute();
const path = user.value = route.params.travelerId;

// 取得每個相簿的第一張相片的 URL
const albumCoverUrls = ref([]);
//取得文章
const Articles = ref([]);


//取得session
const travelerid = sessionStorage.getItem('travelerid');
const login = sessionStorage.getItem('traveler');


//取得旅者資料
const trdownload = async () => {
	const URL = `${import.meta.env.VITE_API_easystep}/blog/${path}`;
	try {
		const response = await axios.get(URL, {
			withCredentials: true,
			cache: false,
		});
		trdatas.value = response.data;
		console.log(response.data.infoDTo);
		travelername.value = response.data.username;
	} catch (error) {

	}
};

//下載文章
const download = async () => {
	const URL = `${import.meta.env.VITE_API_easystep}/article/TopArticle/${path}`;
	try {
		const response = await axios.get(URL, {
			withCredentials: true,
			cache: false,
		});
		Articles.value = response.data;
		console.log(response.data);
	} catch (error) {
		console.log(error);
	}
};

//計算文章長度給省略號
const truncateText = (text, maxLength) => {
	if (text.length > maxLength) {
		return text.slice(0, maxLength) + '...';
	}
	return text;
};


// 取得所有該旅者的相簿和相片
const phdownload = async () => {
	const URL = `${import.meta.env.VITE_API_easystep}/photo/${path}`;
	try {
		const response = await axios.get(URL, {
			withCredentials: true,
			cache: false,
		});

		// 保存相簿信息
		Album.value = response.data;

		// 取得每個相簿的第一張相片的 URL
		albumCoverUrls.value = await Promise.all(
			response.data.map(async (album) => {
				// 獲取相簿的相片
				const photosResponse = await axios.get(
					`${import.meta.env.VITE_API_easystep}/photo/showphoto?albumId=${album.albumId}`,
					{ withCredentials: true, cache: false }
				);

				// 如果有相片，返回第一張相片的 URL，否則返回一個預設 URL
				if (photosResponse.data.length > 0) {
					return photosResponse.data[0].photoURL;
				} else {
					return 'https://res.cloudinary.com/dciwmbvcl/image/upload/v1698651546/hn2wmztbc8lxvwrzf24k.jpg'; // 將這個 URL 替換為你的預設圖片 URL
				}
			})
		);

	} catch (error) {
		console.error(error);
	}
};

// 更新小語
const updateAbout = async (travelerid) => {
	const a = document.getElementsByName("text")[0].value;
	const URL = `${import.meta.env.VITE_API_easystep}/blog/editAbout/${travelerid}?about=${a}`;
	try {
		await axios.put(URL, {
			withCredentials: true,
			cache: false,
		});
		console.log(a);
	} catch (error) {
		console.log(a);

	}
	closeEditModal();
};

// 直接關閉model不更改
const closeEditModal = () => {
	window.location.reload();
};


onMounted(() => {
	trdownload();
	download();
	phdownload();
});
</script>

<template>
	<div class="container">
		<changebotton></changebotton>
		<div class="container text-center">
			<div class="row ">
				<div class="col">
					<div class="card mt-5 mb-5 mx-auto" style="max-width: 800px; ">
						<div class="tab-content" id="nav-tabContent">
							<div class="tab-pane fade show active" id="nav-home" role="tabpanel"
								aria-labelledby="nav-home-tab" tabindex="0">
								<div class="card-body">
									<div class="card mb-3 mx-auto border border-white " style="max-width: 320px; ">
										<div class="card-body d-flex justify-content-center">
											<div class="circular-container border border-5">
												<img :src="trdatas.photoURL" class="card-img-top " id="photo" alt="...">
											</div>
										</div>
										<div class="card-body">
											<h1 class="card-title" style="font-family: 'Caveat', cursive;">{{
												trdatas.username
											}} </h1>
											<ul>
												<li>{{ trdatas.email }}</li>
												<li>{{ trdatas.mobile }}</li>
												<li>Sex: {{ trdatas.sexabbr }}</li>
												<li v-if="trdatas.about" style="font-size: 19px">{{ trdatas.about }}</li>
												<li v-else style="font-size: 19px">每一次的旅行，都像是一次新的人生</li>
											</ul>
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-primary" style="margin-right: 2px;"
											data-bs-toggle="modal" :data-bs-target="'#EditModal'"
											v-if="travelerid === path && login">編輯個人小語</button>
										<RouterLink to="/Traveler/TravelerUpdate" v-if="travelerid === path && login">
											<button type="button" class="btn btn-primary">編輯個人資料</button>
										</RouterLink>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col">
					<div style="height:600px;width:400px;overflow-y: scroll; overflow-x: hidden;">
						<H2>最新文章</H2>
						<div class="album py-5 bg-body-tertiary">
							<div class="container">
								<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-4 container">
									<div class="card " style="width: 18rem; margin-left: 10px;" v-for="Article in Articles"
										:key="Article.ArticleId">
										<div class="card-body">
											<h5 class="card-title">{{ Article.title }}</h5>
											<p class="card-text">{{ truncateText(Article.text, 30) }}</p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col">
					<div style="height:600px;width:400px;overflow-y: scroll; overflow-x: hidden;">
						<h2>近期相片</h2>
						<div class="album py-5 bg-body-tertiary">
							<div class="container">
								<div class="col" v-for="(album, index) in Album" :key="album.albumId">
									<div class="card shadow-sm">
										<div class="card-img-top"
											style="width: 100%; height: 225px; background-color: #55595c;"
											v-if="!albumCoverUrls[index]">
										</div>
										<img :src="albumCoverUrls[index]" id="photo" class="card-img-top" alt="album cover"
											width="100%" height="225" v-else>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!--EditModel-->
		<div class="modal fade" id="EditModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true"
			data-bs-backdrop="static">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<h3 class="modal-title" id="exampleModalLabel">心情小語</h3>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="modal-body" style="max-height: 400px; overflow-y: auto;">
						<textarea class="form-control" id="exampleFormControlTextarea1" rows="5" v-model="trdatas.about"
							style="resize: none;" name="text"></textarea>
					</div>
					<footer class="blockquote-footer" style="margin-left: 30px;"><cite title="Source Title">{{
						trdatas.username }}</cite></footer>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" @click="updateAbout(trdatas.travelerid)">更新</button>
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal"
							@click="closeEditModal">Close</button>
					</div>
				</div>
			</div>
		</div>


	</div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Caveat&display=swap');



ul {
	list-style-type: none;
}

li {
	font-family: 'Caveat', cursive;
	font-size: 30px;
}

.circular-container {
	width: 230px;
	/* 調整寬度和高度以設置圓形的大小 */
	height: 230px;
	border-radius: 50%;
	/* 將邊界半徑設置為50%以創建圓形 */
	overflow: hidden;
	/* 隱藏超出圓形容器的內容 */
}

#photo {
	width: 100%;
	height: 100%;
	object-fit: cover;
}
</style>
