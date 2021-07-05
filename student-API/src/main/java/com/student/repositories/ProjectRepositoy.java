package com.student.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.student.models.Project;

@Repository
public interface ProjectRepositoy extends JpaRepository<Project, Long> {

    public List<Project> findAllByStudentIdIn(List<Long> studentIDs);
}
