package com.vmware.kafka.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("anitha").password("{noop}test123").roles("ADMIN").and().withUser("admin")
				.password("{noop}admin").roles("ADMIN");
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests().antMatchers(HttpMethod.POST, "/eventdatas/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/eventdatas/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.PATCH, "/eventdatas/**").hasRole("ADMIN")
				.and().csrf().disable();
	}

}
