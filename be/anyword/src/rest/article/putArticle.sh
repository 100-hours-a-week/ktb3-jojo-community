curl -i -b ../cookies.txt \
-X PUT http://localhost:8080/api/article \
-H "Content-Type: application/json" \
-d '{
    "imageUrls":["https://...", "https://..."]
    }'