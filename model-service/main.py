import base64
from io import BytesIO

import cv2
import numpy as np
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from PIL import Image
import logging

from starlette.responses import JSONResponse

from src.services.scan_receipt import scan_rec

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = FastAPI()

class ReceiptRequest(BaseModel):
    userId: int
    data: str

@app.post("/api/receipts")
async def load_receipt(request: ReceiptRequest):
    logger.info(f"Received request: userId={request.userId}")

    if not request.data.startswith('data:image/'):
        raise HTTPException(status_code=400, detail="Invalid image format. Expected data:image/...;base64,...")

    try:
        header, base64_data = request.data.split(',', 1)
        image_bytes = base64.b64decode(base64_data)

        image = Image.open(BytesIO(image_bytes))
        image_np = np.array(image)
        image_cv = cv2.cvtColor(image_np, cv2.COLOR_RGB2BGR)

        receipt_data = await scan_rec(image_cv)

        if receipt_data is None:
            raise HTTPException(status_code=404, detail="Receipt data not found")

        # Формируем ручной словарь ответа
        return {
            "retailPlace": receipt_data.retailPlace,
            "inn": receipt_data.inn,
            "date": receipt_data.date.isoformat(),  # datetime в строку
            "total_sum": receipt_data.total_sum,
            "address": receipt_data.address,
            "items": [
                {
                    "name": item.name,
                    "price": item.price,
                    "quantity": item.quantity,
                    "total": item.total,
                    "nds": item.nds,
                    "category": item.category
                }
                for item in receipt_data.items
            ],
            "fiscal_document_number": receipt_data.fiscal_document_number,
            "fiscal_sign": receipt_data.fiscal_sign
        }

    except Exception as e:
        logger.error(f"Unexpected error: {str(e)}")
        raise HTTPException(status_code=500, detail="Internal server error")

@app.get("/api/tests")
async def root():
    return {"message": "Hello World"}

@app.get("/hello/{name}")
async def say_hello(name: str):
    return {"message": f"Hello {name}"}


