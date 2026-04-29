import requests

while True:
    question = input("Ask your question: ")

    response = requests.post(
        "http://127.0.0.1:5000/query",
        json={"question": question}
    )

    print("Answer:", response.json()["answer"])