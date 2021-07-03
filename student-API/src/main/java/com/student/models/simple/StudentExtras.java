package com.student.models.simple;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentExtras {

	String fatherName;
	String motherName;
	String fatherContact;
	String fatherEmail;
	String permanentAddress;
	
	public static StudentExtras fromJSON(String script) {
		StudentExtras extras = null;
		try {
			extras = new Gson().fromJson(script, StudentExtras.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return extras;
	}
	
	public String toJSON() {
		return new Gson().toJson(this);
	}
}
