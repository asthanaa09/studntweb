package com.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.models.Student;
import com.student.repositories.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository mStudentRepository;
	
	public Student save(Student student) {
		return save(student, null);
	}
	
	public Student save(Student student, Long studentID) {
		if(studentID != null) {
			// Update
			student.setId(studentID);
			student = update(student);
		
		} else {
			// Create Student and project
			student = mStudentRepository.save(student);
		}
		
		return student;
	}
	
	private Student update(Student student) {
		return student;
	}
	
	public Student findStudent(String email) {
		return mStudentRepository.findByEmail(email);
	}
}
