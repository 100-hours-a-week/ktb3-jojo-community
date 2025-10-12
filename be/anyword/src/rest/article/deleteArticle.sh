curl -i -b ../cookies.txt \
-X DELETE http://localhost:8080/api/article \
-H "Content-Type: application/json"