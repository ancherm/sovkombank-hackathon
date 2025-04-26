const routes = [
    {
        path: '/',
        name: 'download',
        component: () => import('@/views/DownloadView.vue'),
        meta: { title: 'Загрузка чека' }
    },
    {
        path: '/user',
        name: 'user',
        component: () => import('@/views/UserView.vue'),
        meta: { title: 'Пользователь' }
    },
];

export default routes;