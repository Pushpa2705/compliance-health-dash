import json
import chromadb
from sentence_transformers import SentenceTransformer

# 🔹 Load external JSON file
with open("data/health_data.json", "r") as f:
    data = json.load(f)

# 🔹 Init Chroma
client = chromadb.PersistentClient(path="./data/chroma_db")

collection = client.get_or_create_collection(
    name="health_compliance"
)

model = SentenceTransformer("all-MiniLM-L6-v2")

documents = []
metadatas = []
ids = []

# 🔹 Process data
for item in data:
    text = f"{item['title']}: {item['content']}"

    documents.append(text)
    metadatas.append({
        "category": item["category"],
        "title": item["title"]
    })
    ids.append(item["id"])

# 🔹 Create embeddings
embeddings = model.encode(documents).tolist()

# 🔹 Insert into DB
collection.add(
    documents=documents,
    embeddings=embeddings,
    metadatas=metadatas,
    ids=ids
)

print("✅ External JSON data inserted into ChromaDB!")