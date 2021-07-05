package com.student.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.student.models.User;
import com.student.repositories.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    /**
     * Let people login with either Username
     **/
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	User users = userRepository.findByUsername(userName);

	if (users == null)
	    new UsernameNotFoundException("User Not Found with user name : " + userName);

	return UserPrincipal.create(users);
    }

    /**
     * Used by JWTAuthenticationFilter
     * 
     * @param id
     * @return
     */
    @Transactional
    public UserDetails loadUserById(Long id) {
	User user = userRepository.findById(id)
		.orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));
	return UserPrincipal.create(user);
    }
}
