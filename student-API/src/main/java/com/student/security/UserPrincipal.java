package com.student.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.student.models.Role;
import com.student.models.User;

/**
 * This is the class whose instances will be returned from our custom
 * UserDetailsService. Spring Security will use the information stored in the
 * UserPrincipal object to perform authentication and authorization.
 * 
 * @author Anurag Asthana
 *
 */
public class UserPrincipal implements UserDetails {

    /**
     * Spring security by default add prefix to granted authority
     */
    private static final String GRANTED_AUTHORITY_PREFIX = "ROLE_";
    private static final long serialVersionUID = 1L;

    User user;
    private Long id;
    private String username;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, User user, String password, Collection<? extends GrantedAuthority> authorities) {
	this.id = id;
	this.password = password;
	this.user = user;
	this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	Set<Role> roles = user.getRoles();

	roles.forEach((role) -> {
	    authorities.add(new SimpleGrantedAuthority(GRANTED_AUTHORITY_PREFIX + role.getRoleType().toString()));
	});

	return new UserPrincipal(user.getId(), user, user.getPassword(), authorities);
    }

    public Long getId() {
	return id;
    }

    @Override
    public String getUsername() {
	return username;
    }

    @Override
    public String getPassword() {
	return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
	return true;
    }

    @Override
    public boolean isAccountNonLocked() {
	return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
	return true;
    }

    @Override
    public boolean isEnabled() {
	return true;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;
	UserPrincipal that = (UserPrincipal) o;
	return Objects.equals(id, that.id);
    }

    public User getUser() {
	return this.user;
    }

    @Override
    public int hashCode() {

	return Objects.hash(id);
    }
}
