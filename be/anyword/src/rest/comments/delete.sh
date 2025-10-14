curl -i -b ../cookies.txt \
-X DELETE http://localhost:8080/api/comments/1 \
-H "Content-Type: application/json"