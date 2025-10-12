curl -i -b ../cookies.txt \
-X PUT http://localhost:8080/api/article/1 \
-H "Content-Type: application/json" \
-d '{
    "imageUrls":["https://...12311", "https://..."]
    }'