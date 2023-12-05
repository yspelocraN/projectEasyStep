<script setup>
import changebotton from '../components/changebotton.vue';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
import Swal from 'sweetalert2';


const Articles = ref([]);
const selectedArt = ref('');
const Article = ref('');
const user = ref('');
const route = useRoute();
const path = user.value = route.params.travelerId;


//取得session
const travelerid = sessionStorage.getItem('travelerid');
const login = sessionStorage.getItem('traveler');


//取得所有該旅者的文章
const download = async () => {
	const URL = `${import.meta.env.VITE_API_easystep}/article/allArticle/${path}`;
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

onMounted(() => {
	download();
});


// 根據文章ID取得該文章內容
const showArt = async (articleId) => {
	const URL = `${import.meta.env.VITE_API_easystep}/article/showArt?ArtId=${articleId}`;
	try {
		const response = await axios.get(URL, {
			withCredentials: true,
			cache: false,
		});
		Article.value = response.data;
		// 设置 selectedArt.articleId
		selectedArt.value = {

			title: response.data.title,
			text: response.data.text,
			createTime: response.data.createTime,
			articleId: articleId, // 设置 articleId 属性
		};
		console.log(response.data);
	} catch (error) {
		console.error(error);
	}
};


//計算文章長度給省略號
const truncateText = (text, maxLength) => {
	if (text.length > maxLength) {
		return text.slice(0, maxLength) + '...';
	}
	return text;
};

// 直接關閉model不更改
const closeEditModal = () => {
	window.location.reload();
};

// 上傳文章
const PostContent = ref({
	title: '',
	text: '',
});

//PO文
const PostArt = async () => {
	const postData = {
		title: PostContent.value.title,
		text: PostContent.value.text,
		user: travelerid,
	};

	try {
		await axios.post(`${import.meta.env.VITE_API_easystep}/article/post?Title=${postData.title}&&text=${postData.text}&&user=${postData.user}`, {
			withCredentials: true,
			cache: false,
		});

		// 清空輸入框
		PostContent.value.title = '';
		PostContent.value.text = '';

		// 關閉模態框
		closeEditModal();
	} catch (error) {
		console.error(error);
	}
};


// 更新文章
const updateArt = async (articleId) => {
	const a = document.getElementsByName("title")[0].value;
	const b = document.getElementsByName("text")[0].value;
	//取得文章的值
	const URL = `${import.meta.env.VITE_API_easystep}/article/updateArt/${articleId}?title=${a}&text=${b}`;
	try {
		await axios.put(URL, {
			withCredentials: true,
			cache: false,
		});
		console.log(a);
		console.log(b);

	} catch (error) {
		console.log(a);
		console.log(b);

	}
	closeEditModal();
};

//刪除前詢問
const daleteAsk = (articleId) => {
	Swal.fire({
		title: '確定要刪除嗎?',
		text: "消失的紀錄將不再回來",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Yes',

	}).then((result) => {
		if (result.isConfirmed) {
			return Swal.fire({
				title: '刪除成功!',
				text: '您徹底忘了一件事',
				icon: 'success',
				showConfirmButton: true,
			});
		}
	})
		.then(() => {
			deleteArt(articleId)
			console.log('刪除操作已完成');
		});
}
//刪除文章
const deleteArt = async (articleId) => {
	const URL = `${import.meta.env.VITE_API_easystep}/article/deleteArt/${articleId}`;
	try {
		await axios.delete(URL, {
			withCredentials: true,
			cache: false,
		});

	} catch (error) {


	}
	closeEditModal();
};

//Demo文章
const Demo = () => {
	PostContent.value.title = '花蓮之美：大自然與探險的樂園';
	PostContent.value.text = `
      花蓮，位於台灣東岸的寶石，以其壯觀的自然美景和令人驚艷的探險機會而聞名。在這個美麗的城市，我們發現了大自然的奇蹟，以及豐富多彩的活動和體驗。
	  
      首先，太魯閣國家公園帶來了我們的震撼。在這裡，我們可以欣賞到壯觀的大理石峽谷、清澈的河流和懸崖上的瀑布。這片美麗的國家公園是攀岩者和登山者的夢想，也是大自然愛好者的天堂。

      接下來，我們前往秀姑巒溪，享受了泛舟的樂趣。在清澈的水中，我們穿越了壯觀的峽谷，欣賞了綠色山脈和多彩的岩層。這是一種放鬆和融入自然的絕佳方式。

      最後，我們參觀了花蓮市，品味了當地美食，包括新鮮的海鮮和道地的台灣小吃。這個城市充滿著熱情的人們和豐富的文化。

      花蓮之旅是一次難忘的冒險，它結合了自然之美和文化之韻。我們深陷於這個令人驚嘆的地方，帶著美好的回憶，感謝這片自然和人文之寶的探險之旅。
  `;
};


</script>

<template>
	<div class="container">
		<changebotton></changebotton>
		<button type="button" class="btn btn-primary" data-bs-toggle="modal" :data-bs-target="'#PostModal'"
			v-if="travelerid === path && login">發表文章</button>
		<div class="row mb-2">
			<div class="col-md-12" v-for="Article in Articles" :key="Article.ArticleId">
				<div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative"
					style="height: 8rem;">
					<div class="col p-4 d-flex flex-column position-static">
						<h3 class="mb-0">{{ Article.title }}</h3>
						<p class="card-text mb-auto">{{ truncateText(Article.text, 50) }}</p>
						<button type="button" class="btn btn-sm btn-outline-secondary position-absolute bottom-0 start-0"
							@click="showArt(Article.articleId); selectedArt = Article" data-bs-toggle="modal"
							:data-bs-target="'#exampleModal'" style="margin: 3px  3px 3px 10px;">
							View
						</button>
						<button type="button" class="btn btn-sm btn-outline-secondary position-absolute bottom-0 start-0"
							@click="showArt(Article.articleId); selectedArt = Article" data-bs-toggle="modal"
							:data-bs-target="'#EditModal'" style="margin: 3px  3px 3px 60px;"
							v-if="travelerid === path && login">
							Edit
						</button>
					</div>
				</div>
			</div>
		</div>


		<!-- showModel-->

		<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true"
			data-bs-backdrop="static">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<h3 class="modal-title" id="exampleModalLabel">{{ selectedArt.title }}</h3>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="modal-body" style="max-height: 400px; overflow-y: auto;">
						<p>{{ selectedArt.text }}</p>
						<footer class="blockquote-footer">CreateTime-<cite title="Source Title">{{ selectedArt.createTime
						}}</cite></footer>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>

		<!--EditModel-->
		<div class="modal fade" id="EditModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true"
			data-bs-backdrop="static">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="modal-body" style="max-height: 400px; overflow-y: auto;">
						<div class="mb-3">
							<label for="exampleFormControlInput1" class="form-label">標題:</label>
							<input type="email" class="form-control" id="exampleFormControlInput1"
								v-model="selectedArt.title" name="title">
						</div>
						<div class="mb-3">
							<label for="exampleFormControlTextarea1" class="form-label">內文:</label>
							<textarea class="form-control" id="exampleFormControlTextarea1" rows="10"
								v-model="selectedArt.text" style="resize: none;" name="text"></textarea>
						</div>
						<footer class="blockquote-footer">CreateTime-<cite title="Source Title">{{ selectedArt.createTime
						}}</cite>
							<br>
							<button type="button" class="btn btn-danger"
								@click="daleteAsk(selectedArt.articleId)">刪除文章</button>
						</footer>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" @click="updateArt(selectedArt.articleId)">更新</button>
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal"
							@click="closeEditModal">Close</button>
					</div>
				</div>
			</div>
		</div>

		<!--PostModel-->
		<div class="modal fade" id="PostModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true"
			data-bs-backdrop="static">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="btn btn-outline-light" @click="Demo()">Demo</button>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="modal-body" style="max-height: 400px; overflow-y: auto;">
						<div class="mb-3">
							<label for="exampleFormControlInput1" class="form-label">標題:</label>
							<input type="text" class="form-control" v-model="PostContent.title">
						</div>
						<div class="mb-3">
							<label for="exampleFormControlTextarea1" class="form-label">內文:</label>
							<textarea class="form-control" rows="10" v-model="PostContent.text"
								style="resize: none"></textarea>
						</div>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" @click="PostArt()">發布</button>
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal"
							@click="closeEditModal">Close</button>
					</div>
				</div>
			</div>
		</div>


	</div>
</template>




<style scoped>
@import '../css/styles';
</style>