<template>
    <div class="container">
        <br>

        <div class="d-flex justify-content-center">
            <h1 class="card-title mb-5 mt-4">旅行小訣竅</h1>
        </div>
        
        <div v-if="this.adminLoginStatus == true || this.adminLoginStatus == 'true'" class="contenter" role="group" aria-label="Basic example">
            <button type="button" class="btn btn-outline-primary" data-bs-toggle="collapse" data-bs-target="#addTip" aria-expanded="false" aria-controls="addTip">
                新增旅行訣竅&nbsp;&nbsp;&nbsp;+
            </button>
            
            <div class="container collapse" id="addTip">
                <br>
                <form>
                    <div class="mb-3">
                        <label for="title" class="form-label">標題：</label>
                        <input type="text" class="form-control" id="title" v-model="this.new.title">
                    </div>
                    
                    <div class="mb-3">
                        <label for="details" class="form-label">內容：</label>
                        <input type="text" class="form-control" id="details" v-model="this.new.details">
                    </div>
                    
                    <button type="reset" class="btn btn-outline-danger" @click="clearNew">清除</button>
                    <span>&nbsp; &nbsp;</span>
                    <button type="submit" class="btn btn-outline-success" @click.prevent="createTip">
                        新增訣竅&nbsp;&nbsp;&nbsp;+
                    </button>
                    <hr>
                </form>
            </div>
        </div>

        <div v-if="this.adminLoginStatus == true || this.adminLoginStatus == 'true'">
            <br><br>
        </div>

        <div class="accordion" id="accordionExample">
            <div class="accordion-item" v-for="tip in this.tips" :key="tip.tipId">
                
                <h2 class="accordion-header">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse" :data-bs-target="target(tip.tipId)" aria-expanded="false" :aria-controls="getIdStr(tip.tipId)">
                        <strong>{{ tip.title }}</strong>
                    </button>
                </h2>
                
                <div :id="getIdStr(tip.tipId)" class="accordion-collapse collapse show" data-bs-parent="#accordionExample">
                    <div class="accordion-body">
                        {{ tip.details }}
                    </div>

                    <div v-if="this.adminLoginStatus == true || this.adminLoginStatus == 'true'" class="contenter" role="group" aria-label="Basic example">
                        <span>&nbsp;&nbsp;</span>

                        <button type="button" class="btn btn-outline-danger" @click.prevent="deleteTip(tip.tipId)">刪除</button>
                        
                        <span>&nbsp;&nbsp;</span>
                        
                        <button type="button" class="btn btn-outline-warning buttonSelected" data-bs-toggle="collapse" :data-bs-target="targetEdit(tip.tipId)" aria-expanded="false" :aria-controls="getEditId(tip.tipId)">
                            編輯
                        </button>
                        
                        <div class="container collapse" :id="getEditId(tip.tipId)">
                            <hr>

                            <form>
                                <div class="mb-3">
                                    <label for="floatingInput" class="form-label">
                                        原標題：&nbsp;<span>{{ tip.title }}</span>
                                    </label>
                                    <input type="text" class="form-control" :id="getEditTitle(tip.tipId)" v-model="this.edit.title">
                                </div>
                                
                                <div class="mb-3">
                                    <label :for="getEditDetails(tip.tipId)" class="form-label">
                                        原內容：
                                        <br>
                                        {{ tip.details }}
                                    </label>
                                    <input type="text" class="form-control" :id="getEditTitle(tip.tipId)" v-model="this.edit.details">
                                </div>

                                <button type="reset" class="btn btn-outline-danger" @click="clearEdit">清除</button>
                                <span>&nbsp; &nbsp;</span>
                                <button type="submit" class="btn btn-outline-success" @click.prevent="editTip(tip.tipId, tip.title, tip.details)">更新</button>
                            </form>

                            <hr>
                        </div>

                        <p></p>
                    </div>
                </div>

            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Tips',

    data() {
        return {
            tips: [],
            edit: {
                tipId:      0,
                title:      '',
                details:    ''
            },
            new: {
                title:      '',
                details:    ''
            },
            adminLoginStatus: false,
            pageReload: '0'
        }
    },

    created() {
        this.chkAdminLogin()
        this.getTips()
        this.chkPageReload()
    },

    methods: {
        chkAdminLogin() {
            if (sessionStorage.getItem('adminLoginStatus') != null) {
				this.adminLoginStatus = sessionStorage.getItem('adminLoginStatus')
			}
        },

        chkPageReload() {
            this.pageReload = sessionStorage.getItem('reloadPage')
            if (this.pageReload != '1') {
                window.location.reload()
                sessionStorage.setItem('reloadPage', '1')
            }
        },

        getTips() {
            fetch('http://localhost:8084/easystep/admin/tips', {
                method: 'GET'
            })
            .then(res => res.json())
            .then(data => {
                this.tips = data
            })
        },

        createTip() {
            if (    !(this.new.title == null || this.new.title == '')
                &&  !(this.new.details == null || this.new.details == '')) {
                fetch('http://localhost:8084/easystep/admin/createTip', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(this.new)
                })
                .then(() => {
                    setTimeout(() => {
                        this.clearNew()
                        this.getTips()
                    }, 1000);
                })
                .then(() => {
                    window.location.reload()
                })
            }
        },

        clearNew() {
            this.new.title = ''
            this.new.details = ''
        },

        editTip(tipId, title, details) {
            this.edit.tipId = tipId;
            if (this.edit.title == null || this.edit.title == '') {
                this.edit.title = title
            }
            if (this.edit.details == null || this.edit.details == '') {
                this.edit.details = details
            }
            
            fetch(`http://localhost:8084/easystep/admin/editTip`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(this.edit)
            })
            .then(() => {
                setTimeout(() => {
                    this.getTips()
                }, 1000);
            })
            .then(() => {
                window.location.reload()
            })
        },

        clearEdit() {
            this.edit.title = ''
            this.edit.details = ''
        },

        deleteTip(tipId) {
            fetch(`http://localhost:8084/easystep/admin/deleteTip/${tipId}`, {
                method: 'DELETE'
            })
            .then(() => {
                this.getTips()
            })
            .then(() => {
                window.location.reload()
            })
        },

        getIdStr(id) {
            return `tip_${id}`
        },

        target(id) {
            return `#${this.getIdStr(id)}`
        },

        getEditId(id) {
            return `editTip_${id}`
        },

        targetEdit(id) {
            return `#${this.getEditId(id)}`
        },

        getEditTitle(id) {
            return `title_${id}`
        },

        getEditDetails(id) {
            return `details_${id}`
        },
    }
}
</script>

<style>
.buttonSelected:hover {
    color: aliceblue;
}
</style>