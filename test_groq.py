import os
from dotenv import load_dotenv
from groq import Groq

load_dotenv()

client = Groq(api_key=os.getenv("GROQ_API_KEY"))

user_input = input("Enter your question: ")

response = client.chat.completions.create(
    model="llama-3.1-8b-instant",
    messages=[
        {"role": "system", "content": "You are a helpful assistant. Answer clearly and correctly."},
        {"role": "user", "content": user_input}
    ]
)

reply = response.choices[0].message.content

print("\nAI Response:")
print(reply)