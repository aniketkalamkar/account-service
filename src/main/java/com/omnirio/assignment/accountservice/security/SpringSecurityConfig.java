package com.omnirio.assignment.accountservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.omnirio.assignment.accountservice.security.filter.AuthorizationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
		  prePostEnabled = true, // (1)
		  securedEnabled = true, // (2)
		  jsr250Enabled = true) 
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
////		auth.authenticationProvider(authenticationProvider());
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.authorizeRequests().
		antMatchers("/hello/**").permitAll().
		and().
		authorizeRequests().
		antMatchers(HttpMethod.GET, "/api/account/*").hasAnyAuthority("ROLE_C","ROLE_M").
		antMatchers("/api/account/*").hasAuthority("ROLE_M").
		anyRequest().authenticated().
		and()
        .addFilter(new AuthorizationFilter(authenticationManager()))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}
}