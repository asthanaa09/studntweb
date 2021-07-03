package com.student.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.student.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
