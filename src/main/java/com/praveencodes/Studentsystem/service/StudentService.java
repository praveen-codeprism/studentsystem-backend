package com.praveencodes.Studentsystem.service;

import com.praveencodes.Studentsystem.model.Student;

import java.util.List;

public interface StudentService {
    public Student saveStudent(Student student);
    public List<Student> getAllStudents();

    void deleteStudent(Long id);

    Student getStudentById(Long id);
}
