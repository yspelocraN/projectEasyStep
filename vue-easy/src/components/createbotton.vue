<template>
  <div>
    <button @click="showInputDialog" class="btn btn-outline-primary">建立相簿</button>
  </div>
</template>
  

<script>
const javaApiUrl = import.meta.env.VITE_API_easystep;
const travelerid = sessionStorage.getItem('travelerid');

export default {
  methods: {
    showInputDialog() {
      this.$swal({
        title: '請輸入相簿名稱',
        input: 'text',
        inputAttributes: {
          autocapitalize: 'off',
        },
        showCancelButton: true,
        confirmButtonText: '確認',
        cancelButtonText: '關閉',
        inputValidator: (value) => {
          if (!value) {
            return '相簿名稱不能為空';
          }
        },
      }).then((result) => {
        if (result.isConfirmed) {
          const albumName = result.value;
          const travelerId = travelerid; // 讀取旅者ID 目前test使用

          // 發送post請求給後端
          fetch(`${javaApiUrl}/album/create?albumName=${albumName}&travelerid=${travelerId}`, {
            method: 'POST',
          })
            .then((response) => {
              if (response.status === 200) {
                return response.text();
              } else if (response.status === 404) {
                throw new Error('伺服器錯誤');
              } else {
                throw new Error('未知錯誤');
              }
            })
            .then((data) => {
              this.$swal({
                title: '相簿創建成功',
                text: data,
                icon: 'success',
                confirmButtonText: 'OK',
                footer: '', // 将 footer 设置为空字符串，隐藏左下角的按钮
              }).then(() => {
                location.reload(); // 在用户点击"OK"按钮后刷新页面
              });
            })
            .catch((error) => {
              console.error('請求失敗:', error);
              this.$swal('相簿創建失敗');
            });
        }
      });
    },
  },
};
</script>
  