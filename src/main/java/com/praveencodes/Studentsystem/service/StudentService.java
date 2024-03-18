package com.praveencodes.Studentsystem.service;

import com.praveencodes.Studentsystem.model.Student;

import java.util.List;

public interface StudentService {
    Student saveStudent(Student student);

    boolean loginStudent(String name, String password);

    List<Student> getAllStudents();

    void deleteStudent(Long id);

    Student getStudentById(Long id);
}
