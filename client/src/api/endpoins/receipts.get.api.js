import http from "@/api/http.js";

export async function apiGetReceipts(page = 1, size = 10, searchQuery = '', userId) {
    try {
        const response = await http.get(`/api/receipts?page=${page}&size=${itemsPerPage}&searchQuery=${searchQuery}&userId=${userId}`);

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

export async function apiGetReceiptsDontPagination(page = 1, size = 10, searchQuery = '', userId) {
    try {
        const response = await http.get(`/api/receipts?userId=${userId}`);
        let receipts = response;

        if (searchQuery.trim() !== '') {
            const lowerCaseQuery = searchQuery.toLowerCase();
            receipts = receipts.filter(receipt =>
                receipt.retailPlace?.toLowerCase().includes(lowerCaseQuery) ||
                receipt.items?.some(item =>
                    item.name?.toLowerCase().includes(lowerCaseQuery)
                )
            );
        }
        console.log(response)
        const totalReceipts = receipts.length;
        const totalPages = Math.ceil(totalReceipts / size);

        const start = (page - 1) * size;
        const end = start + size;
        const paginatedReceipts = receipts.slice(start, end);

        return {
            receipts: paginatedReceipts,
            pagination: {
                currentPage: page,
                totalPages,
                totalReceipts,
                pageSize: size,
            },
        };

    } catch (error) {
        console.error('Ошибка при загрузке чеков:', error);
        throw new Error('Не удалось загрузить чеки');
    }
}

