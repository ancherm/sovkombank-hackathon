<script setup>
import { ref } from 'vue'


const props = defineProps({
  user: {
    type: Object,
    required: true
  }
})

const editMode = ref(false)

function toggleEdit() {
  editMode.value = !editMode.value
}
</script>

<template>
  <v-container class="py-0">
    <v-row justify="center">
      <v-col cols="12" >
        <v-card class="pa-6" elevation="10">
          <v-card-title class="d-flex align-center justify-space-between">
            <div>
              <v-icon start class="me-2">mdi-account</v-icon>
              Профиль пользователя
            </div>
          </v-card-title>

          <v-divider class="my-4"></v-divider>

          <v-card-text class="text-center">
            <v-avatar size="120" class="mb-4">
              <v-img :src="props.user.avatar" alt="User avatar" />
            </v-avatar>

            <div class="text-h5 mb-2">{{ props.user.username }}</div>
            <div class="text-body-2 mb-4">{{ props.user.email }}</div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.v-avatar {
  margin: auto;
}
</style>
