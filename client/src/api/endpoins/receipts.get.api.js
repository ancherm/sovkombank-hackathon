// apiGetReceipts.js
import tmpDataImage from "@/views/consts.js"

export async function apiGetReceipts(page = 1, itemsPerPage = 10, searchQuery = '', userId = 1) {
    // Здесь добавляется логика для фильтрации или получения данных в зависимости от userId
    const totalReceipts = 47;  // или получить количество чеков для данного userId
    const totalPages = Math.ceil(totalReceipts / itemsPerPage);

    // Мокаем список чеков
    const receipts = Array.from({ length: totalReceipts }, (_, i) => ({
        id: i + 1,
        retailPlace: `Магазин ${i + 1}`,
        dataImage: tmpDataImage,
        date: `2024-04-${String((25 - (i % 30))).padStart(2, '0')}`,
        totalSum: 500 + i * 10,
        userId: userId,  // Используем userId
        items: [
            { name: 'Товар A', quantity: 1, price: 100, category: 'SNEKI' },
            { name: 'Товар B', quantity: 2, price: 150, category: 'SNEKI' },
        ],
    }));

    // Фильтруем по поисковому запросу, если он есть
    const filteredReceipts = receipts.filter(receipt => {
        const matchesSearch = searchQuery === '' || receipt.retailPlace.toLowerCase().includes(searchQuery.toLowerCase());
        return matchesSearch;
    });

    // Считаем откуда и докуда брать чеки
    const start = (page - 1) * itemsPerPage;
    const end = start + itemsPerPage;
    const paginatedReceipts = filteredReceipts.slice(start, end);

    return new Promise((resolve) => {
        setTimeout(() => {
            resolve({
                receipts: paginatedReceipts,
                pagination: {
                    currentPage: page,
                    totalPages,
                    totalReceipts: filteredReceipts.length
                }
            });
        }, 500); // имитация задержки сети
    });
}

