<script setup>
import { ref, computed } from 'vue'
import UserProfile from "@/components/UserProfile.vue"
import ReceiptComponent from "@/components/ReceiptComponent.vue"
import tmpDataImage from "@/views/consts.js"

// Данные о пользователе
const userData = ref({
  name: 'Иван Иванов',
  email: 'ivan.ivanov@example.com',
  avatar: 'https://randomuser.me/api/portraits/men/75.jpg',
})

// Мокаем 50 чеков
const receipts = ref(Array.from({ length: 50 }, (_, i) => ({
  retailPlace: `Магазин ${i + 1}`,
  dataImage: tmpDataImage,
  date: `2024-04-${String(25 - (i % 30)).padStart(2, '0')}`,
  totalSum: 500 + i * 10,
  items: [
    { name: 'Товар A', quantity: 1, price: 100 },
    { name: 'Товар B', quantity: 2, price: 150 },
  ],
})))

// Параметры отображения
const showAll = ref(false)
const page = ref(1)
const itemsPerPage = 10 // сколько показывать после нажатия "Показать все"

// Список чеков для отображения
const visibleReceipts = computed(() => {
  if (!showAll.value) {
    return receipts.value.slice(0, 3) // пока не нажали кнопку - только 3
  } else {
    const start = (page.value - 1) * itemsPerPage
    return receipts.value.slice(start, start + itemsPerPage)
  }
})

// Количество страниц для пагинации
const totalPages = computed(() => {
  return Math.ceil(receipts.value.length / itemsPerPage)
})

// Для toolbar collapse
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
          <v-btn icon>
            <v-icon>mdi-dots-vertical</v-icon>
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
        <div>
          <!-- Перебор чеков -->
          <ReceiptComponent
              v-for="receipt in visibleReceipts"
              :key="receipt.retailPlace + receipt.date"
              :receipt-data="receipt"
          />

          <!-- Кнопка "Показать все" -->
          <div class="text-center mt-4" v-if="!showAll">
            <v-btn color="primary" @click="showAll = true">
              Показать все
            </v-btn>
          </div>

          <!-- Пагинация после показа всех -->
          <div class="text-center mt-4" v-else>
            <v-pagination
                v-model="page"
                :length="totalPages"
                color="primary"
            />
          </div>
        </div>
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
