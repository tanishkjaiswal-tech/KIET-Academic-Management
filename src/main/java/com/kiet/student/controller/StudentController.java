package com.kiet.student.controller;

import com.kiet.student.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {

    // Assessment requirement: keep data in an in-memory List; no database.
    private final List<Student> students = new ArrayList<>(List.of(
        new Student(101, "Rahul Sharma", "rahul@kiet.edu", "CSE(AI & ML)", 2, 8.65),
        new Student(102, "Priya Singh", "priya@kiet.edu", "CSE", 2, 9.10),
        new Student(103, "Aman Verma", "aman@kiet.edu", "IT", 1, 8.20),
        new Student(104, "Sneha Gupta", "sneha@kiet.edu", "CSE(AI)", 3, 9.25),
        new Student(105, "Karan Yadav", "karan@kiet.edu", "ECE", 2, 7.95)
    ));

    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable int studentId) {
        Optional<Student> student = findStudent(studentId);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Student not found"));
    }

    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        if (findStudent(student.getStudentId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Student ID already exists"));
        }
        students.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable int studentId,
                                           @RequestBody Student updatedStudent) {
        Optional<Student> existing = findStudent(studentId);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Student not found"));
        }

        Student student = existing.get();
        student.setName(updatedStudent.getName());
        student.setEmail(updatedStudent.getEmail());
        student.setBranch(updatedStudent.getBranch());
        student.setYear(updatedStudent.getYear());
        student.setCgpa(updatedStudent.getCgpa());
        // The ID in the URL remains the record ID.
        student.setStudentId(studentId);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable int studentId) {
        Optional<Student> student = findStudent(studentId);
        if (student.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Student not found"));
        }
        students.remove(student.get());
        return ResponseEntity.ok(Map.of("message", "Student deleted successfully"));
    }

    @GetMapping("/search")
    public List<Student> searchByBranchAndYear(@RequestParam String branch,
                                               @RequestParam int year) {
        String requestedBranch = branch.trim().toLowerCase();
        return students.stream()
                .filter(student -> student.getBranch() != null
                        && (student.getBranch().equalsIgnoreCase(branch.trim())
                        || student.getBranch().toLowerCase().startsWith(requestedBranch)))
                .filter(student -> student.getYear() == year)
                .collect(Collectors.toList());
    }

    @GetMapping("/topper")
    public List<Student> getStudentsByMinimumCgpa(@RequestParam double cgpa) {
        return students.stream()
                .filter(student -> student.getCgpa() >= cgpa)
                .collect(Collectors.toList());
    }

    private Optional<Student> findStudent(int studentId) {
        return students.stream()
                .filter(student -> student.getStudentId() == studentId)
                .findFirst();
    }
}
