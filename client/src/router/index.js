import { createRouter, createWebHistory } from 'vue-router';
import routes from './routes';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
});

// router.beforeEach(async (to, from, next) => {
//     const {useAuthStore} = await import('@/stores/auth.store');
//     const authStore = useAuthStore();
//
//     // Проверяем требует ли роут аутентификации
//     if (to.meta.requiresAuth) {
//         if (authStore.isAuthenticated) {
//             next();
//         } else {
//             next({name: 'login', query: {redirect: to.fullPath}});
//         }
//     }
//     // Все остальные случаи
//     else {
//         next();
//     }
// });

export default router;