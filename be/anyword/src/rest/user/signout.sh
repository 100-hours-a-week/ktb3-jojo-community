curl -i -b cookies.txt \
-X DELETE http://localhost:8080/api/user/current/signout \
-H "Content-Type: application/json"