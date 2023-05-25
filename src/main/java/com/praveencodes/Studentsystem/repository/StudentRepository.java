package com.praveencodes.Studentsystem.repository;

import com.praveencodes.Studentsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    Student getStudentById(int intValue);
}
