package com.student.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.student.utils.Utils;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Setter
@Getter
public class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_time")
	Date creationTime;
	
	@PrePersist
	protected void onCreate() {
		if(this.getCreationTime() == null)
			this.setCreationTime(Utils.now());
	}
	
	/**
	 * Generate unique has-number for every object. Reduce hash colision problem
	 * 
	 * ref : https://stackoverflow.com/questions/2265503/why-do-i-need-to-override-the-equals-and-hashcode-methods-in-java
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	/**
	 * Ref : https://stackoverflow.com/questions/2265503/why-do-i-need-to-override-the-equals-and-hashcode-methods-in-java
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null || this == null)
			return false;
		if(this != obj)
			return false;
		if(this.getClass() != obj.getClass())
			return false;
		
		BaseEntity comparable = (BaseEntity) obj;
		if(this.id == null) {
			if(comparable.getId() != null)
				return false;
		} else if(!comparable.getId().equals(this.getId()))
			return false;
		
		return true;
	}
}
