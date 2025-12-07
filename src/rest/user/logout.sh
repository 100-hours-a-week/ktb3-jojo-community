curl -i -b ../cookies.txt \
-X POST http://localhost:8080/api/user/current/logout \
-H "Content-Type: application/json"