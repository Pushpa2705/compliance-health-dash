import requests

url = "http://127.0.0.1:5000/query"

def score_answer(answer):
    score = 0

    if answer and len(answer) > 20:
        score += 3

    keywords = ["encrypt", "data", "security", "protect"]
    if any(k in answer.lower() for k in keywords):
        score += 3

    if "not found" not in answer.lower():
        score += 2

    if "\n" not in answer:
        score += 2

    return score


total_score = 0
count = 0

print("👉 Enter 10 questions (type 'exit' to stop)\n")

while count < 10:
    q = input(f"Q{count+1}: ")

    if q.lower() == "exit":
        break

    res = requests.post(url, json={"question": q})
    data = res.json()

    answer = data.get("answer", "")

    s = score_answer(answer)

    print("Answer:", answer)
    print("Score:", s, "/10\n")

    total_score += s
    count += 1

if count > 0:
    print("Average Score:", total_score / count)