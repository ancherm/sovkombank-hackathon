from typing import List, Dict, Optional
from pydantic import BaseModel
from datetime import datetime

from src.internal.model_product_classifier.product_classifier import predict_category


class ReceiptItem(BaseModel):
    name: str
    price: float  # в рублях
    quantity: float
    total: float  # в рублях
    category: Optional[str] = None

class Receipt(BaseModel):
    retailPlace: str
    inn: str
    date: datetime
    total_sum: float
    items: List[ReceiptItem]

def parse_receipt(api_response: dict) -> Receipt:
    json_data = api_response["data"]["json"]

    # Обработка товаров
    items = []
    for item in json_data["items"]:
        category = predict_category(item["name"].strip())
        items.append(ReceiptItem(
            name=item["name"].strip(),
            price=item["price"] / 100,  # переводим из копеек в рубли
            quantity=item["quantity"],
            total=item["sum"] / 100,    # переводим из копеек в рубли
            category=category  # можно добавить категоризацию товаров
        ))

    # Основная информация о чеке
    return Receipt(
        retailPlace=json_data["retailPlace"].strip(),
        inn=json_data["userInn"].strip(),
        date=datetime.fromisoformat(json_data["dateTime"]),
        total_sum=json_data["totalSum"] / 100,
        items=items,
    )