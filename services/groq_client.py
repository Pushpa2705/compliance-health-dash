import os
import requests
from dotenv import load_dotenv

load_dotenv()

class GroqClient:
    def __init__(self, model="llama-3.1-8b-instant"):
        self.api_key = os.getenv("GROQ_API_KEY")

        if not self.api_key:
            raise ValueError("❌ GROQ_API_KEY not found in .env")

        self.model = model
        self.url = "https://api.groq.com/openai/v1/chat/completions"

    def generate(self, prompt):
        payload = {
            "model": self.model,
            "messages": [
                {
                    "role": "user",
                    "content": prompt
                }
            ]
        }

        try:
            response = requests.post(
                self.url,
                headers={
                    "Authorization": f"Bearer {self.api_key}",
                    "Content-Type": "application/json"
                },
                json=payload,
                timeout=10
            )

            # 🔥 PRINT FULL ERROR (IMPORTANT)
            if response.status_code != 200:
                print("❌ FULL ERROR:", response.text)
                return "Error: Unable to generate answer"

            data = response.json()

            return data["choices"][0]["message"]["content"]

        except Exception as e:
            print("❌ Exception:", e)
            return "Error: Unable to generate answer"