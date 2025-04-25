<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card class="pa-4">
          <v-card-title class="text-h5 mb-4">Загрузка изображения</v-card-title>

          <VFileInput @change="handleFileInput" />

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

    <!-- Snackbar -->
    <v-snackbar
        v-model="snackbar.show"
        :color="snackbar.color"
        :timeout="3000"
        location="top right"
    >
      {{ snackbar.message }}
    </v-snackbar>
  </v-container>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

// Константы
const API_URL = 'http://localhost:8080/api/receipts';

// Состояния
const userId = 1;
const file = ref(null);
const imageData = ref(null);
const previewUrl = ref(null);
const loading = ref(false);
const errorMessage = ref('');

// Snackbar
const snackbar = ref({
  show: false,
  message: '',
  color: 'success',
});

// Функции
const handleFileInput = async (event) => {
  resetError();

  const file = event?.target?.files?.[0]; // <-- правильно достаем файл

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
        resolve(result.split(',')[1]); // Только base64 часть
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
    const response = await uploadImage();
    showSnackbar('Изображение успешно отправлено', 'success');
    console.log('Ответ:', response.data);
  } catch (err) {
    console.error('Ошибка отправки:', err);
    setError('Произошла ошибка при отправке');
    showSnackbar('Ошибка при отправке изображения', 'error');
  } finally {
    loading.value = false;
  }
};

const uploadImage = () => {
  const payload = {
    userId,
    data: imageData.value,
  };

  return axios.post(API_URL, payload, {
    headers: { 'Content-Type': 'application/json' },
  });
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
</script>

<style scoped>
</style>
