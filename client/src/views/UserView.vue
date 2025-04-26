<script setup>
import { ref } from 'vue'
import UserProfile from "@/components/UserProfile.vue"
import ReceiptList from "@/components/ReceiptList.vue";

// Данные о пользователе
const userData = ref({
  name: 'Иван Иванов',
  email: 'ivan.ivanov@example.com',
  avatar: 'https://randomuser.me/api/portraits/men/75.jpg',
})



const collapse = ref(false)
</script>

<template>
  <v-card>
    <v-toolbar :collapse="collapse" title="SOVKOM-BANK">
      <template v-slot:append>
        <div class="d-flex ga-1">
          <v-btn icon>
            <v-icon>mdi-account</v-icon>
          </v-btn>
          <v-btn>ЗАГРУЗИТЬ ЧЕК</v-btn>
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
