curl -i -b ../cookies.txt \
-X DELETE http://localhost:8080/api/article/1 \
-H "Content-Type: application/json"