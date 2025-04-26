<script setup>
import {onMounted, ref} from 'vue'
import UserProfile from "@/components/UserProfile.vue"
import ReceiptList from "@/components/ReceiptList.vue";
import {useRouter} from "vue-router";
import DownloadView from "@/views/DownloadView.vue";
import {apiGetUser} from "@/api/endpoins/user.get.api.js";

const userData = ref({
  username: '',
  avatar: 'https://i.pinimg.com/1200x/1c/4b/3c/1c4b3c9bfee4f74fc762d9c576e330fb.jpg',
})

async function loadUserData(userId) {
  try {
    const user = await apiGetUser(userId);
    console.log("API RESPONSE", user)
    userData.value = {
      username: user.username,
      avatar: 'https://i.pinimg.com/1200x/1c/4b/3c/1c4b3c9bfee4f74fc762d9c576e330fb.jpg',
    };
  } catch (error) {
    console.error('Ошибка при загрузке данных пользователя:', error);
  }
}

onMounted(() => {
  loadUserData(1);
});


const collapse = ref(false)

const router = useRouter()

function goToPage(pageName) {
  router.push({ name: pageName })
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
        </div>
      </template>
    </v-toolbar>
  </v-card>

  <v-container class="py-10 custom-container">
    <v-row>
      <!-- Левая колонка -->
      <v-col cols="5">
        <UserProfile :user="userData"/>
      </v-col>

      <!-- Правая колонка -->
      <v-col cols="7">
        <ReceiptList :items-per-page="10" :userId="123" />
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
