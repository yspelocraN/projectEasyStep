<template>
    <div class="container">
        <div class="d-flex justify-content-center">
            <h1 class="card-title mb-5 mt-4">旅人資訊</h1>
        </div>

        <div class="accordion" id="accordionExample" v-if="this.adminLoginStatus == true || this.adminLoginStatus == 'true'">
            <div class="accordion-item" v-for="traveler in travelers" :key="traveler.travelerId">
                
                <h2 class="accordion-header">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse" :data-bs-target="target(traveler.travelerId)" aria-expanded="false" :aria-controls="getIdStr(traveler.travelerId)">
                        <strong>
                            旅人姓名：{{ traveler.travelerName }}
                            <br>
                            旅人帳號：{{ traveler.travelerAcct }}
                        </strong>
                    </button>
                </h2>
                
                
                <div class="container collapse" :id="getIdStr(traveler.travelerId)">
                    <hr>

                    <div class="card card-body">
                        Google ID：{{ traveler.googleId }}
                    </div>

                    <div class="card card-body">
                        註冊狀態：{{ traveler.enable }}
                    </div>

                    <div class="card card-body">
                        旅人性別：{{ traveler.sexAbbr }}
                    </div>

                    <div class="card card-body">
                        旅人電話：{{ traveler.mobile }}
                    </div>

                    <div class="card card-body">
                        旅人信箱：{{ traveler.email }}
                    </div>

                    <div class="card card-body">
                        緊急聯絡人姓名：{{ traveler.ecName }}
                    </div>

                    <div class="card card-body">
                        緊急聯絡人關係：{{ traveler.ecRel }}
                    </div>
                    
                    <div class="card card-body">
                        緊急聯絡人電話：{{ traveler.ecTel }}
                    </div>

                    <br>

                    <div class="btn-group" role="group" aria-label="Basic example">
                        <button type="button" class="btn btn-danger disabled" disabled @click.prevent="deleteTraveler(traveler.travelerId)">帳號刪除</button>

                        <button type="button" v-if="traveler.enable == true || traveler.enable == 'true'" class="btn btn-outline-warning buttonSelected" @click.prevent="suspend(traveler.travelerId)">
                            帳號停權
                        </button>
                        <button type="button" v-else class="btn btn-outline-warning buttonSelected" @click.prevent="restore(traveler.travelerId)">
                            帳號復權
                        </button>
                    </div>

                    <hr>
                    
                </div>

            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'AdminTravelerManage',

    data() {
        return{
            travelers:  [],
            adminLoginStatus: false,
            pageReload: '0'
        }
    },

    created() {
        this.getTravelers()
        this.chkAdminLogin()
        this.chkPageReload()
    },

    methods: {
        chkAdminLogin() {
			if (sessionStorage.getItem('adminLoginStatus') != null) {
				this.adminLoginStatus = sessionStorage.getItem('adminLoginStatus')
			}
            setTimeout(() => {
                // console.log(`chkAdmin: this.venders = ${this.venders}`)
            }, 10000);
		},

        chkPageReload() {
            this.pageReload = sessionStorage.getItem('reloadPage')
            if (this.pageReload != '1') {
                window.location.reload()
                sessionStorage.setItem('reloadPage', '1')
            }
        },
        
        getTravelers() {
            fetch('http://localhost:8084/easystep/admin/travelers', {
                methods: 'GET'
            })
            .then(res => res.json())
            .then(data => {
                this.travelers = data
            })
        },

        deleteTraveler(tId) {
            fetch(`http://localhost:8084/easystep/admin/deleteTraveler/${tId}`, {
                method: "DELETE"
            })
            .then(() => {
                this.getTravelers()
            })
            .then(() => {
                window.location.reload()
            })
        },

        suspend(tId) {
            fetch(`http://localhost:8084/easystep/admin/suspendTraveler/${tId}`, {
                method: "GET"
            })
            .then(() => {
                this.getTravelers()
            })
            .then(() => {
                window.location.reload()
            })
        },
        
        restore(tId) {
            fetch(`http://localhost:8084/easystep/admin/restoreTraveler/${tId}`, {
                method: "GET"
            })
            .then(() => {
                this.getTravelers()
            })
            .then(() => {
                window.location.reload()
            })
        },

        getIdStr(id) {
            return `traveler_${id}`
        },

        target(id) {
            return `#${this.getIdStr(id)}`
        },
    }
}
</script>

<style>
.buttonText {
    color: aliceblue;
    text-align: left;
}

.buttonSelected:hover {
    color: aliceblue;
}
</style>