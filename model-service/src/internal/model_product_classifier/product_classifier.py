import torch
from transformers import AutoTokenizer, AutoModel
import pickle
from torch import nn

# Загрузка токенизатора
print("Загрузка токена")
#tokenizer = AutoTokenizer.from_pretrained("src/internal/model_product_classifier/tokenizer", local_files_only=True)
tokenizer = AutoTokenizer.from_pretrained("ai-forever/ruBert-base", token="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")  # Пример с моделью Hugging Face
print(tokenizer)

# Загрузка LabelEncoder
with open("src/internal/model_product_classifier/label_encoder.pkl", "rb") as f:
    le = pickle.load(f)

# Устройство
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

# Модель
class ProductClassifier(nn.Module):
    def __init__(self, model_name, num_classes):
        super(ProductClassifier, self).__init__()
        self.bert = AutoModel.from_pretrained(model_name)
        self.fc = nn.Linear(self.bert.config.hidden_size, num_classes)

    def forward(self, input_ids, attention_mask):
        outputs = self.bert(input_ids=input_ids, attention_mask=attention_mask)
        cls_output = outputs.last_hidden_state[:, 0, :]
        logits = self.fc(cls_output)
        return logits

# Инициализация модели
model = ProductClassifier(model_name="ai-forever/ruBert-base", num_classes=len(le.classes_))
model.load_state_dict(torch.load("src/internal/model_product_classifier/product_classifier.pth", map_location=device))
model = model.to(device)
model.eval()

# Функция для предсказания категории
def predict_category(name: str) -> str:
    inputs = tokenizer(name, return_tensors="pt", padding=True, truncation=True, max_length=128)
    inputs = {key: val.to(device) for key, val in inputs.items() if key in ["input_ids", "attention_mask"]}

    with torch.no_grad():
        outputs = model(**inputs)
        preds = torch.argmax(outputs, dim=1)

    category = le.inverse_transform(preds.cpu().numpy())[0]
    return category
