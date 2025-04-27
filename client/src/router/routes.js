const routes = [
    {
        path: '/download',
        name: 'download',
        component: () => import('@/views/DownloadView.vue'),
        meta: { title: 'Загрузка чека', requiresAuth: true }
    },
    {
        path: '/user',
        name: 'user',
        component: () => import('@/views/UserView.vue'),
        meta: { title: 'Пользователь', requiresAuth: true }
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/LoginView.vue'),
        meta: { title: 'Логин' }
    }
];

export default routes;