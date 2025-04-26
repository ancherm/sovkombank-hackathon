from typing import List, Dict, Optional
from pydantic import BaseModel
from datetime import datetime

class ReceiptItem(BaseModel):
    name: str
    price: float  # в рублях
    quantity: float
    total: float  # в рублях
    nds: Optional[int] = None
    category: Optional[str] = None

class Receipt(BaseModel):
    retailPlace: str
    inn: str
    date: datetime
    total_sum: float
    address: str
    items: List[ReceiptItem]
    fiscal_document_number: str
    fiscal_sign: int

def parse_receipt(api_response: dict) -> Receipt:
    json_data = api_response["data"]["json"]

    # Обработка товаров
    items = []
    for item in json_data["items"]:
        items.append(ReceiptItem(
            name=item["name"].strip(),
            price=item["price"] / 100,  # переводим из копеек в рубли
            quantity=item["quantity"],
            total=item["sum"] / 100,    # переводим из копеек в рубли
            nds=item.get("nds"),
            category=None  # можно добавить категоризацию товаров
        ))

    # Основная информация о чеке
    return Receipt(
        retailPlace=json_data["retailPlace"].strip(),
        inn=json_data["userInn"].strip(),
        date=datetime.fromisoformat(json_data["dateTime"]),
        total_sum=json_data["totalSum"] / 100,
        address=json_data["retailPlaceAddress"].strip(),
        items=items,
        fiscal_document_number=str(json_data["fiscalDocumentNumber"]),
        fiscal_sign=json_data["fiscalSign"]
    )