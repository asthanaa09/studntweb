package com.student.rest;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.models.Student;

@RestController
@RequestMapping("/api/student")
@RolesAllowed("ROLE_ADMIN")
public class StudentController {

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Student student) {
	return new ResponseEntity<String>("Success", HttpStatus.OK);
    }
}
