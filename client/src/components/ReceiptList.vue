<script setup>
import {ref, computed, onMounted} from 'vue'
import ReceiptComponent from "@/components/ReceiptComponent.vue"
import {apiGetReceipts} from "@/api/endpoins/receipts.get.api.js"

// Пропсы
const props = defineProps({
  userId: {
    type: Number,
    required: true
  },
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
  apiGetReceipts(page.value, props.itemsPerPage, searchQuery.value, props.userId)  // Загружаем чеки с API или моков
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

// Поиск локально по текущим чекам
const filteredReceipts = computed(() => {
  if (!searchQuery.value) {
    return receipts.value
  }
  return receipts.value.filter(receipt =>
      receipt.retailPlace.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
})

</script>

<template>
  <div>
    <!-- Поле поиска -->
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
        v-for="receipt in filteredReceipts"
        :key="receipt.retailPlace + receipt.date"
        :receipt-data="receipt"
    />

    <!-- Пагинация -->
    <div class="text-center mt-4">
      <!-- Спиннер загрузки -->
      <v-progress-circular
          v-if="isLoading"
          indeterminate
          color="primary"
          size="40"
          class="mb-4"
      />

      <!-- Пагинация, только если не идёт загрузка -->
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
