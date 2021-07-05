package com.student.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.models.Project;
import com.student.repositories.FileService;
import com.student.repositories.ProjectRepositoy;

@RestController
@RequestMapping("/api/project")
public class ProjectService {

    @Autowired
    private ProjectRepositoy mProjectRepositoy;
    @Autowired
    private FileService mFileSerive;

    /**
     * Create new Project for corresponding student
     * 
     * @param project
     * @return
     */
    public Project insert(Project project) {
	project = mProjectRepositoy.save(project);
	// 1. Save photo
	project = mFileSerive.createPhoto(project);

	return project;
    }

    /**
     * Get All project List.
     * 
     * @return
     */
    public List<Project> getAll() {
	return mProjectRepositoy.findAll();
    }

    /**
     * Get list of project done by student
     * 
     * @param studentID
     * @return
     */
    public List<Project> findByStudentId(Long studentID) {
	return mProjectRepositoy.findAllByStudentIdIn(Arrays.asList(studentID));
    }
}
