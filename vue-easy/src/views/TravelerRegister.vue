<template>
  <div>
    <div class="card mt-5 mx-auto" style="max-width: 800px;">
      <div class="card-header">
        <h1 class="col-md-4 ">註冊專區</h1>
      </div>
      <div class="card-body">
        <div class="row g-3 needs-validation">
          <div class="col-md-4 position-relative">
            <label for="" class="form-label">帳號</label>
            <input required type="text" class="form-control" id="acct" placeholder="6-15位中英字" v-model="acct">
            <div th:text="${acct}?${acct}"></div>
            <div class="invalid-feedback">
              必填
            </div>
          </div>
          <div class="col-md-4 position-relative">
            <label for="" class="form-label">暱稱</label>
            <input required type="text" class="form-control" id="username" v-model="username">
            <div th:text="${username}?${username}"></div>
            <div class="invalid-feedback">
              必填
            </div>
          </div>
          <div class="col-md-4 position-relative">
            <label for="" class="form-label">性別</label>
            <select required class="form-select" id="sexabbr" v-model="sexabbr">
              <option selected value="">請選擇性別</option>
              <option value="F">女性</option>
              <option value="M">男性</option>
              <option value="T">不確定</option>
            </select>
            <div class="invalid-feedback">
              必填
            </div>
          </div>
          <div class="col-md-6 position-relative">
            <label for="" class="form-label">手機號碼</label>
            <input required type="text" class="form-control" id="mobile" placeholder="09xx-xxxxxx" v-model="mobile">
            <div th:text="${mobile}?${mobile}"></div>
            <div class="invalid-feedback">
              必填
            </div>
          </div>
          <div class="col-md-6 position-relative">
            <label for="" class="form-label">信箱</label>
            <input required type="text" class="form-control" id="email" placeholder="xxxx@gmail.com" v-model="email">
            <div th:text="${email}?${email}"></div>
            <div class="invalid-feedback">
              必填
            </div>
          </div>
          <div class="col-md-4 position-relative">
            <label for="" class="form-label">關係人姓名</label>
            <input required type="text" class="form-control" id="recName" v-model="ecName">
            <div class="invalid-feedback">
              必填
            </div>
          </div>
          <div class="col-md-4 position-relative">
            <label for="" class="form-label">關係</label>
            <input required type="text" class="form-control" id="relationship" v-model="relationship">
            <div class="invalid-feedback">
              必填
            </div>
          </div>
          <div class="col-md-4 position-relative">
            <label for="" class="form-label">關係人電話</label>
            <input type="text" class="form-control" id="tel" v-model="tel">
            <div th:text="${tel}?${tel}"></div>
            <div class="invalid-feedback">
              必填
            </div>
          </div>
          <div class="col-md-6 position-relative">
            <label for="" class="form-label">密碼</label>
            <input required type="password" class="form-control" id="pwd" v-model="pwd" placeholder="6-15位中英字">
            <div th:text="${pwd1}?${pwd1}"></div>
            <div class="invalid-feedback">
              必填
            </div>
          </div>
          <div class="col-md-6 position-relative mb-3">
            <label for="" class="form-label">再次確認密碼</label>
            <input required type="password" class="form-control" id="repwd" v-model="repwd" placeholder="6-15位中英字">
            <div th:text="${repwd2}?${repwd2}"></div>
            <div class="invalid-feedback">
              必填
            </div>
          </div>
          <!-- Button trigger modal -->
          <div class="col-12  d-flex justify-content-center  ">
            <div class="form-check">
              <input class="form-check-input" type="checkbox" id="gridCheck" @click="toggleDisabled">
              <label for="gridCheck">
                我已閱讀且同意<a data-bs-toggle="modal" data-bs-target="#staticBackdrop" class="alert-link">隱私權政策</a>
              </label>
            </div>
          </div>

          <div class="d-grid gap-2 col-8 mx-auto mt-3">
            <button class="btn btn-outline-primary" id="send" @click="submitReg" data-bs-toggle="modal"
              data-bs-target="#exampleModal" :disabled="isDisabled">送出</button>
          </div>
          <hr />
          <div class="d-grid gap-2 col-8 mx-auto mb-2 justify-content-center ">
            <h5>使用其他帳戶註冊</h5>
          </div>
          <div class="d-grid gap-2 col-8 mx-auto mb-2 justify-content-center ">
            <GoogleLogin :callback="callback" theme="filled_blue" context="signup" />
          </div>
          <div>
            <button type="button" class="btn btn-outline-light" @click="submitData">一般帳號</button>
            <button type="button" class="btn btn-outline-light" @click="submitGoogle">google帳號</button>
          </div>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">訊息</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" @click="clean"></button>
              </div>
              <div class="modal-body">
                <ul v-for="(value, key) in message" :key="key">
                  <li v-if="value == null">訊息加載中...</li>
                  <li v-if="value.isnull">{{ value.isnull }}</li>
                  <li v-if="value.acct">{{ value.acct }}</li>
                  <li v-if="value.username">{{ value.username }}</li>
                  <li v-if="value.coname">{{ value.coname }}</li>
                  <li v-if="value.tel">{{ value.tel }}</li>
                  <li v-if="value.mobile">{{ value.mobile }}</li>
                  <li v-if="value.email">{{ value.email }}</li>
                  <li v-if="value.pwd">{{ value.pwd }}</li>
                  <li v-if="value.repwd">{{ value.repwd }}</li>
                </ul>
                <ul v-if="messageyes == ''">訊息加載中...</ul>
                <ul v-else>{{ messageyes }}</ul>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="clean">Close</button>
              </div>
            </div>
          </div>
        </div>

        <!-- Modal -->

        <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
          aria-labelledby="staticBackdropLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h1 class="modal-title fs-5" id="staticBackdropLabel">隱私權政策</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
                <p class="box3">
                  非常歡迎您光臨「easystep website」（以下簡稱本網站），為了讓您能夠安心的使用本網站的各項服務與資訊，特此向您說明本網站的隱私權保護政策，以保障您的權益，請您詳閱下列內容：<br><br>
                  <b>一、隱私權保護政策的適用範圍</b><br>
                  隱私權保護政策內容，包括本網站如何處理在您使用網站服務時收集到的個人識別資料。隱私權保護政策不適用於本網站以外的相關連結網站，也不適用於非本網站所委託或參與管理的人員。<br><br>
                  <b>二、個人資料的蒐集、處理及利用方式</b><br>
                  •
                  當您造訪本網站或使用本網站所提供之功能服務時，我們將視該服務功能性質，請您提供必要的個人資料，並在該特定目的範圍內處理及利用您的個人資料；非經您書面同意，本網站不會將個人資料用於其他用途。<br>
                  • 本網站在您使用服務信箱、問卷調查等互動性功能時，會保留您所提供的姓名、電子郵件地址、聯絡方式及使用時間等。<br>
                  • 於一般瀏覽時，伺服器會自行記錄相關行徑，包括您使用連線設備的 IP
                  位址、使用時間、使用的瀏覽器、瀏覽及點選資料記錄等，做為我們增進網站服務的參考依據，此記錄為內部應用，決不對外公佈。<br>
                  • 為提供精確的服務，我們會將收集的問卷調查內容進行統計與分析，分析結果之統計數據或說明文字呈現，除供內部研究外，我們會視需要公佈統計數據及說明文字，但不涉及特定個人之資料。<br>
                  • 您可以隨時向我們提出請求，以更正或刪除本網站所蒐集您錯誤或不完整的個人資料。<br><br>
                  <b>三、資料之保護</b><br>
                  •
                  本網站主機均設有防火牆、防毒系統等相關的各項資訊安全設備及必要的安全防護措施，加以保護網站及您的個人資料採用嚴格的保護措施，只由經過授權的人員才能接觸您的個人資料，相關處理人員皆簽有保密合約，如有違反保密義務者，將會受到相關的法律處分。<br>
                  • 如因業務需要有必要委託其他單位提供服務時，本網站亦會嚴格要求其遵守保密義務，並且採取必要檢查程序以確定其將確實遵守。<br><br>
                  <b>四、網站對外的相關連結</b><br>
                  本網站的網頁提供其他網站的網路連結，您也可經由本網站所提供的連結，點選進入其他網站。但該連結網站不適用本網站的隱私權保護政策，您必須參考該連結網站中的隱私權保護政策。<br><br>
                  <b>五、與第三人共用個人資料之政策</b><br>
                  本網站絕不會提供、交換、出租或出售任何您的個人資料給其他個人、團體、私人企業或公務機關，但有法律依據或合約義務者，不在此限。<br>
                  前項但書之情形包括不限於：<br>
                  • 經由您書面同意。<br>
                  • 法律明文規定。<br>
                  • 為免除您生命、身體、自由或財產上之危險。<br>
                  • 與公務機關或學術研究機構合作，基於公共利益為統計或學術研究而有必要，且資料經過提供者處理或蒐集者依其揭露方式無從識別特定之當事人。<br>
                  • 當您在網站的行為，違反服務條款或可能損害或妨礙網站與其他使用者權益或導致任何人遭受損害時，經網站管理單位研析揭露您的個人資料是為了辨識、聯絡或採取法律行動所必要者。<br>
                  • 有利於您的權益。<br>
                  • 本網站委託廠商協助蒐集、處理或利用您的個人資料時，將對委外廠商或個人善盡監督管理之責。<br><br>
                  <b>六、Cookie 之使用</b><br>
                  為了提供您最佳的服務，本網站會在您的電腦中放置並取用我們的 Cookie，若您不願接受 Cookie 的寫入，您可在您使用的瀏覽器功能項中設定隱私權等級為高，即可拒絕 Cookie
                  的寫入，但可能會導致網站某些功能無法正常執行 。<br><br>
                  <b>七、隱私權保護政策之修正</b><br>
                  本網站隱私權保護政策將因應需求隨時進行修正，修正後的條款將刊登於網站上。
                </p>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
    
<script setup>
import axios from 'axios';
import { ref, reactive } from 'vue';
import { decodeCredential } from 'vue3-google-login'

const isDisabled = ref(true);
const toggleDisabled = () => {
  isDisabled.value = !isDisabled.value;
};
const submitData = () => {
  acct.value = 'tra116'
  pwd.value = 'tra116'
  repwd.value = 'tra116'
  username.value = '吳筱'
  ecName.value = '吳伯'
  relationship.value = '父女'
  tel.value = '062037219'
  mobile.value = '0958581095'
  sexabbr.value = 'T'
  email.value = 'c2311505good@yahoo.com.tw'

}

const submitGoogle = () => {
  acct.value = 'www116'
  pwd.value = 'www116'
  repwd.value = 'www116'
  tel.value = '062037219'
  mobile.value = '0958581095'
  ecName.value = '吳伯'
  relationship.value = '父子'
  sexabbr.value = 'T'

}





const sub = ref('');
const callback = (response) => {
  const userData = decodeCredential(response.credential)
  console.log(userData);
  sub.value = userData.sub;
  email.value = userData.email
  username.value = userData.name

  alert('註冊尚未完成請將資料補齊送出')
}
const acct = ref('');
const pwd = ref('');
const repwd = ref('');
const username = ref('');
const email = ref('');
const tel = ref('');
const mobile = ref('');
const ecName = ref('');
const relationship = ref('');
const sexabbr = ref('');

const message = ref([]);
const messageyes = ref('');

const clean = () => {
  message.value = "";
  messageyes.value = "";
}



const submitReg = async () => {

  const datass = reactive({
    "sub": sub.value,
    "acct": acct.value,
    "pwd": pwd.value,
    "username": username.value,
    "tel": tel.value,
    "mobile": mobile.value,
    "sexabbr": sexabbr.value,
    "email": email.value,
    "relationship": relationship.value,
    "ecName": ecName.value,
    "repwd": repwd.value
  })
  const url = 'http://localhost:8084/easystep/Traveler/add';
  const response = await axios.post(url, datass);
  console.log(response);
  console.log('內容：' + datass.acct);

  message.value = response.data.no;
  messageyes.value = response.data.yes;

}
</script>
    
<style scoped></style>