package com.student.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.models.User;
import com.student.models.UserView;
import com.student.models.payloads.LoginRequest;
import com.student.models.payloads.SignupRequest;
import com.student.repositories.UserRepository;
import com.student.response.LoginResponse;
import com.student.security.JwtTokenProvider;
import com.student.security.UserPrincipal;
import com.student.service.UserService;
import com.student.utils.CookieHelper;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    UserRepository mUserRepository;

    @Autowired
    AuthenticationManager mAuthenticationManager;
    @Autowired
    JwtTokenProvider mTokenProvider;
    @Autowired
    private UserService mUserService;
    @Autowired
    private CookieHelper mCookieHelper;

    /**
     * Is a prefered way to get user details as request body or using request
     * headers
     * 
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {
	LoginResponse authResponse = new LoginResponse();

	try {
	    Authentication auth = mAuthenticationManager.authenticate(
		    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

	    SecurityContextHolder.getContext().setAuthentication(auth);
	    User user = ((UserPrincipal) auth.getPrincipal()).getUser();
	    user.setPassword(null);

	    authResponse = new LoginResponse();
	    String token = mTokenProvider.generateToken(auth);
	    authResponse.setToken(token);
	    authResponse.setUser(user);
	    authResponse.setMessage("Successfully logged in");
	    mCookieHelper.setCookies(request, response, token);
	    authResponse.setHttpStatusCode(HttpStatus.OK.ordinal());
	    response.reset();
	    response.sendRedirect("redirect:/dashboard/project");
	    
	} catch (Exception e) {
	    authResponse.setMessage(e.getMessage());
	    authResponse.setHttpStatusCode(HttpStatus.BAD_REQUEST.ordinal());
	}

	return new ResponseEntity<LoginResponse>(authResponse, HttpStatus.OK);
    }

    /**
     * Register new user
     * 
     * @param user
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<?> create(@RequestBody SignupRequest signUpRequest) {
	return new ResponseEntity<UserView>(mUserService.createUser(signUpRequest), HttpStatus.OK);
    }
    
    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
	mCookieHelper.deleteCookie(request, response);
	response.sendRedirect("redirect:/login");
    }
}
