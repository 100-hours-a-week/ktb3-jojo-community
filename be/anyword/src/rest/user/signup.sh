curl -X POST http://localhost:8080/api/user/signup \
-H "Content-Type: application/json" \
-d '{
  "email": "jojo@email.com",
  "password": "Jojo1234!",
  "nickname": "startup",
  "profileImageUrl": "https://example.com/image.png"
}'