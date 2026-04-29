from flask import Blueprint, request, jsonify
from services.groq_client import GroqClient

categorise_bp = Blueprint("categorise", __name__)

groq = GroqClient()

CATEGORIES = [
    "Security",
    "Compliance",
    "Performance",
    "Risk",
    "Operations"
]

@categorise_bp.route("/categorise", methods=["POST"])
def categorise():
    data = request.get_json()

    if not data or "text" not in data:
        return jsonify({"error": "Missing 'text' field"}), 400

    text = data["text"]

    prompt = f"""
You are an expert compliance analyst.

Classify the following text into ONE of these categories:
{", ".join(CATEGORIES)}

Return JSON only:
{{
  "category": "...",
  "confidence": 0.0 to 1.0,
  "reasoning": "..."
}}

Text:
{text}
"""

    try:
        response = groq.generate(prompt)

        return jsonify(response)

    except Exception as e:
        return jsonify({
            "error": "AI processing failed",
            "details": str(e)
        }), 500