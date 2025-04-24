import { defineStore } from 'pinia';
import authApi from '@/api/endpoints/auth.api';

export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: null,
        token: null,
    }),

    getters: {
        isAuthenticated: (state) => !!state.token,
    },

    actions: {
        async login(credentials) {
            try {
                const response = await authApi.login(credentials);
                this.token = response.token;
                this.user = response.user;

                // Перенаправление после успешного входа
                const redirect = router.currentRoute.value.query.redirect || '/';
                router.push(redirect);

                return true;
            } catch (error) {
                this.logout();
                throw error;
            }
        },

        logout() {
            this.token = null;
            this.user = null;
            router.push({ name: 'login' });
        },

        // async checkAuth() {
        //     if (!this.token) return false;
        //
        //     try {
        //         const user = await authApi.getProfile();
        //         this.user = user;
        //         return true;
        //     } catch (error) {
        //         this.logout();
        //         return false;
        //     }
        // }
    },

    persist: {
        key: 'auth',
        paths: ['token'],
    }
});