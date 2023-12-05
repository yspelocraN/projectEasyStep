import { createRouter, createWebHistory } from 'vue-router'
import VenderRegister from '../views/VenderRegister.vue'
import VenderLog from '../views/VenderLog.vue'
import VenderUpdate from '../views/VenderUpdate.vue'
import TravelerLog from '../views/TravelerLog.vue'
import TravelerUpdate from '../views/TravelerUpdate.vue'
import TravelerRegister from '../views/TravelerRegister.vue'
import Home from '../views/Home.vue'
import Myblog from '../views/MyPage.vue';
import Photo from '../views/Photo.vue';
import Article from '../views/Article.vue';
import search from '../views/SearchResults.vue';
import LikeVender from '../views/LikeVender.vue';
import TravelerList from '../views/TravelerList.vue';

import VenderList from '@/views/venderList.vue'
import Vender from '../views/Vender.vue'
import Infomation from '@/components/VenderInfomation.vue'
import Photos from '@/components/VenderPhotos.vue'
import Feedbacks from '@/components/VenderFeedbacks.vue'
import TMessages from '../views/TMessages.vue'
import VMessages from '../views/VMessages.vue'
import AddVenderInformation from '@/views/AddVenderInformation.vue'
import AddFeedback from '@/views/Addfeedback.vue'
import FAQ from '../views/FAQ.vue'

import Admin                from '../views/Admin.vue'
import AdminLogin           from '../views/AdminLogin.vue'
import AdminTravelerManage  from '../views/AdminTravelerManage.vue'
import AdminVenderManage    from '../views/AdminVenderManage.vue'
import Tips                 from '../views/Tips.vue'

const routes = [
  {
    path: '/FAQ',
    component: FAQ
  },
  {
    path: '/Vender/VenderRegister',
    component: VenderRegister
  },
  {
    path: '/Vender/VenderLog',
    component: VenderLog
  },
  {
    path: '/Vender/VenderUpdate',
    component: VenderUpdate,
    meta: {
      requiresAuth: true, // 这个路由需要身份验证
      loginPage: '/Vender/VenderLog'
    },
  },
  {
    path: '/Traveler/TravelerLog',
    component: TravelerLog
  },
  {
    path: '/Traveler/TravelerUpdate',
    component: TravelerUpdate,
    meta: {
      requiresAuth: true, // 这个路由需要身份验证
      loginPage: '/Traveler/TravelerLog'
    },
  },
  {
    path: '/Traveler/TravelerRegister',
    component: TravelerRegister
  },
  {
    path: '/',
    component: Home
  },
  {
    //相簿
    path: '/photo/:travelerId',
    component: Photo,
    props: true
  },
  {
    //個人主頁
    //透過網址搜尋該頁面
    path: '/blog/:travelerId',
    component: Myblog,
    props: true
  },
  {
    //網誌
    path: '/article/:travelerId',
    component: Article,
    props: true
  },
  {
    path: '/traveler/list',
    component: TravelerList
  },
  {
    path: '/search',
    component: search
  },
  // 進入廠商頁面
  {
    path: '/vender/:id',
    component: Vender,
    children: [
      {
        path: '',
        component: Infomation,
      },
      {
        path: 'infomation',
        component: Infomation,
      },
      {
        path: 'photos',
        component: Photos,
      },
      {
        path: 'feedbacks',
        component: Feedbacks,
      },

    ],
  },
  // 進入廠商列表
  {
    path: '/venderList',
    component: VenderList
  },
  // 進入聊天室
  {
    path: '/tmessages',
    component: TMessages,
  },
  {
    path: '/vmessages',
    component: VMessages,
  },
  // 進入新增廠商資訊
  {
    path: '/addVenderInformation',
    component: AddVenderInformation,
  },
  // 進入新增廠商回饋
  {
    path: '/addFeedback/:id',
    component: AddFeedback,
  },
  {
    //個人收藏
    path: '/Traveler/LikeVender',
    component: LikeVender
  },

  /* 旅行小訣竅 */
  {
    path: '/tips',
    component: Tips
  },
  /* Administrator */
  {
    path: '/admin',
    component: Admin
  },
  {
    path: '/admin/login',
    component: AdminLogin
  },
  {
    path: '/admin/travelerManage',
    component: AdminTravelerManage
  },
  {
    path: '/admin/venderManage',
    component: AdminVenderManage
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})


router.beforeEach((to, from, next) => {
  if (to.name === 'yourRouteName') {
    // 添加路由进入的动画类
    to.meta.animation = 'fade-enter';
    from.meta.animation = 'fade-leave-to';
  } else {
    // 设置默认的动画类
    to.meta.animation = 'fade-enter';
    from.meta.animation = 'fade-leave-to';
  }
  next();
});


export default router
