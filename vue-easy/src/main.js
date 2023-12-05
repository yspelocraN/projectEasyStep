
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';
import vue3GoogleLogin from 'vue3-google-login'



const app = createApp(App);
app.use(vue3GoogleLogin, {
    clientId: '645477542657-go2vfvs7ltri1263ebdekemhnvd40moh.apps.googleusercontent.com'
})
app.use(router);
app.use(VueSweetalert2);


app.mount('#app');
