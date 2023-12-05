<template>
    <div class="container form-signin w-100 m-auto">

        <div class="container" v-if="this.adminLoginStatus == true || this.adminLoginStatus == 'true'">
            <br><br><br><br><br>

            <div class="d-flex justify-content-center">
                <h1>登入成功，3秒後自動跳轉畫面至主頁</h1>
            </div>
            
            <br><br><br><br><br>
        </div>

        <div v-else class="card mt-5 mx-auto mb-5" style="max-width: 800px;">
            <div class="row gx-3">
                <div class="col-md-6">
                <img :src="imgPath" class="img-fluid rounded-start" alt="...">
                </div>

                <div class="col-md-6">
                    <div class="card-body mt-4">
                        <div class="d-flex justify-content-center">
                            <h1 class="card-title mb-5 mt-4">管理者帳號登入</h1>
                        </div>
                        
                        <br>

                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" id="floatingInput" placeholder="Account" v-model="admin.adminAcct">
                            <label for="floatingInput">帳號</label>
                        </div>

                        <div class="form-floating mb-4">
                            <input type="password" class="form-control" id="floatingPassword" placeholder="Password" v-model="admin.adminPwd">
                            <label for="floatingPassword">密碼</label>
                        </div>

                        <p style="color: red;" v-if="this.chkLoginError == true || this.chkLoginError == 'true'">帳號或密碼錯誤</p>
                        <p v-else>&nbsp;</p>
                        
                        <div class="d-grid gap-2 col-8 mx-auto mb-2">
                            <button class="btn btn-outline-secondary w-100 py-2" type="submit" @click.prevent="adminLogin">管理者登入</button>
                        </div>
                        
                        <br>

                        <hr />

                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import imgPath from '@/assets/admin.jpg'

export default {
    name: 'AdminLogin',

    data() {
        return {
            admin: {
                adminAcct:    '',
                adminPwd:     ''
            },
            adminLoginStatus: false,
            imgPath: imgPath,
            chkLoginError: false,
        }
    },

    created() {
        this.chkAdminLogin()
    },

   methods: {
        chkAdminLogin() {
			if (sessionStorage.getItem('adminLoginStatus') != null) {
				this.adminLoginStatus = sessionStorage.getItem('adminLoginStatus')
			}
            if (this.adminLoginStatus == true || this.adminLoginStatus == 'true') {
                this.chkLoginError = false
                this.goHome()
            }
		},

        adminLogin() {
            fetch('http://localhost:8084/easystep/admin/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(this.admin)
            })
            .then(res => res.json())
            .then(data => {
                if (data == true || data == 'true') {
                    sessionStorage.clear()
                    sessionStorage.setItem('adminLoginStatus', data)
                } else {
                    this.chkLoginError = true
                }
            })
            .then(() => {
                this.chkAdminLogin()
            })
        },

        goHome() {
            // console.log(`before goHome sessionStorage.getItem('reloadPage') = ${sessionStorage.getItem('reloadPage')}`)
            if (sessionStorage.getItem('reloadPage') != '0') {
                sessionStorage.setItem('reloadPage', '0')
                // console.log(`after goHome sessionStorage.getItem('reloadPage') = ${sessionStorage.getItem('reloadPage')}`)
            }
            setTimeout("location.href='/'", 3000)
        }
    }
}
</script>

<style>
</style>