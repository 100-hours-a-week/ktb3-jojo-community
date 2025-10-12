curl -i -b cookies.txt \
-X PUT http://localhost:8080/api/user/current \
-H "Content-Type: application/json" \
-d '{
  "email": "jo@email.com",
  "password": null
}'