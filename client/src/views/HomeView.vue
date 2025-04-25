<script>
import axios from 'axios';

export default {
  data() {
    return {
      userId: 1,
      imageData: null,
      file: null,
      loading: false,
      errorMessage: '',
    };
  },
  methods: {
    onFileChange(files) {
      this.errorMessage = '';
      if (files && files.length > 0) {
        const file = files[0]; // Получаем первый файл из массива
        if (!file.type.match('image.*')) {
          this.errorMessage = 'Пожалуйста, выберите изображение!';
          return;
        }

        const reader = new FileReader();
        reader.onload = (e) => {
          this.imageData = e.target.result.split(',')[1];
        };
        reader.onerror = () => {
          this.errorMessage = 'Ошибка при чтении файла';
        };
        reader.readAsDataURL(file);
      } else {
        this.imageData = null;
      }
    },

    // остальные методы без изменений
    async sendData() {
      if (!this.validateInput()) return;

      this.loading = true;
      this.errorMessage = '';

      try {
        const response = await this.uploadImage();
        this.handleSuccess(response);
      } catch (error) {
        this.handleError(error);
        console.log(error)
      } finally {
        this.loading = false;
      }
    },

    validateInput() {
      if (!this.imageData) {
        this.errorMessage = 'Пожалуйста, выберите изображение';
        return false;
      }
      return true;
    },

    async uploadImage() {
      const payload = {
        userId: this.userId,
        data: this.imageData,
      };

      return await axios.post('http://localhost:8080/api/receipts', payload,{
        headers: {
          'Content-Type': 'application/json',
        },
      });
    },

    handleSuccess(response) {
      console.log('Успех:', response.data);
      this.$toast.success('Изображение успешно отправлено');
    },

    handleError(error) {
      console.error('Ошибка:', error);
      this.errorMessage = 'Произошла ошибка при отправке данных';
      this.$toast.error('Ошибка при отправке изображения');
    },
  },
};
</script>

<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card class="pa-4">
          <v-card-title class="text-h5 mb-4">Загрузка изображения!</v-card-title>

          <v-file-input
              v-model="file"
              accept="image/*"
              label="Выберите изображение"
              prepend-icon="mdi-camera"
              @change="onFileChange"
              outlined
              clearable
          ></v-file-input>

          <v-alert
              v-if="errorMessage"
              type="error"
              class="mb-4"
          >
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
</template>

<style scoped>

</style>