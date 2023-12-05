<template>
	<div>
		<nav class="navbar navbar-expand-lg navbar-light bg-body-tertiary container">
			<div class="container-fluid">
				<RouterLink to="/"><img src="../assets/about_version.png" alt="" id="logo"></RouterLink>

				<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
					data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>

				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav ml-auto">
						<li class="nav-itemp-2 ">
							<RouterLink class="nav-link mx-auto" to="/venderList">尋找廠商</RouterLink>
						</li>
						<li class="nav-itemp-2 ">
							<RouterLink class="nav-link mx-auto" to="/traveler/list">尋找幫手</RouterLink>
						</li>
						<li class="nav-itemp-2 ">
							<RouterLink class="nav-link mx-auto" to="/tips">旅行小訣竅</RouterLink>
						</li>
					</ul>
				</div>

				<div class="mt-2 mt-md-0">
					<div class="collapse navbar-collapse" id="navbarSupportedContent">
						<ul class="navbar-nav ml-auto">

							<li class="nav-itemp-2 " v-if="logoutVisible && trVisible">
								<RouterLink class="nav-link mx-auto" to="/tmessages">聊聊</RouterLink>
							</li>

							<li class="nav-item dropdown" v-if="logoutVisible && veVisible">
								<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
									aria-expanded="false">
									聊聊
								</a>
								<ul class="dropdown-menu">
									<li>
										<RouterLink to="/vmessages" class="dropdown-item">更多聊聊~</RouterLink>
									</li>
									<li v-for="{ travelerId, travelerName } in talkMans">
										<button class="button-like nav-item nav-link link-body-emphasis"
											@click="clickHandler(travelerId)">
											{{ travelerName }}
										</button>
									</li>
								</ul>
							</li>

							<li class="nav-item dropdown" v-if="logoutVisible || (adminLoginStatus == true || adminLoginStatus == 'true')">
								<!-- 超級管理員用 -->
								<a v-if="adminLoginStatus == true || adminLoginStatus == 'true'" class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
									管理員功能表
								</a>
								
								<a v-else class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
									aria-expanded="false">
									用戶專區
								</a>

								<ul class="dropdown-menu">

									<li v-if="trVisible && !(adminLoginStatus == true || adminLoginStatus == 'true')">
										<RouterLink to="/Traveler/TravelerUpdate" class="dropdown-item">小幫手</RouterLink>
									</li>
									<li v-if="trVisible && !(adminLoginStatus == true || adminLoginStatus == 'true')">
										<RouterLink to="/Traveler/LikeVender" class="dropdown-item">收藏廠商</RouterLink>
									</li>
									<li v-if="veVisible && !(adminLoginStatus == true || adminLoginStatus == 'true')">
										<RouterLink to="/Vender/VenderUpdate" class="dropdown-item">廠商</RouterLink>
									</li>
									<li v-if="trVisible && !(adminLoginStatus == true || adminLoginStatus == 'true')">
										<RouterLink :to="'/blog/' + travelerid" class="dropdown-item">Blog</RouterLink>
									</li>
									<li v-if="veVisible && !(adminLoginStatus == true || adminLoginStatus == 'true')">
										<RouterLink to="/addVenderInformation" class="dropdown-item">工作資訊</RouterLink>
									</li>

									<!-- 超級管理員用 -->
									<li v-if="adminLoginStatus == true || adminLoginStatus == 'true'">
										<!-- <RouterLink to="/admin/venderManage" class="dropdown-item" @click="resetReloadStatus">廠商帳號管理</RouterLink> -->
										<button class="dropdown-item" @click="venderManage">廠商帳號管理</button>
									</li>
									<li v-if="adminLoginStatus == true || adminLoginStatus == 'true'">
										<!-- <RouterLink to="/admin/travelerManage" class="dropdown-item" @click="resetReloadStatus">旅人帳號管理</RouterLink> -->
										<button class="dropdown-item" @click="travelerManage">旅人帳號管理</button>
									</li>
									<li v-if="adminLoginStatus == true || adminLoginStatus == 'true'">
										<!-- <RouterLink to="/tips" class="dropdown-item" @click="resetReloadStatus">編輯旅行小訣竅</RouterLink> -->
										<button class="dropdown-item" @click="tipManage">編輯旅行小訣竅</button>
									</li>

								</ul>
							</li>

							<li class="nav-item dropdown ml-auto" v-if="logVisible && !(adminLoginStatus == true || adminLoginStatus == 'true')">
								<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
									aria-expanded="false">
									登入
								</a>
								<ul class="dropdown-menu">
									<li>
										<RouterLink to="/Traveler/TravelerLog" class="dropdown-item">小幫手</RouterLink>
									</li>
									<li>
										<RouterLink to="/Vender/VenderLog" class="dropdown-item">廠商</RouterLink>
									</li>

									<!-- 管理員登入 -->
									<li v-if="isAdminLoginVisable == true || isAdminLoginVisable == 'true'">
										<RouterLink to="/admin" class="dropdown-item">管理者</RouterLink>
									</li>
								</ul>
							</li>

							<li class="nav-item dropdown ml-auto" v-if="logVisible && !(adminLoginStatus == true || adminLoginStatus == 'true')">
								<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
									aria-expanded="false">
									註冊
								</a>
								<ul class="dropdown-menu">
									<li>
										<RouterLink to="/Traveler/TravelerRegister" class="dropdown-item">小幫手</RouterLink>
									</li>
									<li>
										<RouterLink to="/Vender/VenderRegister" class="dropdown-item">廠商</RouterLink>
									</li>
								</ul>
							</li>

							<li class="nav-item ml-auto">
								<!-- 超級管理員用 -->
								<button v-if="adminLoginStatus == true || adminLoginStatus == 'true'" class="btn btn-light" @click="adminLogout">
									管理者登出
								</button>
								
								<button v-else-if="logoutVisible" class="btn btn-light" @click="logout"
									v-show="logoutVisible">登出</button>
							</li>
						</ul>
					</div>
				</div>

				<div class="d-flex">
					<input class="form-control me-2" type="text" v-model="searchKeyword" placeholder="Search"
						aria-label="Search" @keyup.enter="search">
					<button class="btn btn-outline-success" type="button" @click="search">Search</button>
				</div>
			</div>


			

		</nav>

	</div>
</template>
    
<script setup >


import axios from 'axios';
import { onMounted, ref, onBeforeMount, } from 'vue';
import { useRouter } from 'vue-router';

//取得session
const travelerid = sessionStorage.getItem('travelerid');

const logout = async () => {
	const URL = `http://localhost:8084/easystep/Vender/venderlogout`
	const response = await axios.get(URL, {
		withCredentials: true,
	})
	if (response.data == 'ok') {
		console.log(response.data);
		setTimeout("location.href='/'", 1000);
		sessionStorage.clear();
		sessionStorage.removeItem('travelerid');
	}
}

const veVisible = ref(true);
const trVisible = ref(true);
const logoutVisible = ref(false);
const logVisible = ref(true);

const vIsLoggedIn = sessionStorage.getItem('vender');
const tIsLoggedIn = sessionStorage.getItem('traveler');

const logout2 = () => {
	if (vIsLoggedIn) {
		trVisible.value = false
		logoutVisible.value = true
		logVisible.value = false
		console.log(vIsLoggedIn)

	} else if (tIsLoggedIn) {
		veVisible.value = false
		logoutVisible.value = true
		logVisible.value = false
	}

}

const talkMans = ref([])
const emit = defineEmits(["childVenderClick"])
const clickHandler = travelerId => {
	emit("childVenderClick", travelerId)
}
// 載入聊過天的 旅者 跟查看登入狀態
const openTalk = async () => {
	const URL = `${import.meta.env.VITE_API_easystep}/vender/checklogin`
	const response = await axios.get(URL, { withCredentials: true })
	if (response.data.success) {

		const API = `${import.meta.env.VITE_API_easystep}/chatroom/findtras`
		const responses = await axios.get(API, { withCredentials: true })
		talkMans.value = responses.data
	}
}

const searchKeyword = ref('');
const router = useRouter();

const search = () => {
	// 重定向到另一个 URL 并传递搜索关键字作为查询参数
	router.push({ path: '/search', query: { q: searchKeyword.value } });

};


/* 超級管理者權限 */
const adminLoginStatus = ref(false);

function chkAdminLogin() {
	if (sessionStorage.getItem('adminLoginStatus') != null) {
		adminLoginStatus.value = sessionStorage.getItem('adminLoginStatus')
	}
	if (adminLoginStatus == true || adminLoginStatus == 'true') {
		veVisible.value = false
		trVisible.value = false
	}
}

function resetReloadStatus() {
	sessionStorage.setItem('reloadPage', '0')
}

function venderManage() {
	resetReloadStatus()
	setTimeout("location.href='/admin/venderManage'", 1)
}
function travelerManage() {
	resetReloadStatus()
	setTimeout("location.href='/admin/travelerManage'", 1)
}
function tipManage() {
	resetReloadStatus()
	setTimeout("location.href='/tips'", 1)
}

const adminLogout = async () => {
	const response = await axios.get(`http://localhost:8084/easystep/admin/logout`, {withCredentials: true})
	if (response.data == true) {
		sessionStorage.clear()
		sessionStorage.setItem('adminLoginStatus', false)
		logout2()
		chkAdminLogin()
		isAdminLoginVisable.value = false
		cheatSuccess.value = 0
		setTimeout("location.href='/'", 1)
	}
}

const fuckYouCodenami = ['ArrowUp', 'ArrowUp', 'ArrowDown', 'ArrowDown', 'ArrowLeft', 'ArrowRight', 'ArrowLeft', 'ArrowRight', 'b', 'a']
const cheatSuccess = ref(0)
const isAdminLoginVisable = ref(false)
document.addEventListener('keydown', function (event) {
	if (event.key === fuckYouCodenami[cheatSuccess.value]) {
		cheatSuccess.value++
		
		if (	cheatSuccess.value === fuckYouCodenami.length
			&&	(adminLoginStatus.value === false || adminLoginStatus.value === 'false')) {
			isAdminLoginVisable.value = true
			cheatSuccess.value = 0
		}
		setTimeout(() => {
			isAdminLoginVisable.value = false
		}, 30000)
	} else {
		cheatSuccess.value = 0
	}
})



onMounted(() => {
	chkAdminLogin()
	logout2()
	openTalk()
})




</script>
    
<style scoped>
#logo {
	width: 50%;
	height: 50%;
	object-fit: cover;
}
</style>