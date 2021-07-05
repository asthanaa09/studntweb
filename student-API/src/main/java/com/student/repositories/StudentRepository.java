package com.student.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.student.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    public Student findByEmail(String email);
}
