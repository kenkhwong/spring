package com.example.ken;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

@Configuration
@EnableWebSecurity
public class JiraSecurityConnector extends WebSecurityConfigurerAdapter {
	
	private static final String SYSTEM_SITE = "example.atlassian.net";
	private static final String SYSTEM_USER = "system@example.atlassian.net";
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) 
            throws Exception 
    {
        auth.authenticationProvider(new AuthenticationProvider() {
        	@Override
        	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        		
        		if (!SYSTEM_USER.equals(authentication.getPrincipal()))
        			throw new BadCredentialsException("Not a system user");
        		
        		String url = "https://" + SYSTEM_SITE + "/rest/servicedeskapi/servicedesk";
        		String encodedCred = Base64.getEncoder().encodeToString((authentication.getPrincipal()
        				+ ":" + authentication.getCredentials()).getBytes());

        		try {
        			HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        			con.setRequestProperty("Authorization", "Basic " + encodedCred);
        			con.setRequestMethod("GET");

        			int jiraResponseCode = con.getResponseCode();
        			
        			switch (jiraResponseCode) {
        			case 200 : //successful
        				return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
                				authentication.getCredentials(),new HashSet<GrantedAuthority>());
        			default :
        				throw new AuthenticationServiceException("Jira response is " + jiraResponseCode);
        			}

        		} catch (IOException e) {
        			throw new AuthenticationServiceException(e.toString(), e);
        		}
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
}