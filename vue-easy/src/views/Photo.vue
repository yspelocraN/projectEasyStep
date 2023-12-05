<script setup>
import changebotton from '../components/changebotton.vue';
import createbotton from '../components/createbotton.vue';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
import Swal from 'sweetalert2';


const photos = ref([]);
const selectedAlbum = ref('');
const Album = ref('');
const user = ref('');
const route = useRoute();
const path = user.value = route.params.travelerId;

// 取得每個相簿的第一張相片的 URL
const albumCoverUrls = ref([]);

//取得session
const travelerid = sessionStorage.getItem('travelerid');
const login = sessionStorage.getItem('traveler');


// 取得所有該旅者的相簿和相片
const download = async () => {
  const URL = `${import.meta.env.VITE_API_easystep}/photo/${path}`;
  try {
    const response = await axios.get(URL, {
      withCredentials: true,
      cache: false,
    });

    // 保存相簿信息
    Album.value = response.data;

    // 取得每個相簿的第一張相片的 URL
    albumCoverUrls.value = await Promise.all(
      response.data.map(async (album) => {
        // 獲取相簿的相片
        const photosResponse = await axios.get(
          `${import.meta.env.VITE_API_easystep}/photo/showphoto?albumId=${album.albumId}`,
          { withCredentials: true, cache: false }
        );

        // 如果有相片，返回第一張相片的 URL，否則返回一個預設 URL
        if (photosResponse.data.length > 0) {
          return photosResponse.data[0].photoURL;
        } else {
          return 'https://res.cloudinary.com/dciwmbvcl/image/upload/v1698651546/hn2wmztbc8lxvwrzf24k.jpg'; // 將這個 URL 替換為你的預設圖片 URL
        }
      })
    );

  } catch (error) {
    console.error(error);
  }
};

onMounted(() => {
  download();
});

//根據相簿ID取得該相簿所有照片
const showphoto = async (albumId) => {
  const URL = `${import.meta.env.VITE_API_easystep}/photo/showphoto?albumId=${albumId}`;
  try {
    const response = await axios.get(URL, {
      withCredentials: true,
      cache: false,
    });
    photos.value = response.data;
    console.log(response.data);
  } catch (error) {
    console.error(error);
  }
};

const showThisPhoto = (photoURL) => {
  window.open(photoURL, '_blank');

}

//刪除前詢問
const daleteAsk = (albumId) => {
  Swal.fire({
    title: '確定要刪除嗎?',
    text: "消失的紀錄將不再回來",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Yes',
  }).then((result) => {
    if (result.isConfirmed) {
      Swal.fire({
        title: '刪除成功!',
        text: '您徹底忘了一件事',
        icon: 'success',
        showConfirmButton: true,
      }).then(() => {
        // 用户确认后执行删除操作
        deleteArt(albumId);
        console.log('刪除操作已完成');
      });
    }
  });
}


//刪除相簿
const deleteArt = async (albumId) => {
  const URL = `${import.meta.env.VITE_API_easystep}/photo/deleteAlbum/${albumId}`;
  try {
    await axios.delete(URL, {
      withCredentials: true,
      cache: false,
    });

  } catch (error) {


  }
  closeEditModal();
};
//關閉model刷新頁面
const closeEditModal = () => {
  window.location.reload();
};

//上傳圖片
const uploadPhotos = async (albumId) => {
  // 顯示 loading 畫面
  Swal.fire({
    title: '上傳中...',
    allowOutsideClick: false,
    onBeforeOpen: () => {
      Swal.showLoading();
    }
  });

  try {
    const { value: files } = await Swal.fire({
      title: '請選擇上傳的圖片',
      input: 'file',
      inputAttributes: {
        'accept': 'image/*',
        'aria-label': 'Upload your profile picture',
        'multiple': true, // 允許多選
      },
      showCancelButton: true, // 显示取消按钮

    });

    if (files && files.length > 0) {
      const formData = new FormData();
      formData.append('albumId', albumId);

      for (let i = 0; i < files.length; i++) {
        formData.append('images', files[i]);
      }

      await axios.post(`${import.meta.env.VITE_API_easystep}/photo/upload`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });

      Swal.fire({
        title: '上傳成功',
        icon: 'success',
      }).then(() => {
        closeEditModal();
      });
    } else {
      Swal.fire({
        title: '請選擇要上傳的文件',
        icon: 'warning',
      });
    }
  } catch (error) {
    console.error('上傳失敗', error);
  }
}

</script>

<template>
  <div class="container">
    <changebotton></changebotton>
    <createbotton v-if="travelerid === path && login"></createbotton>
    <!--for迴圈產生相簿-->
    <div class="album py-5 bg-body-tertiary">
      <div class="container">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
          <div class="col" v-for="(album, index) in Album" :key="album.albumId">
            <div class="card shadow-sm">
              <div class="card-img-top" style="width: 100%; height: 225px; background-color: #55595c;"
                v-if="!albumCoverUrls[index]">
                <text x="50%" y="50%" fill="#eceeef" dy=".3em">{{ album.albumName }}</text>
              </div>
              <img :src="albumCoverUrls[index]" class="card-img-top" alt="album cover" width="100%" height="225" v-else>
              <div class="card-body">
                <p class="card-text">{{ album.albumName }}</p>
                <div class="d-flex justify-content-between align-items-center">
                  <div class="btn-group">
                    <button type="button" class="btn btn-sm btn-outline-secondary"
                      @click="showphoto(album.albumId); selectedAlbum = album" data-bs-toggle="modal"
                      :data-bs-target="'#exampleModal'">
                      View
                    </button>
                    <button type="button" class="btn btn-sm btn-outline-secondary" v-if="travelerid === path && login"
                      @click="showphoto(album.albumId); selectedAlbum = album" data-bs-toggle="modal"
                      :data-bs-target="'#EditModel'">Edit
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>


    <!--Model-->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true"
      data-bs-backdrop="static">
      <div class="modal-dialog modal-xl">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">{{ selectedAlbum.albumName }}</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="album py-5 bg-body-tertiary">
              <div class="container">
                <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                  <div class="card mb-3 " style="width: 200px; height: 200px; border-radius: 40px; margin-left: 15px;"
                    v-for="photo in photos" :key="photo.photoId">

                    <img :src="photo.photoURL" class="card-img-top" alt="..."
                      style="width: 100%; height: 100%; border-radius: 40px;" @click="showThisPhoto(photo.photoURL)">

                  </div>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
          </div>
        </div>
      </div>
    </div>


    <!--EditModel-->

    <div class="modal fade" id="EditModel" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true"
      data-bs-backdrop="static">
      <div class="modal-dialog modal-xl">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">{{ selectedAlbum.albumName }}</h5>
            <button type="button" class="btn btn-primary" style="margin-left: 20px;"
              @click="uploadPhotos(selectedAlbum.albumId)">上傳照片</button>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="album py-5 bg-body-tertiary">
              <div class="container">
                <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                  <div class="card mb-3 " style="width: 200px; height: 200px; border-radius: 40px; margin-left: 15px;"
                    v-for="photo in photos" :key="photo.photoId">

                    <img :src="photo.photoURL" class="card-img-top" alt="..."
                      style="width: 100%; height: 100%; border-radius: 40px;" @click="showThisPhoto(photo.photoURL)">

                  </div>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-danger" @click="daleteAsk(selectedAlbum.albumId)">刪除相簿</button>
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<style scoped></style>