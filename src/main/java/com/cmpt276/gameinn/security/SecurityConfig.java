package com.cmpt276.gameinn.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.
		EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.
		WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.cmpt276.gameinn.auth.LogoutHandler;

@Configuration @EnableWebSecurity public class SecurityConfig extends
	WebSecurityConfigurerAdapter {
	private final LogoutHandler logoutHandler;

	public SecurityConfig(LogoutHandler logoutHandler) {
		this.logoutHandler = logoutHandler;
	}

	@Override protected void configure(HttpSecurity http)
	throws Exception {
		// When you add more endponts for backend, add it with ".antMatchers("/controller_name/**").permitAll()" under http.authorizeRequests()
		http.authorizeRequests()
				.antMatchers("/groupfinders/**").permitAll()
				.antMatchers("/favicon.ico").permitAll()
			.and()
				.oauth2Login()
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.addLogoutHandler(logoutHandler)
			.and()
				.csrf().disable();
	}
}
