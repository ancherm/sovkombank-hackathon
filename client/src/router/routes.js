const routes = [
    {
        path: '/',
        name: 'home',
        component: () => import('@/views/HomeView.vue'),
        meta: { title: 'Главная' }
    },
    {
        path: '/user',
        name: 'user',
        component: () => import('@/views/UserView.vue'),
        meta: { title: 'Пользователь' }
    },
];

export default routes;