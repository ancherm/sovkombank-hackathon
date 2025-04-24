const routes = [
    {
        path: '/',
        name: 'home',
        component: () => import('@/views/HomeView.vue'),
        meta: { title: 'Главная' }
    },
    // {
    //     path: '/login',
    //     name: 'login',
    //     component: () => import('@/views/Auth/LoginView.vue'),
    //     meta: { guestOnly: true }
    // },
];

export default routes;