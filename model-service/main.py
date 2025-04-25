from fastapi import FastAPI
from pydantic import BaseModel
import logging


logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = FastAPI()

class ReceiptRequest(BaseModel):
    userId: int
    data: str

@app.post("/api/receipts")
async def load_receipt(request: ReceiptRequest):
    logger.info(f"Received request: userId={request.userId}, data={request.data}")
    return [
        {"userId": 1, "receiptId": 1, "name": "Product1", "price": 10.99, "categoryName": "Category1"},
        {"userId": 1, "receiptId": 2, "name": "Product2", "price": 5.99, "categoryName": "Category2"}
    ]

@app.get("/api/tests")
async def root():
    return {"message": "Hello World"}

@app.get("/hello/{name}")
async def say_hello(name: str):
    return {"message": f"Hello {name}"}
