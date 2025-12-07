curl -i -b ../cookies.txt \
-X GET "http://localhost:8080/api/article?currentPage=1&pageSize=20&sort=popular" \
-H "Content-Type: application/json"