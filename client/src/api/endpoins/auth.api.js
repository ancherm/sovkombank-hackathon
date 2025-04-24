import http from '../http';

export default {
    login: (credentials) => http.post('/auth/login', credentials),
    register: (userData) => http.post('/auth/register', userData),
};