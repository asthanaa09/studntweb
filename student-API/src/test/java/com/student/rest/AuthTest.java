package com.student.rest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.data.AuthRequest;
import com.student.data.JsonHelper;
import com.student.data.UserTestDataFactory;
import com.student.models.User;
import com.student.models.UserView;
import com.student.models.payloads.SignupRequest;
import com.student.response.LoginResponse;
import com.student.utils.Utils;

/**
 * Test all auth related APIs
 * 
 * @author Anurag Asthana
 *
 */
@SpringBootTest
@AutoConfigureMockMvc // - TODO: What is does 
public class AuthTest {

	private final MockMvc mockMvc;
	private final ObjectMapper objectMapper;
	private final UserTestDataFactory userTestDataFactory;
	
	private final String PASSWORD = "12345334";
	
	private final String AUTH_API_BASE_URL = "/auth/login";
	
	@Autowired
    public AuthTest(MockMvc mockMvc, ObjectMapper objectMapper, UserTestDataFactory userTestDataFactory) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userTestDataFactory = userTestDataFactory;
    }
	
	@Test
	@Ignore
	public void chekcLoginApi() throws Exception {
		UserView userView = userTestDataFactory.createUser(String.format("%s@gmail.com", Utils.getTimestamp()),
				PASSWORD);

		AuthRequest req = new AuthRequest();
		req.setUsername(userView.getUsername());
		req.setPassword(PASSWORD);

		MvcResult createResult = this.mockMvc
				.perform(post(AUTH_API_BASE_URL).contentType(MediaType.APPLICATION_JSON)
						.content(JsonHelper.toJson(objectMapper, req)))
				.andExpect(status().isOk()).andExpect(header().doesNotExist(HttpHeaders.AUTHORIZATION)).andReturn();

		LoginResponse response = JsonHelper.fromJson(objectMapper, createResult.getResponse().getContentAsString(),
				LoginResponse.class);
		User authUser = response.getUser();
		assertEquals(userView.getId(), authUser.getId(), "User ids must match!");
		assertNotNull(response.getToken());
	}
	
	/**
	 * For any bad credential login should be fails
	 * In response token should be null
	 * 
	 * @throws Exception
	 */
	@Test
	@Ignore
    public void testLoginFail() throws Exception {
        UserView userView = userTestDataFactory.createUser(String.format("test.user.%s@nix.io", Utils.getTimestamp()), PASSWORD);

        AuthRequest request = new AuthRequest();
        request.setUsername(userView.getUsername());
        request.setPassword("zxc");

        MvcResult result = this.mockMvc
                .perform(post(AUTH_API_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonHelper.toJson(objectMapper, request)))
                .andExpect(status().isOk())
                .andExpect(header().doesNotExist(HttpHeaders.AUTHORIZATION))
                .andReturn();
        
        LoginResponse response = JsonHelper.fromJson(objectMapper, result.getResponse().getContentAsString(), LoginResponse.class);
        assertTrue((response.getToken() == null));
	}
	
	@Test
    public void testSignUp() throws Exception {
        SignupRequest goodRequest = new SignupRequest();
        goodRequest.setUsername(String.format("test.user.%s@nix.com", Utils.getTimestamp()));
        goodRequest.setPassword(PASSWORD);
        goodRequest.setRePassword(PASSWORD);
        goodRequest.setAuthorities(Stream.of("ADMIN").collect(Collectors.toSet()));

        MvcResult createResult = this.mockMvc
                .perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonHelper.toJson(objectMapper, goodRequest)))
                .andExpect(status().isOk())
                .andReturn();

        UserView userView = JsonHelper.fromJson(objectMapper, createResult.getResponse().getContentAsString(), UserView.class);
        assertNotNull(userView.getId().toString(), "User id must not be null!");
        assertEquals(goodRequest.getUsername(), userView.getUsername(), "User Name  should matched");
    }
	
	/**
	 * Signup should fails on
	 * 
	 * 1. If username or password missing
	 * 2. Password and rePassword are not matched
	 * 
	 * @throws Exception
	 */
	@Test
    public void testSignUpFail() throws Exception {
        SignupRequest goodRequest = new SignupRequest();
        goodRequest.setUsername(null);
        goodRequest.setPassword(PASSWORD);
        goodRequest.setRePassword("diffPassword");
        goodRequest.setAuthorities(Stream.of("ADMIN").collect(Collectors.toSet()));

       this.mockMvc
                .perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonHelper.toJson(objectMapper, goodRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();

    }
}
