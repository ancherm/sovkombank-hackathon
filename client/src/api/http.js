import axios from 'axios';

const url = 'http://localhost:8080'

const http = axios.create({
    baseURL: import.meta.env.VITE_API_URL || url,
    timeout: 10000,
});

http.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

http.interceptors.response.use(
    (response) => response.data,
    (error) => {
        if (error.response?.status === 401) {
        }
        return Promise.reject(error);
    }
);

export default http;