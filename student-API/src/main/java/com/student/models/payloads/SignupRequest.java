package com.student.models.payloads;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SignupRequest {

	@NotBlank
	@Email
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String rePassword;
	private Set<String> authorities;
}
