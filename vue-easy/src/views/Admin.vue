<template>
    <div class="container">
        <br><br><br><br><br>

        <div class="d-flex justify-content-center">
            <h1>{{ this.message }}</h1>
        </div>
        
        <br><br><br><br><br>
    </div>
</template>

<script>
export default {
    name: 'Admin',

    data() {
        return {
            adminLoginStatus: false,
            message: '尚未登入，三秒後跳轉至超級管理者登入畫面',
            pageReload: '0'
        }
    },

    created() {
        this.chkAdminLogin()
        this.chkPageReload()
    },

    methods: {
        chkAdminLogin() {
            if (sessionStorage.getItem('adminLoginStatus') != null) {
				this.adminLoginStatus = sessionStorage.getItem('adminLoginStatus')
			}
			this.message = (this.adminLoginStatus == true || this.adminLoginStatus == 'true') ? '已登入，三秒後跳轉至主畫面' : '尚未登入，三秒後跳轉至超級管理者登入畫面'
			if (this.adminLoginStatus == true || this.adminLoginStatus == 'true') {
                this.goHome()
            } else {
                this.goAdminLogin()
            }
            // console.log(`adminLoginStatus = ${typeof this.adminLoginStatus} ${this.adminLoginStatus}`)
            // console.log(`message = ${typeof this.message} ${this.message}`)
		},

        chkPageReload() {
            this.pageReload = sessionStorage.getItem('reloadPage')
            // console.log(`pageReload = ${typeof this.pageReload} ${this.pageReload}`)
            if (this.pageReload != '1') {
                window.location.reload()
                sessionStorage.setItem('reloadPage', '1')
            }
        },

        goHome() {
            setTimeout(() => {
                this.$router.push('/')
            }, 3000)
        },

        goAdminLogin() {
            setTimeout(() => {
                this.$router.push('/admin/login')
            }, 3000)
        },
    }
}
</script>

<style>
</style>