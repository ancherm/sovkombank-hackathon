import axios from 'axios';
import http from "@/api/http.js";

export async function apiGetUser(userId = 1) {
    try {
        const response = await http.get(`/api/users/${userId}`);
        return response;
    } catch (error) {
        console.error('Ошибка при получении данных пользователя:', error);
        throw error;
    }
}

