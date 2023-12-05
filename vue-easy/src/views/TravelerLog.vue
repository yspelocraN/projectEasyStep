<template>
  <div>
    <div class="card mt-5 mx-auto" style="max-width: 800px;">
      <div class="row gx-3">
        <div class="col-md-6">
          <img :src="imagePath" class="img-fluid rounded-start" alt="...">
        </div>
        <div class="col-md-6">
          <div class="card-body mt-4">
            <h1 class="card-title mb-5 mt-4">小幫手登入</h1>
            <div class="form-floating mb-3">
              <input type="text" class="form-control" v-model="acct" id="acct">
              <label for="floatingInput">帳號</label>

            </div>
            <div class="form-floating mb-4">
              <input type="password" class="form-control" v-model="pwd" id="pwd">
              <label for="floatingPassword">密碼</label>

            </div>
            <div class="d-grid gap-2 col-8 mx-auto mb-2">
              <div>{{ message }}</div>

              <button @click="submitForm" class="btn btn-outline-primary">送出</button>
            </div>
            <div class="d-grid gap-2 col-8 mx-auto mb-2">
              <span class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#exampleModal"
                data-bs-whatever="@mdo">忘記密碼</span>
            </div>
            <hr />
            <div class="d-grid gap-2 col-8 mx-auto mb-2 justify-content-center ">
              <h5>使用其他帳戶登入</h5>
            </div>
            <div class="d-grid gap-2 col-8 mx-auto  justify-content-center ">
              <GoogleLogin :callback="callback" prompt />
            </div>
          </div>
        </div>
      </div>
    </div>


    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="exampleModalLabel">忘記密碼</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>

          <div class="modal-body">
            <div class="mb-3">
              <label for="recipient-name" class="col-form-label">帳號:</label>
              <input type="text" class="form-control" v-model="acctPwd" id="acctPwd">
            </div>
            <div class="mb-3">
              <label for="recipient-name" class="col-form-label">信箱:</label>
              <input type="text" class="form-control" v-model="emailPwd" id="emailPwd">
            </div>

          </div>
          <div class="modal-footer">
            <div>{{ messagePwd }}</div>
            <button class="btn btn-primary" id="send" @click="submitFoget">Send message</button>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>
    
<script setup>

import imagePath from '@/assets/traveler01.jpg';
import { ref } from 'vue'
import { useRouter } from 'vue-router';
import axios from 'axios';
import { decodeCredential } from 'vue3-google-login'
import eventBus from './../js/EventBus';
const router = useRouter();
const sub = ref('');

//google登入
const callback = (response) => {
  const userData = decodeCredential(response.credential)
  console.log(userData);
  sub.value = userData.sub;
  const submitGoogle = async () => {
    const URL = `http://localhost:8084/easystep/Traveler/googlelogin`
    const response = await axios.post(URL, {
      sub: sub.value,
    }, {
      withCredentials: true,
    })
    const saveToSessionStorage = () => {
      sessionStorage.setItem('traveler', true);
    };
    console.log(response)
    if (response.data == '旅者登入成功') {
      travelerUser.value = "旅者登入成功"
      eventBus.emit('userLoggedIn', travelerUser.value);
      trdownload();
      saveToSessionStorage();
      setTimeout("location.href='/'", 1000);
    } else {
      message.value = '登入失敗請先註冊';
      alert('登入失敗請先註冊')
    }

  };
  submitGoogle()
}

const trdownload = async () => {
  const URL = `http://localhost:8084/easystep/Traveler/getlogin`
  const response = await axios.get(URL, {
    withCredentials: true,
    cache: false
  })
  console.log(response.data.infoDTo)
  const userId = response.data.travelerid; // 登入後取得ID
  sessionStorage.setItem('travelerid', userId);//設置一個session辨認會員
  travelername.value = response.data.username;
}

const travelerUser = ref('');
const message = ref(''); // 用于显示消息的变量
const acct = ref(''); // 添加 ref 变量用于双向绑定
const pwd = ref('');  // 添加 ref 变量用于双向绑定
const submitForm = async () => {
  const URL = `http://localhost:8084/easystep/Traveler/login`
  const response = await axios.post(URL, {
    acct: acct.value,
    pwd: pwd.value,
  }, {
    withCredentials: true,
  })
  const saveToSessionStorage = () => {
    sessionStorage.setItem('traveler', true);
  };
  console.log(response)
  if (response.data == '旅者登入成功') {
    // message.value = '登入成功';
    travelerUser.value = "旅者登入成功"
    // router.push('/');
    eventBus.emit('userLoggedIn', travelerUser.value);
    saveToSessionStorage();
    trdownload();
    setTimeout("location.href='/'", 1000);
  } else {
    message.value = '登入失敗';
    console.log('登入失敗')
  }

};



const messagePwd = ref('');
const acctPwd = ref('');
const emailPwd = ref('');
const submitFoget = async () => {
  // 模拟与后端的通信，这里使用setTimeout来模拟一个异步操作
  try {
    // 模拟向后端发送账号和密码
    const response = await fetch('http://localhost:8084/easystep/Traveler/checkpwd', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: new URLSearchParams({
        acctPwd: acctPwd.value,
        emailPwd: emailPwd.value,
      }),
    });


    const data = await response.text();
    // 根据后端响应设置消息
    if (data == '已寄信到您的信箱') {
      messagePwd.value = '已寄信到您的信箱';
    } else {
      messagePwd.value = '輸入錯誤';
    }
  } catch (error) {
    console.error(error);
    messagePwd.value = '輸入錯誤';
  }
  // 模拟一个异步请求
};
</script>
<style scoped></style>