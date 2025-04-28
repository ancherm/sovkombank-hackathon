<script setup>
import {onMounted, ref} from 'vue'
import UserProfile from "@/components/UserProfile.vue"
import ReceiptList from "@/components/ReceiptList.vue";
import {useRouter} from "vue-router";
import DownloadView from "@/views/DownloadView.vue";
import {apiGetUser} from "@/api/endpoins/user.get.api.js";
import StatisticsComponent from "@/components/StatisticsComponent.vue";
import http from "@/api/http.js";

const userId = localStorage.getItem('userId')
let resTotalSum = ref(0.0)

const userData = ref({
  userId: userId,
  username: '',
  avatar: 'https://i.pinimg.com/1200x/1c/4b/3c/1c4b3c9bfee4f74fc762d9c576e330fb.jpg',
  totalPrice: 0,
})

async function loadUserData(userId) {
  try {
    const user = await apiGetUser(userId);
    userData.value.username = user.username;
    await http.get(`/api/receipts/total?userId=${userId}`)
        .then(response => {
          resTotalSum.value = response;
          userData.value.totalPrice = resTotalSum.value;
          console.log(response);
        });
    console.log(user.username)

  } catch (error) {
    console.error('Ошибка при загрузке данных пользователя:', error);
  }
}

async function loadTotalPrice(userId) {
  try {
    resTotalSum = await http.get(`/api/receipts/total?userId=${userId}`)
    userData.value = {
      totalPrice: resTotalSum
    }
    console.log(resTotalSum)
  } catch (error) {
    console.error('Ошибка при загрузке', error);
  }
}

onMounted(() => {
  loadUserData(userId);
  loadTotalPrice(userId);
});


const collapse = ref(false)

const router = useRouter()

function goToPage(pageName) {
  router.push({ name: pageName })
}

function logout() {
  localStorage.removeItem('userId');
  window.location.reload(true);

}
</script>

<template>
  <v-card>
    <v-toolbar :collapse="collapse" title="SOVKOM-BANK">
      <template v-slot:append>
        <div class="d-flex ga-1">
          <v-btn icon @click="goToPage('download')">
            <v-icon>mdi-download</v-icon>
          </v-btn>
          <v-btn icon @click="logout()">
            <v-icon>mdi-logout</v-icon>
          </v-btn>
        </div>
      </template>
    </v-toolbar>
  </v-card>

  <v-container class="py-10 custom-container">
    <v-row>
      <!-- Левая колонка -->
      <v-col cols="12" md="5" class="d-flex flex-column">
        <UserProfile :user="userData" class="mb-8"/>
        <StatisticsComponent :user="userData"/>
      </v-col>
      <!-- Правая колонка -->
      <v-col cols="7">
        <ReceiptList :items-per-page="10" :userId="userId" />
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.custom-container {
  margin: 0;
  padding: 20px;
  max-width: 100%;
}
</style>
