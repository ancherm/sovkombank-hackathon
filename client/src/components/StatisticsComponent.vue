<script setup>
import { ref, watch, onUnmounted, nextTick } from 'vue';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

const startDate = ref(new Date().toISOString().split('T')[0]);
const endDate = ref(new Date().toISOString().split('T')[0]);
const data = ref(null);
const loading = ref(false);

const headers = [
  { title: 'Категория', key: 'categoryName' },
  { title: 'Сумма', key: 'sum' },
  { title: 'Доля, %', key: 'percentage' },
];

const mockData = {
  totalSum: 3409.96,
  categories: [
    { categoryName: 'Молочные продукты', sum: 229.99, percentage: 6.74 },
    { categoryName: 'Мясо и рыба', sum: 3000.0, percentage: 87.98 },
    { categoryName: 'Хлеб и выпечка', sum: 179.97, percentage: 5.28 },
    { categoryName: 'Электроника и мелкая техника', sum: 0.0, percentage: 0.0 },
  ],
};

const fetchData = async () => {
  loading.value = true;
  try {
    await new Promise((resolve) => setTimeout(resolve, 1000));
    data.value = mockData;
  } catch (error) {
    console.error('Ошибка:', error);
  } finally {
    loading.value = false;
  }
};

let chart = null;

const createChart = () => {
  if (!data.value) return;

  nextTick(() => {
    const ctx = document.getElementById('categoryChart')?.getContext('2d');
    if (!ctx) {
      console.error('Canvas не найден!');
      return;
    }

    if (chart) {
      chart.destroy();
    }

    const validCategories = data.value.categories.filter((cat) => cat.sum > 0);
    const labels = validCategories.map((cat) => cat.categoryName);
    const sums = validCategories.map((cat) => cat.sum);
    const colors = [
      'rgba(255, 99, 132, 0.7)',
      'rgba(54, 162, 235, 0.7)',
      'rgba(255, 206, 86, 0.7)',
      'rgba(75, 192, 192, 0.7)',
    ];

    chart = new Chart(ctx, {
      type: 'pie',
      data: {
        labels,
        datasets: [
          {
            data: sums,
            backgroundColor: colors.slice(0, labels.length),
            borderColor: colors.slice(0, labels.length).map((c) => c.replace('0.7', '1')),
            borderWidth: 1,
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: { position: 'top' },
          title: { display: true, text: 'Расходы по категориям' },
        },
      },
    });
  });
};

watch(data, () => {
  if (data.value) {
    try {
      createChart();
    } catch (error) {
      console.error('Ошибка при перерисовывании диаграммы:', error);
    }
  }
});

onUnmounted(() => {
  if (chart) {
    chart.destroy();
  }
});
</script>

<template>
  <div>
    <!-- Форма для выбора периода -->
    <v-form @submit.prevent="fetchData">
      <v-row>
        <v-col cols="12" sm="6">
          <v-text-field
              v-model="startDate"
              label="Дата начала"
              type="date"
              prepend-icon="mdi-calendar"
              required
          />
        </v-col>
        <v-col cols="12" sm="6">
          <v-text-field
              v-model="endDate"
              label="Дата окончания"
              type="date"
              prepend-icon="mdi-calendar"
              required
          />
        </v-col>
      </v-row>
      <v-btn
          color="primary"
          type="submit"
          :loading="loading"
          :disabled="!startDate || !endDate"
      >
        Показать
      </v-btn>
    </v-form>

    <!-- Отображение данных -->
    <v-card v-if="data" class="mt-4">
      <v-card-title>Итог: {{ data.totalSum }} ₽</v-card-title>
      <v-card-text>
        <v-row>
          <v-col cols="12" md="12">
            <div class="chart-container">
              <canvas id="categoryChart"></canvas>
            </div>
          </v-col>
          <v-col cols="12" md="12">
            <v-data-table
                :headers="headers"
                :items="data.categories"
                class="mt-4"
                hide-default-footer
            >
              <template v-slot:item.sum="{ item }">
                {{ item.sum.toFixed(2) }} ₽
              </template>
              <template v-slot:item.percentage="{ item }">
                {{ item.percentage.toFixed(2) }} %
              </template>
            </v-data-table>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </div>
</template>