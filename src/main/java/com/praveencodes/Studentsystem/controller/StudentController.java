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
    public ResponseEntity<StudentResponse> signup(@RequestBody Student student) {
        if (studentService.existsByEmail(student.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new StudentResponse(null, "Email is already in use."));
        }

        if (student.getPassword() == null || student.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StudentResponse(null, "Password cannot be empty."));
        }

        if (student.getRole() == null) {
            student.setRole(studentService.getDefaultRole());
        }

        Student savedStudent = studentService.saveStudent(student);

        if (savedStudent == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new StudentResponse(null, "Failed to save student."));
        }

        StudentResponse response = new StudentResponse(savedStudent, "Signup successful. Welcome, " + savedStudent.getName() + "!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<StudentResponse> login(@RequestBody Student student) {
        boolean loginSuccessful = studentService.loginStudent(student.getEmail(), student.getPassword());
        if (loginSuccessful) {
            Student loggedInStudent = studentService.findByEmail(student.getEmail());
            if (loggedInStudent == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new StudentResponse(null, "Failed to fetch student details after login."));
            }
            String message = "Login successful. Welcome, " + loggedInStudent.getName() + "!";
            StudentResponse response = new StudentResponse(loggedInStudent, message);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new StudentResponse(null, "Login failed. Incorrect email or password."));
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
            existingStudent.setRole(student.getRole());

            studentService.saveStudent(existingStudent);
            return "Student with ID " + id + " has been updated.";
        }
    }

    // Response DTO class to include student and message
    public static class StudentResponse {
        private Student student;
        private String message;

        public StudentResponse(Student student, String message) {
            this.student = student;
            this.message = message;
        }

        public Student getStudent() {
            return student;
        }

        public void setStudent(Student student) {
            this.student = student;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
