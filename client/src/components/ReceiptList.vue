<script setup>
import {ref, computed, onMounted, watch} from 'vue'
import ReceiptComponent from "@/components/ReceiptComponent.vue"
import {apiGetReceipts, apiGetReceiptsDontPagination} from "@/api/endpoins/receipts.get.api.js"

const userId = localStorage.getItem('userId')
// Пропсы
const props = defineProps({
  itemsPerPage: {
    type: Number,
    required: true
  }
})


const page = ref(1)
const searchQuery = ref('')
const receipts = ref([])  // Массив для хранения чеков
const totalPages = ref(1)
const isLoading = ref(false)  // Флаг загрузки

// Функция для загрузки чеков
function loadReceipts() {
  isLoading.value = true
  apiGetReceiptsDontPagination(page.value, props.itemsPerPage, searchQuery.value, userId)
      .then(response => {
        receipts.value = response.receipts
        totalPages.value = response.pagination.totalPages
        isLoading.value = false
      })
      .catch(() => {
        isLoading.value = false
      })
}

// Загружаем чеки при монтировании компонента
onMounted(() => {
  loadReceipts()
})

// Когда меняем страницу — эмитим родителю и показываем индикатор загрузки
function onPageChange(newPage) {
  if (!isLoading.value) {  // Чтобы предотвратить клики во время загрузки
    page.value = newPage
    loadReceipts()
  }
}

watch(searchQuery, () => {
  page.value = 1; // сбрасываем на первую страницу при новом поиске
  loadReceipts()
})


</script>

<template>
  <div>
    <div class="mb-4">
      <v-text-field
          v-model="searchQuery"
          label="Поиск по магазинам"
          prepend-inner-icon="mdi-magnify"
          hide-details
          dense
      />
    </div>

    <!-- Список чеков -->
    <ReceiptComponent
        v-for="receipt in receipts"
        :key="receipt.retailPlace + receipt.date"
        :receipt-data="receipt"
    />

    <div class="text-center mt-4">
      <v-progress-circular
          v-if="isLoading"
          indeterminate
          color="primary"
          size="40"
          class="mb-4"
      />

      <v-pagination
          v-if="!isLoading"
          v-model="page"
          :length="totalPages"
          color="primary"
          @update:model-value="onPageChange"
      />
    </div>
  </div>
</template>
