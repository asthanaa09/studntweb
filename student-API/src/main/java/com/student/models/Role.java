package com.student.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Setter
@Getter
public class Role extends BaseEntity {

	public enum RoleType {
		STUDENT, 
		TEACHER,
		/**
		 * Allows User to manage application
		 */
		ADMIN
	}

	@Column(name = "role")
	@Enumerated(EnumType.ORDINAL)
	private RoleType roleType;
}
