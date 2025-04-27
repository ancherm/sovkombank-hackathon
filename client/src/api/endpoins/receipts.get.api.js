// apiGetReceipts.js
import tmpDataImage from "@/views/consts.js"

export async function apiGetReceipts(page = 1, size = 10, searchQuery = '', userId) {
    try {
        const response = await fetch(`/api/receipts?page=${page}&size=${itemsPerPage}&searchQuery=${searchQuery}&userId=${userId}`);

        if (!response.ok) {
            throw new Error('Ошибка загрузки чеков');
        }

        const data = await response.json();

        const { receipts, totalReceipts } = data;

        const totalPages = Math.ceil(totalReceipts / itemsPerPage);

        return {
            receipts,
            pagination: {
                currentPage: page,
                totalPages,
                totalReceipts
            }
        };

    } catch (error) {
        console.error('Ошибка при загрузке чеков:', error);
        throw new Error('Не удалось загрузить чеки');
    }
}

