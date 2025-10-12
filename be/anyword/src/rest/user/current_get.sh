curl -i -b cookies.txt \
-X GET http://localhost:8080/api/user/current \
-H "Content-Type: application/json"