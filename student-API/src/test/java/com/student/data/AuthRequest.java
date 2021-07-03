package com.student.data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AuthRequest {
  
	@NotNull
	@Email
    private String username;
    @NotNull
    private String password;
    
}