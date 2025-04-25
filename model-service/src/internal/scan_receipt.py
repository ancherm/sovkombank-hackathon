import cv2
import easyocr
import pyzbar.pyzbar as pyzbar

def read_qr(image):
    decoded_objects = pyzbar.decode(image)
    for obj in decoded_objects:
        if obj.type == 'QRCODE':
            return obj.data.decode('utf-8')
    return None

def scan_rec(image):
    qr_data = read_qr(image)

    if qr_data:
        print("[✅] Найден QR-код!")
        print("Содержимое QR:", qr_data)
        # Здесь можно дополнительно распарсить данные чека из qr_data
    else:
        print("[❌] QR-код не найден, переходим к OCR...")
        # OCR через EasyOCR
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        reader = easyocr.Reader(['ru'], gpu=False)
        results = reader.readtext(gray, detail=0)

        for line in results:
            print(line)