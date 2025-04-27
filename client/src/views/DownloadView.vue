<script setup>
import { ref } from 'vue';
import { useRouter } from "vue-router";
import ReceiptComponent from "@/components/ReceiptComponent.vue";
import { uploadImage } from "@/api/endpoins/receipts.post.api.js";

// Константы
const userId = 1;
const file = ref(null);
const imageData = ref(null);
const previewUrl = ref(null);
const loading = ref(false);
const errorMessage = ref('');
let receiptData = ref(null);

// Snackbar
const snackbar = ref({
  show: false,
  message: '',
  color: 'success',
});

// Функции
const handleFileInput = async (event) => {
  resetError();

  const file = event?.target?.files?.[0];

  if (!file) {
    imageData.value = null;
    previewUrl.value = null;
    return;
  }

  console.log(file);

  if (!file.type.startsWith('image/')) {
    setError('Файл должен быть изображением');
    return;
  }

  try {
    const base64 = await readFileAsBase64(file);
    imageData.value = base64;
    previewUrl.value = URL.createObjectURL(file);
  } catch {
    setError('Ошибка при чтении файла');
  }
};

const readFileAsBase64 = (file) => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = e => {
      const result = e.target?.result;
      if (typeof result === 'string') {
        const base64String = `data:${file.type};base64,${result.split(',')[1]}`;
        resolve(base64String); // Возвращаем полный base64
      } else {
        reject();
      }
    };
    reader.onerror = () => reject();
    reader.readAsDataURL(file);
  });
};

const sendData = async () => {
  if (!validate()) return;

  loading.value = true;
  resetError();

  try {
    console.log(imageData.value);
    receiptData.value = null
    const response = await uploadImage(userId, imageData.value);
    showSnackbar('Изображение успешно отправлено', 'success');

    // Теперь обновляем реактивную переменную receiptData
    receiptData.value = response;

    // Очищаем input после успешной отправки
    imageData.value = null;
    previewUrl.value = null;  // Очищаем превью
  } catch (err) {
    console.error('Ошибка отправки:', err);
    setError('Произошла ошибка при отправке');
    showSnackbar('Ошибка при отправке изображения', 'error');
  } finally {
    loading.value = false;
  }
};

const validate = () => {
  if (!imageData.value) {
    setError('Пожалуйста, выберите изображение');
    return false;
  }
  return true;
};

const setError = (msg) => {
  errorMessage.value = msg;
};

const resetError = () => {
  errorMessage.value = '';
};

const showSnackbar = (message, color = 'success') => {
  snackbar.value.message = message;
  snackbar.value.color = color;
  snackbar.value.show = true;
};

const router = useRouter()

function goToPage(pageName) {
  router.push({ name: pageName })
}
</script>

<template>
  <!-- Toolbar -->
  <v-card>
    <v-toolbar :collapse="false" title="SOVKOM-BANK">
      <template v-slot:append>
        <div class="d-flex ga-1">
          <v-btn icon @click="goToPage('user')">
            <v-icon>mdi-account</v-icon>  <!-- Иконка "пользователь" -->
          </v-btn>
        </div>
      </template>
    </v-toolbar>
  </v-card>

  <!-- Upload Form -->
  <v-container>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card class="pa-4">
          <v-card-title class="text-h5 mb-4">
            Загрузка изображения
          </v-card-title>

          <VFileInput @change="handleFileInput"/>

          <v-img
              v-if="previewUrl"
              :src="previewUrl"
              class="mb-4"
              height="200"
              contain
          />

          <v-alert v-if="errorMessage" type="error" class="mb-4">
            {{ errorMessage }}
          </v-alert>

          <v-btn
              color="primary"
              :loading="loading"
              :disabled="!imageData"
              @click="sendData"
              block
          >
            <v-icon left>mdi-send</v-icon>
            Отправить
          </v-btn>
        </v-card>
      </v-col>
    </v-row>
  </v-container>

  <!-- Отображение компонента ReceiptComponent только если данные есть -->
  <v-container>
    <ReceiptComponent v-if="receiptData" :receipt-data="receiptData"/>
  </v-container>

  <!-- Snackbar -->
  <v-snackbar
      v-model="snackbar.show"
      :color="snackbar.color"
      :timeout="3000"
      location="top right"
  >
    {{ snackbar.message }}
  </v-snackbar>
</template>



