package com.student.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import com.student.security.CustomUserDetailsService;
import com.student.security.JwtAuthenticationEntryPoint;
import com.student.security.JwtAuthenticationFilter;
import com.student.utils.CookieHelper;

/**
 * This class for crux of security implementation ref:
 * https://www.callicoder.com/spring-boot-spring-security-jwt-mysql-react-app-part-2/
 * 
 * @author Anurag
 *
 */
@Configuration
@EnableWebSecurity // Enables Security for web applications
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, // Enables Role level security e.g:
									 // @RolesAllowed("ROLE_ADMIN")public Poll
									 // createPoll() {}
	prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    String tokenKey = "student-app";
    
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
	return new JwtAuthenticationFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
	authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
	return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
		.antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
			"/**/*.css", "/**/*.js")
		.permitAll().antMatchers("/auth/**").permitAll()
		.antMatchers("/api/project/**").permitAll()
		.antMatchers("/login", "/api/project/**").permitAll().anyRequest()
		.authenticated()
		.and()
		.rememberMe()
		.key(tokenKey)
		.rememberMeServices(rememberMeServices());

	// Add our custom JWT security filter
	http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    
    @Bean
    public RememberMeServices rememberMeServices() {
	TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices(tokenKey, customUserDetailsService);
	rememberMeServices.setAlwaysRemember(true);
	rememberMeServices.setTokenValiditySeconds(CookieHelper.MAX_AGE);
	return rememberMeServices;
    }
}
