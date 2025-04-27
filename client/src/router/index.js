import { createRouter, createWebHistory } from 'vue-router';
import routes from './routes';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
});

router.beforeEach((to, from, next) => {
    const userId = localStorage.getItem('userId');

    if (to.meta.requiresAuth) {
        if (!userId) {
            next({ name: 'login', query: { redirect: to.fullPath } });
        } else {
            next();
        }
    } else {
        next();
    }
});

export default router;