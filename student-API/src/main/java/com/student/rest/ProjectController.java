package com.student.rest;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.student.models.Project;
import com.student.models.Student;
import com.student.repositories.StudentRepository;
import com.student.response.ApiResponse;
import com.student.service.ProjectService;
import com.student.service.StudentService;

@RestController
@RequestMapping("/api/project")
@RolesAllowed("ROLE_ADMIN")
public class ProjectController {

    @Autowired
    ProjectService mProjectService;
    @Autowired
    StudentRepository mStudentRepository;

    @Autowired
    StudentService mStudentService;

    /**
     * TODO: Implement Exception Spring handler
     * 
     * @param project
     * @param studentID
     * @return
     */
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Project project, @RequestParam("firstName") String firstName,
	    @RequestParam(value = "midName", required = false) String midName,
	    @RequestParam(value = "lastName", required = false) String lastName,
	    @RequestParam(value = "email", required = true) String email,
	    @RequestParam(value = "mobile", required = false) String mobile) {

	ApiResponse response = new ApiResponse();

	try {
	    project.setStudent(getStudent(firstName, lastName, mobile, email));
	    project = mProjectService.insert(project);
	    response.setHttpStatusCode(HttpStatus.OK.value());
	    response.setResult(project);
	} catch (Exception e) {
	    response.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
	    response.setMessage(e.getMessage());
	    e.printStackTrace();
	}

	return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    /**
     * Find student by email, create/update if not exists
     * 
     * @param firstName
     * @param lastName
     * @param mobile
     * @param email
     * @return
     */
    private Student getStudent(String firstName, String lastName, String mobile, String email) {
	Student student = mStudentRepository.findByEmail(email);

	if (student == null)
	    student = new Student();

	student = new Student();
	student.setName(firstName);
	student.setMobile(mobile);
	student.setEmail(email);
	student.setMobile(mobile);

	return mStudentService.save(student);
    }
}
