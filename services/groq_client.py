import os
import requests
import time
import logging
import json
from dotenv import load_dotenv

load_dotenv()
logging.basicConfig(level=logging.INFO)

class GroqClient:
    def __init__(self, model="llama3-8b-8192"):
        self.api_key = os.getenv("GROQ_API_KEY")

        if not self.api_key:
            raise ValueError("❌ GROQ_API_KEY not found in .env")

        self.model = model
        self.url = "https://api.groq.com/openai/v1/chat/completions"

        self.headers = {
            "Authorization": f"Bearer {self.api_key}",
            "Content-Type": "application/json"
        }

    def generate(self, prompt, retries=3):
        payload = {
            "model": self.model,
            "messages": [
                {
                    "role": "system",
                    "content": "You MUST return valid JSON only. No extra text."
                },
                {
                    "role": "user",
                    "content": prompt
                }
            ],
            "temperature": 0.3,
            "max_tokens": 512
        }

        for attempt in range(retries):
            try:
                response = requests.post(
                    self.url,
                    headers=self.headers,
                    json=payload,
                    timeout=15
                )

                response.raise_for_status()
                data = response.json()

                content = data["choices"][0]["message"]["content"]

                logging.info(f"Groq raw response: {content}")

                try:
                    return json.loads(content)
                except json.JSONDecodeError:
                    return {
                        "category": "Unknown",
                        "confidence": 0.0,
                        "reasoning": "Invalid JSON from model"
                    }

            except requests.exceptions.RequestException as e:
                logging.error(f"Attempt {attempt + 1} failed: {e}")

                if attempt < retries - 1:
                    time.sleep(2 ** attempt)
                else:
                    return {
                        "category": "Error",
                        "confidence": 0.0,
                        "reasoning": "API failed after retries"
                    }