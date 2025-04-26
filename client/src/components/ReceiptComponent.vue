<script setup>
import { ref } from 'vue'

const props = defineProps({
  receiptData: {
    type: Object,
    required: true,
  },
})

const recData = props.receiptData

// Функция для сохранения base64 изображения
function downloadImage() {
  const link = document.createElement('a')
  link.href = recData.dataImage
  link.download = 'receipt.png'  // имя файла
  link.click()
}
</script>


<template>
  <v-expansion-panels class="mb-4">
    <v-expansion-panel>
      <v-expansion-panel-title>
        <div class="d-flex justify-space-between w-100">
          <span>Чек {{ recData.retailPlace }}</span>
          <span class="text-caption">{{ recData.date }}</span>
        </div>
      </v-expansion-panel-title>

      <v-expansion-panel-text>
        <!-- Строка: Фото слева, данные справа -->
        <v-row>
          <!-- Фото -->
          <v-col cols="12" md="6" class="text-center">
            <v-img
                v-if="recData.dataImage"
                :src="recData.dataImage"
                max-height="200"
                contain
                class="mb-2"
            />
            <v-btn
                v-if="recData.dataImage"
                color="primary"
                @click="downloadImage"
                size="small"
            >
              Скачать изображение
            </v-btn>
          </v-col>

          <!-- Дата и итог справа -->
          <v-col cols="12" md="6">
            <div class="mb-2">
              <strong>Дата:</strong> {{ recData.date }}
            </div>
            <div class="mb-2">
              <strong>Итоговая сумма:</strong> {{ recData.totalSum }} ₽
            </div>
          </v-col>
        </v-row>

        <v-divider class="my-4"></v-divider>

        <!-- Список товаров -->
        <div>
          <strong>Товары:</strong>
          <v-list dense>
            <v-list-item
                v-for="(item, index) in recData.items"
                :key="index"
            >
              <v-list-item-content>
                <v-list-item-title>{{ item.name }}</v-list-item-title>
                <v-list-item-subtitle>
                  Количество: {{ item.quantity }} | Цена: {{ item.price }} ₽
                </v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </div>
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<style scoped>
</style>
