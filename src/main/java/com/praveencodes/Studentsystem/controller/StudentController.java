package com.praveencodes.Studentsystem.controller;

import com.praveencodes.Studentsystem.model.Student;
import com.praveencodes.Studentsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public String add(@RequestBody Student student) {
        studentService.saveStudent(student);
        return "New student is added";
    }

    @PostMapping("/add-all")
    public String addAl(@RequestBody List<Student> students) {
        students.stream().forEach(student->studentService.saveStudent(student));
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
            existingStudent.setGender(student.getGender());
            studentService.saveStudent(existingStudent);
            return "Student with ID " + id + " has been updated.";
        }

    }
}
