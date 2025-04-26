import cv2
import easyocr
import numpy as np
import pyzbar.pyzbar as pyzbar
import requests
from typing import Optional, Dict, List
import logging

from src.internal.data.receipt import parse_receipt



def read_qr(image):
    decoded_objects = pyzbar.decode(image)
    for obj in decoded_objects:
        if obj.type == 'QRCODE':
            return obj.data.decode('utf-8')
    return None


logger = logging.getLogger(__name__)

async def scan_rec(image: np.ndarray) -> Optional[Dict]:
    """
    Анализирует изображение чека: сначала ищет QR-код, затем использует OCR
    """
    try:
        # 1. Поиск QR-кода
        qr_data = read_qr(image)

        if qr_data:
            logger.info(f"Найден QR-код: {qr_data[:50]}...")  # Логируем часть данных
            receipt_data = await fetch_receipt_data(qr_data)
            if receipt_data:
                return parse_receipt(receipt_data)

        # 2. Если QR не найден - используем OCR
        logger.warning("QR-код не найден, используется OCR")
        return

    except Exception as e:
        logger.error(f"Ошибка при сканировании чека: {str(e)}")
        return None

async def fetch_receipt_data(qr_data: str) -> Optional[Dict]:
    logger.info(f"Запрашиваем данные чека по API")
    try:
        url = "https://proverkacheka.com/api/v1/check/get"
        data = {
            "token": "32641.YNCv5PVM1cyeDGzfv",  # TODO - вынести в конфиг
            "qrraw": qr_data,
        }
        response = requests.post(url, data=data, timeout=10)
        response.raise_for_status()
        logger.info(f"данные полученны")
        return response.json()
    except requests.RequestException as e:
        logger.error(f"Ошибка API: {str(e)}")
        return None

async def process_with_ocr(image: np.ndarray) -> Dict:
    """
    Обработка чека через EasyOCR
    """
    try:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        reader = easyocr.Reader(['ru'], gpu=False)
        results = reader.readtext(gray, detail=0)

        # Здесь должен быть ваш парсинг текста
        # Это пример - нужно адаптировать под реальные чеки!
        items = parse_ocr_text(results)

        return {
            "shop": "Не определено (OCR)",
            "items": items,
            "total": sum(item["total"] for item in items)
        }
    except Exception as e:
        logger.error(f"OCR ошибка: {str(e)}")
        return {"error": "Не удалось распознать чек"}

def parse_ocr_text(lines: List[str]) -> List[Dict]:
    """
    Парсит распознанный текст в структурированные данные
    """
    items = []
    # Пример простого парсера (нужно доработать!)
    for line in lines:
        if any(word in line.lower() for word in ["итого", "сумма"]):
            continue

        # Наивный парсинг - в реальности нужен более сложный алгоритм
        parts = line.rsplit(' ', 2)
        if len(parts) >= 3:
            items.append({
                "name": ' '.join(parts[:-2]),
                "quantity": 1,
                "price": float(parts[-2]),
                "total": float(parts[-1])
            })
    return items