<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import http from "@/api/http.js";

const username = ref('');
const password = ref('');
const errorMessage = ref('');
const loading = ref(false);

const router = useRouter();

const login = async () => {
  errorMessage.value = '';
  loading.value = true;

  try {
    const response = await http.post('/api/users', {
      username: username.value,
      password: password.value,
      userType: 'CLIENT',
    });

    const userData = response;

    localStorage.setItem('userId', userData.id);

    await router.push('/users');
  } catch (error) {
    console.error('Ошибка при входе:', error);
    errorMessage.value = 'Ошибка входа. Проверьте логин или пароль.';
  } finally {
    loading.value = false;
  }
};
</script>

<template>
  <div class="login-container">
    <v-card class="pa-4" max-width="400">
      <v-card-title class="text-center">Вход</v-card-title>

      <v-card-text>
        <v-form @submit.prevent="login">
          <v-text-field
              v-model="username"
              label="Имя пользователя"
              prepend-icon="mdi-account"
              required
          />

          <v-text-field
              v-model="password"
              label="Пароль"
              type="password"
              prepend-icon="mdi-lock"
              required
          />

          <v-btn
              type="submit"
              color="primary"
              class="mt-4"
              :loading="loading"
              block
          >
            Войти
          </v-btn>

          <v-alert
              v-if="errorMessage"
              type="error"
              class="mt-4"
              dense
          >
            {{ errorMessage }}
          </v-alert>
        </v-form>
      </v-card-text>
    </v-card>
  </div>
</template>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.v-card {
  width: 100%;
  max-width: 400px;
}
</style>
