curl -i -b ../cookies.txt \
-X POST http://localhost:8080/api/article \
-H "Content-Type: application/json" \
-d '{
    "title":"제목 1",
    "content":"내용",
    "imageUrls":["https://...", "https://..."]
    }'