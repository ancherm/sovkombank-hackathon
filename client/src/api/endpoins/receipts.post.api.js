import http from "@/api/http.js";

const API_URL = '/api/receipts';

export async function uploadImage(userId, imageData) {
    const payload = {
        userId,
        data: imageData,
    };

    try {
        const response = await http.post(API_URL, payload, {
            headers: { 'Content-Type': 'application/json' },
        });
        return response;
    } catch (error) {
        throw new Error('Произошла ошибка при отправке изображения: ' + error.message);
    }
}
