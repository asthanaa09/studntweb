package com.student.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.student.models.Role;
import com.student.models.User;
import com.student.models.UserRoles;
import com.student.models.UserView;
import com.student.models.payloads.SignupRequest;
import com.student.repositories.RoleRepository;
import com.student.repositories.UserRepository;
import com.student.repositories.UserRolesRepository;

@Service
public class UserService {

    @Autowired
    private RoleRepository mRoleRepository;
    @Autowired
    private UserRepository mUserRepository;
    @Autowired
    private UserRolesRepository mUserRoleRepository;
    @Autowired
    private PasswordEncoder mPasswordEncoder;

    public UserView createUser(SignupRequest request) {
	Set<String> authorities = Stream
		.of(Role.RoleType.ADMIN.toString(), Role.RoleType.TEACHER.toString(), Role.RoleType.STUDENT.toString())
		.collect(Collectors.toSet());

	if (mUserRepository.findByUsername(request.getUsername()) != null)
	    throw new RuntimeException("User already exists");

	if (!request.getPassword().equals(request.getRePassword()))
	    throw new RuntimeException("Passwords not matched");

	if (request.getAuthorities() == null || !authorities.containsAll(request.getAuthorities()))
	    throw new RuntimeException("Un-supported authorities");

	// Create User
	User user = new User();
	user.setUsername(request.getUsername());
	user.setPassword(mPasswordEncoder.encode(request.getPassword()));
	user.setActive(true);
	user = mUserRepository.save(user);

	// Password
	insertRole(user, request.getAuthorities());
	return getUserView(user);
    }

    private void insertRole(User user, Set<String> roles) {
	List<Role> availableRole = mRoleRepository.findAll();

	Map<String, Role> roleMap = new HashMap<String, Role>();
	List<UserRoles> userRoles = new ArrayList<UserRoles>();

	for (Role role : availableRole)
	    roleMap.put(role.getRoleType().toString(), role);

	roles.forEach((type) -> {
	    if (roles.contains(type.toString())) {
		userRoles.add(new UserRoles(user, roleMap.get(type)));
	    }
	});

	mUserRoleRepository.saveAll(userRoles);
    }

    public UserView getUserView(User user) {
	UserView userView = new UserView();
	userView.setUsername(user.getUsername());
	userView.setId(user.getId());
	return userView;
    }
}
