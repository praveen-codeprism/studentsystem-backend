package com.praveencodes.Studentsystem.service;

import com.praveencodes.Studentsystem.model.Student;
import com.praveencodes.Studentsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id.intValue());
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id.intValue()).orElse(null);
    }

    @Override
    public boolean loginStudent(String email, String password) {
        Student student = studentRepository.findByEmail(email);
        return student != null && student.getPassword().equals(password);
    }

    @Override
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
}
