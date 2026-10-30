# KIET Student Academic Management REST API

This Spring Boot project implements all six questions in the uploaded MSE2 Sample Question case study. It uses an in-memory `List<Student>`; no database is configured.

## Requirements
- Java 17 or later
- Maven 3.6+ (or open the project in IntelliJ IDEA and let it import the Maven project)

## Run
1. Extract the ZIP.
2. Open the project folder containing `pom.xml` in IntelliJ IDEA.
3. Ensure the Project SDK is Java 17 or later.
4. Run `src/main/java/com/kiet/student/StudentApplication.java`, or use:
   ```bash
   mvn spring-boot:run
   ```
5. The server runs at `http://localhost:8080`.

At startup, the application logger prints:
- KIET Student Management System Started
- Loading Student Data...
- Student Management REST API is Ready

## Endpoint checklist

| Case-study task | Method | URL |
|---|---|---|
| Q3: Get all students | GET | `/students` |
| Q3: Get student by ID | GET | `/students/101` |
| Q4: Add a student | POST | `/students` |
| Q5: Update a student | PUT | `/students/101` |
| Q5: Delete a student | DELETE | `/students/105` |
| Q6: Search by branch and year | GET | `/students/search?branch=CSE&year=2` |
| Q6: Minimum CGPA | GET | `/students/topper?cgpa=9.0` |

## Example POST /students JSON
```json
{
  "studentId": 106,
  "name": "Amit Kumar",
  "email": "amit@kiet.edu",
  "branch": "CSE(AI)",
  "year": 2,
  "cgpa": 8.75
}
```

## Example PUT /students/101 JSON
```json
{
  "studentId": 101,
  "name": "Rahul Sharma",
  "email": "rahul@kiet.edu",
  "branch": "CSE(AI & ML)",
  "year": 3,
  "cgpa": 9.05
}
```

## Expected error responses
Missing student:
```json
{"message":"Student not found"}
```
Duplicate ID on POST:
```json
{"message":"Student ID already exists"}
```

## Testing with Postman
For each request, choose `Body` -> `raw` -> `JSON` for POST and PUT. Capture screenshots of the request URL/method, JSON body where applicable, and response body/status for Moodle submission.

Note: the data is stored only in memory. Restarting the application resets the student list. Branch search intentionally accepts a prefix such as `CSE` to match `CSE`, `CSE(AI)`, and `CSE(AI & ML)`.
