package com.student.response;

import com.student.models.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {
	
	Integer httpStatusCode;
	String message;
	String token;
	User user;
}
