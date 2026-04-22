from services.groq_client import GroqClient

client = GroqClient()  
while True:
    text = input("Enter your question: ")

    if text.lower() == "exit":
        break

    response = client.generate(text)
    print("AI:", response)