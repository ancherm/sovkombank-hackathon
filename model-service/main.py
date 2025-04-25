from fastapi import FastAPI

app = FastAPI()


@app.get("/api/tests")
async def root():
    return {"message": "Hello World"}

@app.post("/api/receipts")
async def root():
    return [
        {"userId": 1, "receiptId": 1, "name": "Product1", "price": 10.99, "categoryName": "Category1"},
        {"userId": 1, "receiptId": 2, "name": "Product2", "price": 5.99, "categoryName": "Category2"}
    ]

@app.get("/hello/{name}")
async def say_hello(name: str):
    return {"message": f"Hello {name}"}
