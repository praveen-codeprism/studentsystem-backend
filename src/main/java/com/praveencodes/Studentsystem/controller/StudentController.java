package com.praveencodes.Studentsystem.controller;

import com.praveencodes.Studentsystem.model.Student;
import com.praveencodes.Studentsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Student student) {
        if (studentService.existsByEmail(student.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already in use.");
        }

        // Assuming you have validation logic for password strength, etc.
        if (student.getPassword() == null || student.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password cannot be empty.");
        }

        // Save student
        studentService.saveStudent(student);

        return ResponseEntity.status(HttpStatus.CREATED).body("Signup successful. Welcome, " + student.getName() + "!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Student student) {
        boolean loginSuccessful = studentService.loginStudent(student.getEmail(), student.getPassword());
        if (loginSuccessful) {
            // Here you can add any additional information you want to include in the response
            String message = "Login successful. Welcome, " + student.getName() + "!";
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed. Incorrect email or password.");
        }
    }

    @PostMapping("/add-all")
    public String addAll(@RequestBody List<Student> students) {
        students.forEach(student -> studentService.saveStudent(student));
        return "Multiple students are added";
    }

    @GetMapping("/getAll")
    public List<Student> list() {
        return studentService.getAllStudents();
    }

    @GetMapping("/get/{id}")
    public Student getStudent(@PathVariable("id") Long id) {
        return studentService.getStudentById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return "Student with ID " + id + " has been deleted.";
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @RequestBody Student student) {
        Student existingStudent = studentService.getStudentById(id);
        if (existingStudent == null) {
            return "Student with ID " + id + " not found.";
        } else {
            existingStudent.setName(student.getName());
            existingStudent.setAddress(student.getAddress());
            existingStudent.setNumber(student.getNumber());
            existingStudent.setPassword(student.getPassword());
            existingStudent.setGender(student.getGender());
            existingStudent.setEmail(student.getEmail());

            studentService.saveStudent(existingStudent);
            return "Student with ID " + id + " has been updated.";
        }
    }
}
