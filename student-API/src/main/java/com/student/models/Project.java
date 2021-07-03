package com.student.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Project done by student
 * 
 * @author Anurag Asthana
 *
 */
@Entity
@Table(name = "project")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project extends BaseEntity {
	
	@Column(name = "name")
	String name;
	
	@ManyToOne
	@JoinColumn(name = "photo_id")
	Files photo;
	
	@ManyToOne
	@JoinColumn(name = "student_id")
	Student student;
	
	/**
	 * Project duration in days
	 */
	@Column(name = "duration")
	Integer duration;
	
	@Transient
	String base64encoding;
}
