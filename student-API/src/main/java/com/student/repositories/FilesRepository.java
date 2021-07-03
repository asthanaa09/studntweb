package com.student.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.student.models.Files;

@Repository
public interface FilesRepository extends JpaRepository<Files, Long> {

}
