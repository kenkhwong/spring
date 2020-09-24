package com.example.ken;

import java.util.HashSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String SYSTEM_USER = "st@kenkhwong.atlassian.net";
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) 
            throws Exception 
    {
        auth.authenticationProvider(new AuthenticationProvider() {
        	@Override
        	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        		if (!SYSTEM_USER.equals(authentication.getPrincipal()))
        			throw new BadCredentialsException("Not a system account");
        		
        		
        		return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
        				authentication.getCredentials(),new HashSet<GrantedAuthority>());
        	}

        	@Override
        	public boolean supports(Class<?> authentication) {
        		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
        	}
        });
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
				.anyRequest().authenticated()
				.and().httpBasic();
	}
/*
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user =
			User.withUsername("user")
				.password("{noop}password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}*/
}