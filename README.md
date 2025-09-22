# FIT AAJ Backend (Spring Boot + MySQL)

## Endpoints
- POST `/api/auth/register` → { message: "signup successful" }
- POST `/api/auth/login` → { message: "login successful", userId, name }
- POST `/api/dashboard` → { message: "dashboard saved" }
- GET `/api/dashboard/{userId}` → [ DashboardEntry ]

## Setup
1. Create MySQL DB named `fitaaj`.
2. Set environment variables (PowerShell):
```
$env:DB_URL="jdbc:mysql://localhost:3306/fitaaj?useSSL=false&serverTimezone=UTC"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_password"
$env:CORS_ORIGIN="http://localhost:5173"
```
3. Run backend:
```
mvn -q -f .\fit-aaj-backend\pom.xml spring-boot:run
```

## Sample Requests
- Register:
```
curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d '{"name":"John","email":"john@example.com","password":"pass123"}'
```
- Login:
```
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"email":"john@example.com","password":"pass123"}'
```
- Save dashboard:
```
curl -X POST http://localhost:8080/api/dashboard -H "Content-Type: application/json" -d '{"userId":1,"date":"2025-09-22","steps":5000,"waterMl":1500,"exerciseMinutes":30,"caloriesIntake":1800}'
```
- List dashboard:
```
curl http://localhost:8080/api/dashboard/1
```
