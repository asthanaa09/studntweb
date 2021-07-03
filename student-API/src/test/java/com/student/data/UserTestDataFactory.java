package com.student.data;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.models.UserView;
import com.student.models.payloads.SignupRequest;
import com.student.service.UserService;

@Service
public class UserTestDataFactory {
	
	@Autowired
	private UserService mUserService;
	
	public UserView createUser(String username, String password) {
		
		Set<String> authorities = Stream.of("ADMIN").collect(Collectors.toSet());
		
		SignupRequest createRequest = new SignupRequest();
		createRequest.setUsername(username);
		createRequest.setPassword(password);
		createRequest.setRePassword(password);
		createRequest.setAuthorities(authorities);

		UserView userView = mUserService.createUser(createRequest);
		assertNotNull(userView.getId(), "User id must not be null!");

		return userView;
	}
}
