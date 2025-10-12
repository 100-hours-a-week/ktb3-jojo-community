curl -i -c cookies.txt \
-X POST http://localhost:8080/api/user/login \
-H "Content-Type: application/json" \
-d '{
  "email": "jojo@email.com",
  "password": "Jojo1234!"
}'