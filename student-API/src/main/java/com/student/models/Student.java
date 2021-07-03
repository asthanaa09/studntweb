package com.student.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.student.models.simple.StudentExtras;
import com.student.utils.Utils;
import com.sun.istack.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * TODO: Add validation
 * 
 * @author Anurag Asthana
 *
 */
@Entity
@Table(name = "student")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseEntity {
	
	@Column(name = "first_name")
	String name;
	
	@Column(name = "mid_name")
	String midName;
	
	@Column(name = "last_name")
	String lastName;
	
	@Column(name = "mobile")
	String mobile;
	
	@Column(name = "email")
	@NotNull
	@NotBlank
	@Email
	String email;
	
	@Column(name = "dob")
	@Temporal(TemporalType.DATE)
	Date dob;
	
	@Column(name = "roll_no")
	String rollNumber;
	
	@Column(name = "extras")
	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	String extrasScript;
	
	@Transient
	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	StudentExtras extras;
	
	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	Date updateTime;
	
	@PrePersist
	public void onUpdate() {
		this.setUpdateTime(Utils.now());
	}
	
	public String getExtrasScript() {
		return extrasScript;
	}

	public void setExtrasScript(String extrasScript) {
		this.extrasScript = extrasScript;
		this.extras = StudentExtras.fromJSON(extrasScript);
	}

	public StudentExtras getExtras() {
		return extras;
	}

	public void setExtras(StudentExtras extras) {
		this.extras = extras;
		if(extras != null)
			this.extrasScript = this.toString();
	}
}