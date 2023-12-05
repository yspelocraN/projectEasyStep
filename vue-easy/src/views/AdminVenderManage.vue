<template>
    <div class="container">
        <div class="d-flex justify-content-center">
            <h1 class="card-title mb-5 mt-4">廠商資訊</h1>
        </div>
        
        <div class="accordion" id="accordionExample" v-if="this.adminLoginStatus == true || this.adminLoginStatus == 'true'">
            <div class="accordion-item" v-for="vender in venders" :key="vender.venderId">
                
                <h2 class="accordion-header">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse" :data-bs-target="target(vender.venderId)" aria-expanded="false" :aria-controls="getIdStr(vender.venderId)">
                        <strong>
                            廠商姓名：{{ vender.venderName }}
                            <br>
                            廠商帳號：{{ vender.venderAcct }}
                        </strong>
                    </button>
                </h2>
                
                
                <div class="container collapse" :id="getIdStr(vender.venderId)">
                    <hr>

                    <div class="card card-body">
                        註冊狀態：{{ vender.enable }}
                    </div>

                    <div class="card card-body">
                        廠商電話：{{ vender.tel }}
                    </div>

                    <div class="card card-body">
                        廠商住址：
                        <br>
                        {{ findDist(vender.zipno) }}
                        <br>
                        {{ vender.street }}
                    </div>

                    <div class="card card-body">
                        廠商信箱：{{ vender.email }}
                    </div>

                    <div class="card card-body">
                        公司名稱：{{ vender.coname }}
                    </div>
                    
                    <div class="card card-body">
                        統一編號：{{ vender.uninum }}
                    </div>

                    <br>

                    <div class="btn-group" role="group" aria-label="Basic example">
                        <button type="button" class="btn btn-danger disabled" disabled @click.prevent="deleteVenders(vender.venderId)">帳號刪除</button>

                        <button type="button" v-if="vender.enable == true || vender.enable == 'true'" class="btn btn-outline-warning buttonSelected" @click.prevent="suspend(vender.venderId)">
                            帳號停權
                        </button>
                        <button type="button" v-else class="btn btn-outline-warning buttonSelected" @click.prevent="restore(vender.venderId)">
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
import { data as zone } from '../js/taiwan_districts_insert3.js'

export default {
    name: 'AdminVenderManage',

    data() {
        return{
            venders:  [],
            adminLoginStatus: false,
            pageReload: '0'
        }
    },

    created() {
        this.chkAdminLogin()
        this.getVenders()
        this.chkPageReload()
    },

    methods: {
        chkAdminLogin() {
			if (sessionStorage.getItem('adminLoginStatus') != null) {
				this.adminLoginStatus = sessionStorage.getItem('adminLoginStatus')
			}
            setTimeout(() => {
                // console.log(`chkAdmin: this.venders = ${this.venders}`)
            }, 1000);
		},

        chkPageReload() {
            this.pageReload = sessionStorage.getItem('reloadPage')
            if (this.pageReload != '1') {
                window.location.reload()
                sessionStorage.setItem('reloadPage', '1')
            }
        },
        
        getVenders() {
            fetch('http://localhost:8084/easystep/admin/venders', {
                methods: 'GET'
            })
            .then(res => res.json())
            .then(data => {
                this.venders = data
            })
        },

        deleteVenders(vId) {
            fetch(`http://localhost:8084/easystep/admin/deleteVender/${vId}`, {
                method: "DELETE"
            })
            .then(() => {
                this.getVenders()
            })
            .then(() => {
                window.location.reload()
            })
        },

        suspend(vId) {
            fetch(`http://localhost:8084/easystep/admin/suspendVender/${vId}`, {
                method: "GET"
            })
            .then(() => {
                this.getVenders()
            })
            .then(() => {
                window.location.reload()
            })
        },
        
        restore(vId) {
            fetch(`http://localhost:8084/easystep/admin/restoreVender/${vId}`, {
                method: "GET"
            })
            .then(() => {
                this.getVenders()
            })
            .then(() => {
                window.location.reload()
            })
        },

        getIdStr(id) {
            return `vender${id}`
        },

        target(id) {
            return `#${this.getIdStr(id)}`
        },

        findDist(zipCode) {
            let cityName = null
            let distName = null
            let fullName = null

            zone.forEach(element => {
                const district = element.districts.find((district) => district.zip == zipCode)
                if (district) {
                    cityName = element.name
                    distName = district.name
                }
            });
            fullName = zipCode + '\t' + cityName + '\t' + distName

            return fullName
        }
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