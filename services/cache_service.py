import redis
import hashlib
import json


class CacheService:
    def __init__(self):
        self.client = redis.Redis(
            host="localhost",
            port=6379,
            db=0,
            decode_responses=True
        )
        self.ttl = 900  # 15 minutes

    def make_cache_key(self, question: str):
        return hashlib.sha256(question.encode()).hexdigest()

    def get(self, question: str):
        key = self.make_cache_key(question)

        try:
            data = self.client.get(key)

            if data:
                self.client.incr("cache_hits")
                return json.loads(data)

            self.client.incr("cache_miss")
            return None

        except:
            return None

    def set(self, question: str, value):
        key = self.make_cache_key(question)

        self.client.setex(
            key,
            self.ttl,
            json.dumps(value)
        )

    def stats(self):
        return {
            "hits": int(self.client.get("cache_hits") or 0),
            "miss": int(self.client.get("cache_miss") or 0)
        }