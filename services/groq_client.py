import os
import requests
import time
import logging
from dotenv import load_dotenv


load_dotenv()

logging.basicConfig(level=logging.INFO)

class GroqClient:
    def __init__(self, model="llama-3.1-8b-instant"):
        self.api_key = os.getenv("GROQ_API_KEY")

        if not self.api_key:
            raise ValueError("❌ GROQ_API_KEY not found in .env")

        self.model = model
        self.base_url = "https://api.groq.com/openai/v1/chat/completions"

        self.headers = {
            "Authorization": f"Bearer {self.api_key}",
            "Content-Type": "application/json"
        }

    def generate(self, prompt):
        payload = {
            "model": self.model,
            "messages": [
                {
                    "role": "system",
                    "content": "You are a helpful AI assistant. Answer clearly and correctly."
                },
                {
                    "role": "user",
                    "content": prompt
                }
            ],
            "temperature": 0.3,
            "max_tokens": 512
        }

        for attempt in range(3):
            try:
                response = requests.post(
                    self.base_url,
                    headers=self.headers,
                    json=payload,
                    timeout=15
                )


                response.raise_for_status()
                data = response.json()

                return data["choices"][0]["message"]["content"]

            except requests.exceptions.RequestException as e:
                logging.error(f"Attempt {attempt + 1} failed: {e}")

                if attempt < 2:
                    time.sleep(2 ** attempt) 
                else:
                    return "❌ Error: API failed after retries"



if __name__ == "__main__":
    client = GroqClient()

    print(" Chatbot (type 'exit' to stop)\n")

    while True:
        user_input = input("You: ")

        if user_input.lower() == "exit":
            break

        reply = client.generate(user_input)
        print("Bot:", reply)