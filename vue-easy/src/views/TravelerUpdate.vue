<template>
  <div>
    <div class="card mt-5 mb-5 mx-auto" style="max-width: 800px;">
      <div class="card-header position-relative">
        <h1 class="col-md-4 float-start">資料修改</h1>
        <div class="nav nav-tabs justify-content-end position-absolute bottom-0 end-0" id="nav-tab" role="tablist">
          <button class="nav-link active" id="nav-home-tab" data-bs-toggle="tab" data-bs-target="#nav-home" type="button"
            role="tab" aria-controls="nav-home" aria-selected="true">基本資料</button>
          <button class="nav-link" id="nav-profile-tab" data-bs-toggle="tab" data-bs-target="#nav-profile" type="button"
            role="tab" aria-controls="nav-profile" aria-selected="false">修改密碼</button>

        </div>
      </div>

      <div class="tab-content" id="nav-tabContent">
        <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab" tabindex="0">

          <div class="card-body ">



            <div class="card mb-3 mx-auto border border-white " style="max-width: 320px;">
              <div class="card-body d-flex justify-content-center">
                <div class="circular-container border border-5">
                  <div v-if="imagePath == ''">
                    <img src="../../src/assets/sphoto.png" class="card-img-top " id="photo" alt="...">
                  </div>
                  <div v-else>
                    <img :src="imagePath" class="card-img-top " id="photo" alt="...">
                  </div>
                </div>
              </div>
              <div class="card-body">
                <div class="row">
                  <div class="col-md-11 position-relative mx-auto">
                    <div class="text-center"><label for="formFile" class="form-label "><i
                          class="bi bi-person-circle me-1"></i>個人照</label></div>
                    <input class="form-control form-control-sm" type="file" id="formFile" @change="handleFileUpload">
                    <div class="text-center mt-3"><button @click="uploadFile" class="btn btn-outline-secondary ms-2"
                        data-bs-toggle="modal" data-bs-target="#exampleModal2"
                        style="--bs-btn-padding-y: .25rem; --bs-btn-padding-x: .5rem; --bs-btn-font-size: .75rem;">確定上傳照片<i
                          class="bi bi-image-fill ms-1"></i></button></div>
                  </div>
                </div>
              </div>
            </div>
            <!-- Modal -->
            <div class="modal fade" id="exampleModal2" tabindex="-1" aria-labelledby="exampleModalLabel"
              aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">提示</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"
                      @click="clean"></button>
                  </div>
                  <div class="modal-body">
                    <ul v-if="messagePhoto == ''">訊息加載中...</ul>
                    <ul v-else>{{ messagePhoto }}</ul>

                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="clean">Close</button>
                  </div>
                </div>
              </div>
            </div>







            <div class="row g-3 needs-validation" novalidate>

              <div class="col-md-4 position-relative mb-2">
                <label for="" class="form-label">帳號</label>
                <input type="text" class="form-control" id="acct" :value="acct" disabled>
              </div>
              <div class="col-md-4 position-relative">
                <label for="validationTooltip02" class="form-label">信箱</label>
                <input type="text" class="form-control" id="email" :value="email" disabled>
              </div>
              <div class="col-md-4 position-relative"></div>

              <div class="col-md-4 position-relative">
                <label for="" class="form-label">暱稱</label>
                <input type="text" class="form-control" id="username" :placeholder="username" v-model="vusername"
                  :disabled="isDisabled">
                <div class="invalid-feedback">
                  必填
                </div>
              </div>
              <div class="col-md-4 position-relative">
                <label for="" class="form-label">性別</label>
                <select class="form-select" id="sexabbr" v-model="sexabbr" :disabled="isDisabled">
                  <option>初始設定：{{ sex }}</option>
                  <option value=" ">------更改請選擇下方------</option>
                  <option value="F">女性</option>
                  <option value="M">男性</option>
                  <option value="T">不確定</option>
                </select>
                <div class="invalid-feedback">
                  必填
                </div>
              </div>
              <div class="col-md-4 position-relative">
                <label for="" class="form-label">手機號碼</label>
                <input type="text" class="form-control" id="mobile" :placeholder="mobile" v-model="vmobile"
                  :disabled="isDisabled">
                <div class="invalid-feedback">
                  必填
                </div>
              </div>
              <div class="col-md-4 position-relative">
                <label for="" class="form-label">關係人名稱</label>
                <input type="text" class="form-control" id="ecName" :placeholder="ecName" v-model="vecName"
                  :disabled="isDisabled">
                <div class="invalid-feedback">
                  必填
                </div>
              </div>
              <div class="col-md-4 position-relative">
                <label for="" class="form-label">關係</label>
                <input type="text" class="form-control" id="relationship" :placeholder="relationship"
                  v-model="vrelationship" :disabled="isDisabled">
                <div class="invalid-feedback">
                  必填
                </div>
              </div>
              <div class="col-md-4 position-relative">
                <label for="" class="form-label">關係人電話</label>
                <input type="text" class="form-control" id="tel" :placeholder="tel" v-model="vtel" :disabled="isDisabled">
                <div class="invalid-feedback">
                  必填
                </div>
              </div>
              <div class="d-grid gap-2 col-8 mx-auto mt-5">
                <button class="btn btn-outline-primary" @click="toggleDisabled">修改</button>
                <button class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#exampleModal"
                  @click="submitReg" :disabled="isDisabled">確認送出修改</button>
              </div>

              <!-- <div>目前{{datas}}</div> -->
              <!-- Modal -->
              <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                aria-hidden="true">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h1 class="modal-title fs-5" id="exampleModalLabel">訊息</h1>
                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"
                        @click="clean"></button>
                    </div>
                    <div class="modal-body">
                      <ul v-for="(value, key) in message" :key="key">
                        <li v-if="value == null">訊息加載中...</li>
                        <li v-if="value.isnull">{{ value.isnull }}</li>
                        <li v-if="value.username">{{ value.username }}</li>
                        <li v-if="value.mobile">{{ value.mobile }}</li>
                      </ul>
                      <!-- <ul v-if="messageyes==''">訊息加載中...</ul>
        <ul v-else>{{messageyes}}</ul> -->
                      <ul v-if="messageyes">{{ messageyes }}</ul>

                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"
                        @click="clean">Close</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

        </div>


        <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab" tabindex="0">
          <div class="card-body">
            <div class="col-md-4 position-relative mb-3">
              <label for="validationTooltip01" class="form-label">帳號</label>
              <input type="text" class="form-control" id="acct" :placeholder="acct" disabled>
            </div>
            <div class="row g-3 needs-validation " novalidate>
              <div class="col-md-4 position-relative">
                <label for="validationTooltip05" class="form-label">原密碼</label>
                <input type="password" class="form-control" id="pwd" v-model="pwd" placeholder="6-15位中英字">
                <div class="invalid-feedback">
                  必填
                </div>
              </div>
              <div class="col-md-4 position-relative">
                <label for="validationTooltip05" class="form-label">新密碼</label>
                <input type="password" class="form-control" id="newPwd" v-model="newPwd" placeholder="6-15位中英字">
                <div class="invalid-feedback">
                  必填
                </div>
              </div>
              <div class="col-md-4 position-relative">
                <label for="validationTooltip06" class="form-label">再次確認密碼</label>
                <input type="password" class="form-control" id="reNewPwd" v-model="reNewPwd" placeholder="6-15位中英字">
                <div class="invalid-feedback">
                  必填
                </div>
              </div>
              <div class="d-grid gap-2 col-8 mx-auto mt-5">
                <button class="btn btn-outline-primary" @click="submitReg2" data-bs-toggle="modal"
                  data-bs-target="#exampleModal3">確認送出修改</button>
              </div>
            </div>
          </div>
          <!-- Modal -->
          <div class="modal fade" id="exampleModal3" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <h1 class="modal-title fs-5" id="exampleModalLabel">訊息</h1>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"
                    @click="clean"></button>
                </div>
                <div class="modal-body">
                  <ul v-if="messagePwd == ''">訊息加載中...</ul>
                  <ul v-else>{{ messagePwd }}</ul>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="clean">Close</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
    
<script setup >
import { ref, reactive, onBeforeMount, onMounted, onBeforeUpdate, onUpdated } from 'vue';
// import imagePath from '@/assets/sphoto.png';
const imagePath = ref('@/assets/sphoto.png');
const selectedFile = ref(null);
const messagePhoto = ref('');

//抓圖片資料
const handleFileUpload = (e) => {
  const file = e.target.files[0];
  selectedFile.value = file;
  console.log("資料" + selectedFile.value + file)
};
//傳送圖片到雲端
const uploadFile = async () => {
  if (selectedFile.value != null) {
    const formData = new FormData();
    formData.append('image', selectedFile.value);
    formData.append('id', id.value);

    try {
      const response = await fetch('http://localhost:8084/easystep/Cloud/upload2', {
        method: 'POST',
        body: formData,
      });


      const data = await response.text();
      if (data != null) {
        const transformations = "c_fill,h_300,w_300";
        const newUrl = data.replace("/upload/", "/upload/" + transformations + "/")
        imagePath.value = newUrl;
        messagePhoto.value = "圖檔上傳成功";
        console.log("圖檔上傳成功")
      }
    } catch (error) {
      console.error(error);
    }
  } else {
    messagePhoto.value = "請選擇圖檔上傳";
    console.log("請選擇圖檔上傳")
  }
};

const potoData = async () => {
  const URL = `http://localhost:8084/easystep/Cloud/getphoto`
  const response = await axios.get(URL, {
    withCredentials: true,
  })
  const transformations = "c_fill,h_300,w_300";
  const data = response.data;
  const newUrl = data.replace("/upload/", "/upload/" + transformations + "/");
  console.log("photo:" + response.data)
  imagePath.value = newUrl;
}

//設置按鈕顯示能否修改
const isDisabled = ref(true);
const toggleDisabled = () => {
  isDisabled.value = !isDisabled.value; // 切換 isDisabled 變數的值
};

//清除彈跳訊息
const clean = () => {
  message.value = "";
  messageyes.value = "";
  messagePwd.value = "";
  messagePhoto.value = "";
}


import axios from 'axios';
const datas = ref([]); // 用于显示消息的变量
//資料庫(不給修改的)
const id = ref('');
const acct = ref('');
const email = ref('');
const username = ref('');
const tel = ref('');
const mobile = ref('');
const relationship = ref('');
const ecName = ref('');
const sexabbr = ref(' ');
const sex = ref('');
//抓取session值當背景
const loadData = async () => {
  const URL = `http://localhost:8084/easystep/Traveler/getlogin`
  const response = await axios.get(URL, {
    withCredentials: true,
  })
  console.log(response.data);
  datas.value = response.data;
  id.value = response.data.travelerid;
  acct.value = response.data.acct;
  email.value = response.data.email;
  mobile.value = response.data.mobile;
  tel.value = response.data.tel;
  relationship.value = response.data.relationship;
  ecName.value = response.data.ecname;
  username.value = response.data.username;
  if (response.data.sexabbr == "M") {
    sex.value = "男性"
  } else if (response.data.sexabbr == "F") {
    sex.value = "女性"
  } else {
    sex.value = "不確定"
  }

  // sex.value=response.data.sexabbr;
  sexabbr.value = response.data.sexabbr;

}


const vusername = ref('');
const vtel = ref('');
const vmobile = ref('');
const vrelationship = ref('');
const vecName = ref('');
//抓取session值當value（不用一直刪
const vloadData = async () => {
  const URL = `http://localhost:8084/easystep/Traveler/getlogin`
  const response = await axios.get(URL, {
    withCredentials: true,
  })

  vmobile.value = response.data.mobile;
  vtel.value = response.data.tel;
  vrelationship.value = response.data.relationship;
  vecName.value = response.data.ecname;
  vusername.value = response.data.username;
}


onMounted(() => {
  loadData();
  vloadData();
  potoData();
})



const message = ref([]);
const messageyes = ref('');
//上傳修改的內容
const submitReg = async () => {
  const updatas = reactive({
    id: id.value,
    acct: acct.value,
    username: vusername.value,
    tel: vtel.value,
    mobile: vmobile.value,
    sexabbr: sexabbr.value,
    ecName: vecName.value,
    relationship: vrelationship.value
  })
  const url = 'http://localhost:8084/easystep/Traveler/edit';
  const response = await axios.put(url, updatas);
  console.log('更新內容：' + response);
  console.log('內容：' + updatas.acct);


  if (response.data.yes == "基本資料修改成功") {
    messageyes.value = response.data.yes;
    loadData();
    vloadData();
  } else {
    message.value = response.data.no;
  }
}


const pwd = ref('');
const newPwd = ref('');
const reNewPwd = ref('');
const messagePwd = ref('');
//上傳修改的密碼
const submitReg2 = async () => {
  const updatas = reactive({
    id: id.value,
    pwd: pwd.value,
    newPwd: newPwd.value,
    reNewPwd: reNewPwd.value
  })
  const url = 'http://localhost:8084/easystep/Traveler/editPwd';
  const response = await axios.put(url, updatas);
  console.log('修改密碼：' + response);

  messagePwd.value = response.data.pwd;

}


</script>
    
<style scoped>
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